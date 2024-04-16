import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QFrame, QVBoxLayout, QTableWidget, QTableWidgetItem
from PyQt5.QtChart import QChart, QChartView, QLineSeries
from PyQt5.QtCore import Qt
import numpy as np

class MyWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('Table and Graph Example')
        self.setGeometry(100, 100, 800, 600)

        # Create frames
        self.table_frame = QFrame(self)
        self.table_frame.setFrameShape(QFrame.StyledPanel)
        self.graph_frame = QFrame(self)
        self.graph_frame.setFrameShape(QFrame.StyledPanel)

        # Create layout for main window
        layout = QVBoxLayout()
        layout.addWidget(self.table_frame)
        layout.addWidget(self.graph_frame)

        # Set layout to central widget
        central_widget = QWidget()
        central_widget.setLayout(layout)
        self.setCentralWidget(central_widget)

        # Populate table frame
        self.populateTable()

        # Populate graph frame
        self.populateGraph()

    def populateTable(self):
        # Create table widget
        table = QTableWidget()
        table.setColumnCount(3)
        table.setHorizontalHeaderLabels(['X', 'Y', 'Z'])

        # Add dummy data to table
        for i in range(10):
            table.insertRow(i)
            for j in range(3):
                item = QTableWidgetItem(str(i * j))
                table.setItem(i, j, item)

        # Set table frame layout
        layout = QVBoxLayout()
        layout.addWidget(table)
        self.table_frame.setLayout(layout)

    def populateGraph(self):
        # Create series for the graph
        series = QLineSeries()
        for i in range(10):
            series.append(i, np.sin(i))  # Example data

        # Create chart and add series
        chart = QChart()
        chart.addSeries(series)
        chart.setTitle('Sin Wave Graph')
        chart.createDefaultAxes()

        # Create chart view and set the chart
        chart_view = QChartView(chart)
        chart_view.setRenderHint(QPainter.Antialiasing)

        # Set graph frame layout
        layout = QVBoxLayout()
        layout.addWidget(chart_view)
        self.graph_frame.setLayout(layout)

def main():
    app = QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()

