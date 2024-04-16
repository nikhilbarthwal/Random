import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QLabel, QLineEdit, QGridLayout, QWidget

class MyWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('Fixed Size Window with Grid Layout Example')
        self.setFixedSize(400, 300)  # Set fixed window size (width, height)

        central_widget = QWidget(self)
        self.setCentralWidget(central_widget)

        layout = QGridLayout(central_widget)

        labels = []
        text_fields = []
        for i in range(3):  # Create a 3x2 grid of labels and text fields
            label = QLabel(f'Label {i+1}', self)
            text_field = QLineEdit(self)
            layout.addWidget(label, i, 0)  # Add label to the grid
            layout.addWidget(text_field, i, 1)  # Add text field to the grid
            labels.append(label)
            text_fields.append(text_field)

def main():
    app = QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()
