

from PyQt5.QtWidgets import \
        QApplication, QWidget, QLabel,\
        QPushButton, QVBoxLayout
from PyQt5.QtNetwork import QTcpSocket

import sys

class Crossing(QWidget):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

        # Výroba ovládacích prvků
        self.connectButton = QPushButton(self, text = "Start")
        self.connectButton.clicked.connect(self.connect)

        self.disconnectButton = QPushButton(self, text = "Stop")
        self.disconnectButton.clicked.connect(self.disconnect)

        self.messageLabel = QLabel(self)
        self.statusLabel = QLabel(self)
        self.statusLabel.setText('Disconnected')

        # Rozložení ovládacích prvků
        self.layout = QVBoxLayout(self)
        self.layout.addWidget(
                self.connectButton)
        self.layout.addWidget(self.disconnectButton)

        self.layout.addWidget(self.messageLabel)
        self.layout.addWidget(self.statusLabel)
        

        # Příprava TCP socketu
        self.socket = QTcpSocket(self)
        self.socket.readyRead.connect(self.read)
        self.socket.connected.connect(
                self.connected)
        self.readBuffer = bytearray()

        # Zobrazení
        self.setLayout(self.layout)
        self.show()

    def disconnect(self):
        # Odpojí se od servru
        self.socket.abort()
        self.statusLabel.setText('Disconnected')

    def connect(self):
        # Nejdřív se odpoj, pokud už
        # spojení běží
        self.socket.abort()

        # A znovu se připoj
        self.socket.connectToHost(
                "ksp.mff.cuni.cz", 48888)
        self.statusLabel.setText('Connecting')

    def connected(self):
        # Pozdravíme server
        self.socket.write("HELLO\n".encode())
        self.statusLabel.setText('Connected')

    def read(self):
        # Přečteme všechno, co jsme dostali
        while self.socket.bytesAvailable() > 0:
            self.readBuffer += \
                    self.socket.read(128)

        # Rozdělíme na řádky
        lines = self.readBuffer.split(b"\n")

        # Zbytek uložíme na příště
        self.readBuffer = lines.pop()

        # Zpracujeme řádky, které dorazily
        for l in lines:
            self.messageLabel.setText(
                    l.decode().rstrip())

# Spuštění celého programu
app = QApplication(sys.argv)
crossing = Crossing()
app.exec()

