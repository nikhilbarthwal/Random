import numpy as np
import matplotlib.pyplot as plt

# Parameters
low = 0  # Lower bound of the distribution
high = 10  # Upper bound of the distribution

# Generate 1000 random numbers from a uniform distribution
s = np.random.uniform(low, high, 1000)

# Create a histogram of the generated numbers
count, bins, ignored = plt.hist(s, 30, density=True)

# Plot the uniform distribution
plt.plot(bins, np.ones_like(bins) / (high - low), linewidth=2, color='r')

# Set labels and title
plt.xlabel('X')
plt.ylabel('Probability')
plt.title('Uniform Distribution')

# Display the plot
plt.show()

