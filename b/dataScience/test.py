#!/usr/bin/env python3

import pandas
import numpy as np
import scipy.special
import scipy.stats
import matplotlib.pyplot as plt
import sklearn.linear_model

url = "https://www.cl.cam.ac.uk/teaching/2021/DataSci/data/climate.csv"
climate = pandas.read_csv(url)
df = climate.loc[(climate.station=='Cambridge') & (climate.yyyy>=1985)]
t = df.yyyy + (df.mm-1)/12
temp = (df.tmin + df.tmax)/2

sigma = 10

def temp(theta, t):
    alpha, beta_1, beta_2, gamma = theta
    return alpha + beta_1*np.sin(2*np.pi*t) + beta_2*np.cos(2*np.pi*t) + gamma*(t-2000)

theta_sample = list(zip(*[
    np.random.normal(loc=10, scale=5, size=1000),
    np.random.normal(loc=0, scale=5, size=1000),
    np.random.normal(loc=0, scale=5, size=1000),
    np.random.normal(loc=0, scale=1, size=1000)
]))
w = [scipy.stats.norm.pdf(t, loc=temp(theta, t), scale=sigma) for theta in theta_sample]

print(w)

plt.hist([theta[3] for theta in theta_sample], weights=w, density=True)
plt.show()
