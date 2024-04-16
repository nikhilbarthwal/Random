import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QFrame, QVBoxLayout, QPushButton, QWidget

class MyWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('Two Frames Example')
        self.setGeometry(100, 100, 600, 400)

        # Create two frames
        frame1 = QFrame(self)
        frame1.setStyleSheet("background-color: lightblue;")
        frame2 = QFrame(self)
        frame2.setStyleSheet("background-color: lightgreen;")

        # Create layout for main window
        layout = QVBoxLayout()
        layout.addWidget(frame1)
        layout.addWidget(frame2)

        # Set layout to central widget
        central_widget = QWidget()
        central_widget.setLayout(layout)
        self.setCentralWidget(central_widget)

        # Add buttons to frames
        button1 = QPushButton('Button 1', frame1)
        button2 = QPushButton('Button 2', frame2)

        # Set positions and sizes of buttons
        button1.setGeometry(50, 50, 100, 30)
        button2.setGeometry(50, 50, 100, 30)

def main():
    app = QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()

