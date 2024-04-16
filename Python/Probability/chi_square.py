import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import chi2

# Parameters
df = 3  # Degrees of freedom

# Generate 1000 random numbers from a chi-square distribution
s = np.random.chisquare(df, 1000)

# Create a histogram of the generated numbers
count, bins, ignored = plt.hist(s, 30, density=True)

# Plot the chi-square distribution
x = np.linspace(0, 20, 1000)
plt.plot(x, chi2.pdf(x, df), linewidth=2, color='r')

# Set labels and title
plt.xlabel('X')
plt.ylabel('Probability')
plt.title('Chi-Square Distribution')

# Display the plot
plt.show()

