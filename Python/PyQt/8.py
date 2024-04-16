import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QVBoxLayout, QWidget
from matplotlib.backends.backend_qt5agg import FigureCanvasQTAgg as FigureCanvas
from matplotlib.figure import Figure
import numpy as np

class MyWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('Matplotlib Plot inside PyQt Frame')
        self.setGeometry(100, 100, 800, 600)

        # Create a Matplotlib figure and canvas
        self.figure = Figure()
        self.canvas = FigureCanvas(self.figure)

        # Create a QVBoxLayout to hold the Matplotlib canvas
        layout = QVBoxLayout()
        layout.addWidget(self.canvas)

        # Create a central widget and set the layout
        central_widget = QWidget()
        central_widget.setLayout(layout)
        self.setCentralWidget(central_widget)

        # Plot the data
        self.plot()

    def plot(self):
        # Clear the previous plot
        self.figure.clear()

        # Generate some data
        x = np.linspace(0, 10, 100)
        y = np.sin(x)

        # Add subplot to the figure
        ax = self.figure.add_subplot(111)
        
        # Plot the data
        ax.plot(x, y)
        ax.set_title('Sin Wave')
        ax.set_xlabel('X')
        ax.set_ylabel('Y')

        # Draw the plot on the canvas
        self.canvas.draw()

def main():
    app = QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()

