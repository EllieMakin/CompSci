import scipy.optimize
import numpy as np

pi = np.pi

def f(s):
    return np.exp(-3/2/np.power(s, 2)) / np.sqrt(2*pi*np.power(s, 2))

sMax = scipy.optimize.fmin(lambda t: -f(t), np.log(1))

print(sMax)
