from PyQt5.QtCore import QTimer
from PyQt5.QtWidgets import QApplication, QMessageBox

import sys

app = QApplication(sys.argv)

box = QMessageBox(text="hello world")
box.show()

timer = QTimer(singleShot=True)
timer.timeout.connect(box.close)
timer.start(2000)

app.exec_()
