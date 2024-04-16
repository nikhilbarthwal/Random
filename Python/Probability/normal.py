import numpy as np
import matplotlib.pyplot as plt

# Mean and standard deviation
mu, sigma = 0, 0.1

# Generate 1000 random numbers from a normal distribution
s = np.random.normal(mu, sigma, 1000)

# Create a histogram of the generated numbers
count, bins, ignored = plt.hist(s, 30, density=True)

# Plot the probability density function
plt.plot(bins, 1/(sigma * np.sqrt(2 * np.pi)) *
               np.exp( - (bins - mu)**2 / (2 * sigma**2) ),
         linewidth=2, color='r')

# Display the plot
plt.show()

