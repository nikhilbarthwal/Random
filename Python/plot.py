import matplotlib.pyplot as plt
import numpy as np

plt.rcParams["figure.figsize"] = [7.50, 3.50]
plt.rcParams["figure.autolayout"] = True

x = np.linspace(-10, 10, 100)
y1 = abs(np.sin(x)) + abs(np.cos(x)) + 1.0
y2 = abs(np.cos(x)) * 0.5 + 1.0
plt.fill_between(x, y1, step="pre", alpha=1.0)
plt.fill_between(x, y2, color="white", step="pre", alpha=1.0)

plt.plot(x, y1, color="black", drawstyle="steps")
plt.plot(x, y2, color="black", drawstyle="steps")

plt.show()

