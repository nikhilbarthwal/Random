from matplotlib.widgets import Button, Slider
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm


# Define initial parameters
init_mean = 0
init_stdev = 1


# `x` span of +/- 4 standard deviations
x_start = init_mean - 4 * init_stdev
x_end = init_mean + 4 * init_stdev
step = 0.05

x = np.arange(start=x_start, stop=x_end+step, step=step)


# The parametrized function to be plotted
def f(xaxis, stdev):
    return norm.pdf(x=xaxis, loc=init_mean, scale=stdev)


# Create the figure and the line that we will manipulate
fig, ax = plt.subplots()
line, = ax.plot(x, f(x, init_stdev), lw=2)
ax.set_xlabel('Points')

# adjust the main plot to make room for the sliders
fig.subplots_adjust(left=0.25, bottom=0.25)

# Make a horizontal slider to control the frequency.
axstdev = fig.add_axes([0.25, 0.1, 0.65, 0.03])
slider = Slider(
    ax=axstdev, label='Std. Deviation', valmin=0.1, valmax=30, valinit=init_stdev)


# The function to be called anytime a slider's value changes
def update(_):
    line.set_ydata(f(x, slider.val))
    fig.canvas.draw_idle()


# register the update function with each slider
slider.on_changed(update)

# Create a `matplotlib.widgets.Button` to reset the sliders to initial values.
resetax = fig.add_axes([0.8, 0.025, 0.1, 0.04])
button = Button(resetax, 'Reset', hovercolor='0.975')


def reset(_):
    slider.reset()


button.on_clicked(reset)

plt.show()
