import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QVBoxLayout, QWidget
from PyQt5.QtChart import QChart, QLineSeries, QChartView
from PyQt5.QtCore import Qt

class MyWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('PyQt Chart Example')
        self.setGeometry(100, 100, 800, 600)

        # Create chart
        chart = QChart()
        chart.setTitle('Line Chart Example')

        # Create series
        series = QLineSeries()
        series.setName('Random Data')

        # Add data to series
        for i in range(10):
            series.append(i, i * i)

        # Add series to chart
        chart.addSeries(series)

        # Create chart view
        chart_view = QChartView(chart)
        chart_view.setRenderHint(QPainter.Antialiasing)

        # Create layout for main window
        layout = QVBoxLayout()
        layout.addWidget(chart_view)

        # Set layout to central widget
        central_widget = QWidget()
        central_widget.setLayout(layout)
        self.setCentralWidget(central_widget)

def main():
    app = QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()

