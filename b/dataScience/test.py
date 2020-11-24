#!/usr/bin/env python3

import numpy as np
import scipy.special
import matplotlib.pyplot as plt

n = 10000

def rxy():
    eye_l = 0.05
    eye_r = 0.10
    mouth = 0.30
    i = np.random.uniform()
    if i < eye_l:
        x, y = -0.3, 0.3
    elif i < eye_r:
        x, y = 0.3, 0.3
    elif i < mouth:
        theta = np.random.uniform(-5*np.pi/6, -np.pi/6)
        x, y = 0.5*np.cos(theta), 0.5*np.sin(theta)
    else:
        theta = np.random.uniform(0, 2*np.pi)
        x, y = np.cos(theta), np.sin(theta)
    dx, dy = np.random.normal(loc=0, scale=0.05, size=2)
    return (x+dx, y+dy)

pairs = np.array([rxy() for i in range(n)])
x, y = zip(*pairs)

fig, axes = plt.subplots(2, 2, figsize=(6, 6))
axes[0, 0].scatter(x, y, marker=',', s=1, alpha=0.1)
axes[1, 0].hist(x, bins=100)
axes[0, 1].hist(y, bins=100, orientation="horizontal")
plt.show()
