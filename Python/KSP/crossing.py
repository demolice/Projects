

from PyQt5.QtCore import QTimer
from PyQt5.QtWidgets import \
        QApplication, QWidget, \
        QPushButton, QVBoxLayout
from PyQt5.QtNetwork import QTcpSocket

import sys


# Obecný cestovatel
class Traveller:
    def __init__(self, id, speed):
        self.speed = float(speed)
        self.id = int(id)

        self.timer = QTimer()
        self.timer.timeout.connect(self.done)

    # Cestovatel vstupuje do sledovaného úseku
    def start(self, crossing):
        self.crossing = crossing

        # Časovač je v milisekundách
        self.timer.start(1000 * self.roadLength / self.speed)

    # Obsluha časovače: cestovatel opouští úsek
    def done(self):
        self.crossing.sendBack(self)

    # Převod argumentů zpět na řetězec
    def strArgs(self):
        return ("id=" + str(self.id) + " speed=" + str(self.speed))


class Car(Traveller):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        # Sledujeme 500 metrů silnice
        self.roadLength = 500

    def __str__(self):
        return "CAR " + super().strArgs()


class Pedestrian(Traveller):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        # Sledujeme 100 metrů chodníku
        self.roadLength = 100

    def __str__(self):
        return "PEDESTRIAN " + super().strArgs()


class Crossing(QWidget):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

        # Výroba ovládacích prvků
        self.connectButton = QPushButton(self, text="Start")
        self.connectButton.clicked.connect(self.connect)

        self.disconnectButton = QPushButton(self, text="Stop")
        self.disconnectButton.clicked.connect(self.disconnect)

        # Rozložení ovládacích prvků
        self.layout = QVBoxLayout(self)
        self.layout.addWidget(self.connectButton)
        self.layout.addWidget(self.disconnectButton)

        # Příprava TCP socketu
        self.socket = QTcpSocket(self)
        self.socket.readyRead.connect(self.read)
        self.socket.connected.connect(self.connected)
        self.readBuffer = bytearray()

        self.travellers = {}

        # Zobrazení
        self.setLayout(self.layout)
        self.show()

    def disconnect(self):
        self.socket.write("BYE\n".encode())

    def connect(self):
        # Nejdřív se odpoj,
        # pokud už spojení běží
        self.socket.abort()

        # A znovu se připoj
        self.socket.connectToHost("ksp.mff.cuni.cz", 48888)

    def connected(self):
        # Pozdravíme server
        self.socket.write("HELLO\n".encode())

    def read(self):
        # Přečteme všechno, co jsme dostali
        while self.socket.bytesAvailable() > 0:
            self.readBuffer += self.socket.read(128)

        # Rozdělíme na řádky
        lines = self.readBuffer.split(b"\n")

        # Zbytek uložíme na příště
        self.readBuffer = lines.pop()

        # Zpracujeme řádky, které dorazily
        for l in lines:
            stripped = l.decode().rstrip()
            args = stripped.split(" ")
            travellerType = args.pop(0)
            argmap = dict(map(lambda x: x.split("="), args))

            if travellerType == "CAR":
                self.addTraveller(Car(**argmap))
            elif travellerType == "PEDESTRIAN":
                self.addTraveller(Pedestrian(**argmap))

    def addTraveller(self, traveller):
        # Uložíme si cestovatele
        self.travellers[traveller.id] = traveller

        # Nechť cestovatel vstoupí do oblasti
        traveller.start(self)

    def sendBack(self, traveller):
        # Cestovatel opouští sledovanou oblast
        self.travellers[traveller.id] = None

        # Vrátíme cestovatele serveru
        text = str(traveller) + "\n"
        self.socket.write(text.encode())


# Spuštění celého programu
app = QApplication(sys.argv)
crossing = Crossing()
app.exec()
