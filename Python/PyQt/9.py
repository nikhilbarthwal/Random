import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QPushButton, QWidget, QVBoxLayout

class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('Main Window')
        self.setGeometry(100, 100, 300, 200)

        # Create a button
        self.button = QPushButton('Open Window', self)
        self.button.clicked.connect(self.openWindow)

    def openWindow(self):
        self.newWindow = SecondWindow()
        self.newWindow.show()

class SecondWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('Second Window')
        self.setGeometry(200, 200, 300, 200)

        layout = QVBoxLayout()
        layout.addWidget(QPushButton('Button', self))
        self.setLayout(layout)

def main():
    app = QApplication(sys.argv)
    mainWindow = MainWindow()
    mainWindow.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()

