

from PyQt5.QtWidgets import QApplication, \
        QWidget, QPushButton,\
        QHBoxLayout, QVBoxLayout, QComboBox
import sys


class Hospoda(QWidget):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

        # Ovládací prvky
        self.availableMeals = QComboBox(self)
        self.tables = QComboBox(self)
        self.orderedMeals = QComboBox(self)

        self.orderButton = QPushButton(self, text="Objednat")
        self.orderButton.clicked.connect(self.order)

        self.doneButton = QPushButton(self, text="Vydat")
        self.doneButton.clicked.connect(self.done)

        # Rozložení
        self.layout = QVBoxLayout(self)

        self.menuLayout = QHBoxLayout()
        self.menuLayout.addWidget(self.availableMeals)
        self.menuLayout.addWidget(self.tables)
        self.menuLayout.addWidget(self.orderButton)

        self.orderLayout = QHBoxLayout()
        self.orderLayout.addWidget(self.orderedMeals)
        self.orderLayout.addWidget(self.doneButton)

        self.layout.addLayout(self.menuLayout)
        self.layout.addLayout(self.orderLayout)
        self.setLayout(self.layout)

        # Data
        self.tables.addItems(["u okna", "u dveří", "uprostřed", "salonek"])
        self.availableMeals.addItems(
                ["knedlo-zelo-vepřo",
                 "svíčková se šesti",
                 "řízek se salátem"])

        self.show()

    def order(self):
        # Sestavení objednávky
        # z aktuálně vybraných položek
        meal = self.availableMeals.currentText()
        table = self.tables.currentText()
        objednavka = meal + " " + table
        self.orderedMeals.addItem(objednavka)

    def done(self):
        # Smazání aktuálně vybrané položky
        index = self.orderedMeals.currentIndex()
        self.orderedMeals.removeItem(index)


app = QApplication(sys.argv)
hospoda = Hospoda()
app.exec()
