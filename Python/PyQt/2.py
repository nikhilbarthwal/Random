import sys
from PyQt5.QtWidgets import QApplication, QMainWindow

class MyWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('Fixed Size Window Example')
        self.setFixedSize(400, 300)  # Set fixed window size (width, height)

def main():
    app = QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()

