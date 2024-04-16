import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QLabel, QLineEdit, QVBoxLayout, QWidget

class MyWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('PyQt Example')
        self.setGeometry(100, 100, 2000, 2000)  # Set window size

        # Create central widget and layout
        central_widget = QWidget(self)
        self.setCentralWidget(central_widget)
        layout = QVBoxLayout(central_widget)

        # Create labels and text fields
        labels = []
        text_fields = []
        for i in range(4):
            label = QLabel(f'Label {i+1}', self)
            text_field = QLineEdit(self)
            layout.addWidget(label)
            layout.addWidget(text_field)
            labels.append(label)
            text_fields.append(text_field)

        # Set labels and text fields positions and sizes
        for i, (label, text_field) in enumerate(zip(labels, text_fields)):
            label.setGeometry(50, 50 + i * 100, 200, 50)
            text_field.setGeometry(250, 50 + i * 100, 200, 50)

def main():
    app = QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()

