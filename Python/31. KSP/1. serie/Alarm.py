from PyQt5.QtCore import QTimer
from PyQt5.QtWidgets import QApplication, QMessageBox

import sys

def timeEvent():
    box.setText("Budík už zvoní")
    timer2.start(2000)

app = QApplication(sys.argv)

box = QMessageBox(text="Budík začal zvonit")
box.show()

timer = QTimer(singleShot=True)
timer.timeout.connect(timeEvent)
timer.start(2000)

timer2 = QTimer(singleShot=False)
timer2.timeout.connect(box.close)

app.exec_()
