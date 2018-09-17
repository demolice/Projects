from PyQt5.QtWidgets import \
    QApplication, QMessageBox
import sys

app = QApplication(sys.argv)
box = QMessageBox(text="Hello world")
box.show()
app.exec_()
