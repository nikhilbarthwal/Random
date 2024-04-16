import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import gamma

# Parameters
shape = 2  # Shape parameter (k)
scale = 2  # Scale parameter (theta)

# Generate 1000 random numbers from a gamma distribution
s = np.random.gamma(shape, scale, 1000)

# Create a histogram of the generated numbers
count, bins, ignored = plt.hist(s, 30, density=True)

# Plot the gamma distribution
x = np.linspace(0, 20, 100)
plt.plot(x, gamma.pdf(x, shape, scale=scale), linewidth=2, color='r')

# Set labels and title
plt.xlabel('X')
plt.ylabel('Probability')
plt.title('Gamma Distribution')

# Display the plot
plt.show()

