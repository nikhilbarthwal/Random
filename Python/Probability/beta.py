import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import beta

# Parameters
a = 2  # Shape parameter a
b = 5  # Shape parameter b

# Generate 1000 random numbers from a beta distribution
s = np.random.beta(a, b, 1000)

# Create a histogram of the generated numbers
count, bins, ignored = plt.hist(s, 30, density=True)

# Plot the beta distribution
x = np.linspace(0, 1, 100)
plt.plot(x, beta.pdf(x, a, b), linewidth=2, color='r')

# Set labels and title
plt.xlabel('X')
plt.ylabel('Probability')
plt.title('Beta Distribution')

# Display the plot
plt.show()

