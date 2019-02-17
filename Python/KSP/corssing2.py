import sys
from PySide2.QtCore import Qt, Signal, Slot, QObject, QTimer,\
    QAbstractListModel, QModelIndex, QByteArray, QTextCodec
from PySide2.QtNetwork import QTcpSocket, QAbstractSocket
from PySide2.QtWidgets import QApplication, QWidget, QLabel, QPushButton, \
    QVBoxLayout, QListView

SocketState = QAbstractSocket.SocketState


# Obecný cestovatel

class Traveller:
    def __init__(self, id, speed):
        self.speed = float(speed)
        self.id = int(id)

        # Časovač opuštění oblasti
        self.timer = QTimer()
        self.timer.timeout.connect(self.done)

        # Cestovatel je aktivní
        self.active = True

    # Cestovatel vstupuje do sledovaného úseku
    def start(self, crossing):
        self.crossing = crossing

        # Časovač je v milisekundách
        self.timer.start(1000 * self.roadLength / self.speed)

    # Obsluha časovače: cestovatel opouští úsek
    def done(self):
        # Pokud jsme se mezitím stihli odpojit a připojit, neposílej nic
        if self.active:
            self.crossing.sendBack(self)

    # Převod argumentů zpět na řetězec
    def strArgs(self):
        return ("id=" + str(self.id) + " speed=" + str(self.speed))

    def position(self):
        return self.roadLength - self.timer.remainingTime() * self.speed / 1000


class Car(Traveller):
    # Sledujeme 500 metrů silnice
    roadLength = 500

    def __str__(self):
        return "CAR " + super().strArgs()


class Pedestrian(Traveller):
    # Sledujeme 100 metrů chodníku
    roadLength = 100

    def __str__(self):
        return "PEDESTRIAN " + super().strArgs()

# Rozhraní pro server


class CrossingNetwork(QObject):
    # Změna stavu; signál pro stavový řádek
    stateChanged = Signal(str)

    def __init__(self, model, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.model = model

        # Inicializace socketu
        self.socket = QTcpSocket(self)
        self.socket.readyRead.connect(self.read)
        self.socket.stateChanged.connect(self.socketStateChanged)

        # Příprava pro čtení dat
        self.readBuffer = QByteArray()
        self.textCodec = QTextCodec.codecForName("UTF-8")

    @Slot()
    def socketConnect(self):
        # Nejdřív se odpoj, pokud už spojení běží
        self.socket.abort()

        # A znovu se připoj
        self.socket.connectToHost(
            "ksp.mff.cuni.cz", 48888)

    @Slot()
    def socketDisconnect(self):
        # Odpoj se
        self.socket.disconnectFromHost()

        # Vyprázdni model
        self.model.flush()

    @Slot(SocketState)
    def socketStateChanged(self, state):
        if state == SocketState.ConnectedState:
            # Připojeni? Pozdravíme server.
            self.socket.write(self.textCodec.fromUnicode("HELLO\n"))

        # A informujeme připojené posluchače o změně stavu
        self.stateChanged.emit(str(state).split(".")[-1].split("State")[0])

    def read(self):
        # Přečteme všechno, co jsme dostali
        while self.socket.bytesAvailable() > 0:
            self.readBuffer += \
                self.socket.read(128)

        # Rozdělíme na řádky
        lines = self.readBuffer.split("\n")

        # Zbytek uložíme na příště
        self.readBuffer = lines.pop()

        # Zpracujeme řádky, které dorazily
        for l in lines:
            stripped = self.textCodec.toUnicode(l.data()).rstrip()
            args = stripped.split(" ")
            travellerType = args.pop(0)
            argmap = dict(map(
                lambda x: x.split("="), args))

            if travellerType == "CAR":
                self.addTraveller(Car(**argmap))
            elif travellerType == "PEDESTRIAN":
                self.addTraveller(
                    Pedestrian(**argmap))

    def addTraveller(self, traveller):
        # Uložíme si cestovatele
        self.model.add(traveller)

        # Nechť cestovatel vstoupí do oblasti
        traveller.start(self)

    def sendBack(self, traveller):
        # Vrátíme cestovatele serveru
        text = str(traveller) + "\n"
        self.socket.write(self.textCodec.fromUnicode(text))

        self.model.remove(traveller)

# Model pro Qt


class CrossingModel(QAbstractListModel):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.list = []

    def rowCount(self, parent=None):
        return len(self.list)

    def data(self, index, role):
        row = index.row()
        if row < 0 or row >= len(self.list):
            return None

        traveller = self.list[row]
        if role == Qt.DisplayRole:
            return str(traveller)
        else:
            return None

    def flush(self):
        self.beginRemoveRows(QModelIndex(), 0, self.rowCount())
        for traveller in self.list:
            traveller.active = False
        self.list = []
        self.endRemoveRows()

    def add(self, traveller):
        rc = self.rowCount()
        self.beginInsertRows(QModelIndex(), rc, rc+1)
        self.list.append(traveller)
        self.endInsertRows()

    def remove(self, traveller):
        for i in range(len(self.list)):
            # Najdeme cestovatele v seznamu
            if self.list[i] == traveller:
                # Vyhodíme jej ze seznamu
                self.beginRemoveRows(QModelIndex(), i, i+1)
                del self.list[i]
                self.endRemoveRows()
                return


# View a Controller v jednom objektu
class Crossing(QWidget):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

        # Instance modelu
        self.model = CrossingModel()

        # Instance síťového připojení
        self.network = CrossingNetwork(self.model)

        # Výroba ovládacích prvků
        self.connectButton = QPushButton(self, text="Start")
        self.connectButton.clicked.connect(self.network.socketConnect)

        self.disconnectButton = QPushButton(self, text="Stop")
        self.disconnectButton.clicked.connect(self.network.socketDisconnect)

        # Zobrazení dat a propojení s modelem
        self.listView = QListView(self)
        self.listView.setModel(self.model)

        # Zobrazení stavu
        self.stateLabel = QLabel(self, text="Inactive")
        self.network.stateChanged.connect(self.stateChanged)

        # Rozložení ovládacích prvků
        self.layout = QVBoxLayout(self)
        self.layout.addWidget(self.connectButton)
        self.layout.addWidget(self.disconnectButton)
        self.layout.addWidget(self.listView)
        self.layout.addWidget(self.stateLabel)

        # Zobrazení
        self.setLayout(self.layout)
        self.show()

    # Aktualizátor stavového labelu
    @Slot(str)
    def stateChanged(self, state):
        self.stateLabel.setText(state)


# Spuštění celého programu
app = QApplication(sys.argv)
crossing = Crossing()
app.exec_()
