
from PyQt5.QtCore import QTimer
from PyQt5.QtWidgets import \
    QApplication, QWidget, QLabel, \
    QPushButton, QVBoxLayout

import sys

class Stopky(QWidget):
    def __init__(self, *args, **kwargs):

	# Inicializace QWidgetu samotného
        super().__init__(*args, **kwargs)

    #Pomocné proměnné

	# Výroba ovládacích prvků
        self.label = QLabel(self)

        self.labelStep = QLabel(self)

        self.buttonStart = QPushButton(self, text="Start")
        self.buttonStart.clicked.connect(self.start)

        self.buttonStop = QPushButton(self, text="Stop")
        self.buttonStop.clicked.connect(self.stop)

        self.buttonUp = QPushButton(self, text="*10")
        self.buttonUp.clicked.connect(self.higherStep)

        self.buttonDonw = QPushButton(self, text="/10")
        self.buttonDonw.clicked.connect(self.lowerStep)

	# Umístění ovládacích prvků
        self.layout = QVBoxLayout(self)
        self.layout.addWidget(self.label)
        self.layout.addWidget(self.buttonStart)
        self.layout.addWidget(self.buttonStop)
        self.layout.addWidget(self.labelStep)
        self.layout.addWidget(self.buttonUp)
        self.layout.addWidget(self.buttonDonw)

	# Zobrazení stopek
        self.setLayout(self.layout)
        self.show()

    def start(self):
        self.step = 1000
	# Vyrob časovač
        self.timer = QTimer(singleShot=True)
        self.timer.timeout.connect(self.tick)
        self.timer.start(self.step)

	# Počáteční čas je nula
        self.elapsed = 0
        self.updateLabel()

    def stop(self):
        self.timer.stop()

    def higherStep(self):
        self.updateTimer
        self.step *= 10
        self.timer.start(self.step)
        self.updateLabel

    def lowerStep(self):
        self.updateTimer
        self.step /= 10
        self.timer.start(self.step)
        self.updateLabel

    def updateTimer(self):
        self.timer.stop()
        self.elapsed += self.step

    def tick(self):
	# Uplynula další sekunda
        self.elapsed += self.step
        self.updateLabel()
        self.timer.start(self.step)

    def updateLabel(self):
	# Zobrazení uplynulého času
        self.label.setText(str(self.elapsed / 1000))
        self.labelStep.setText(str(self.step / 1000))

# Spuštění celého programu
app = QApplication(sys.argv)
stopky = Stopky()
app.exec_()
