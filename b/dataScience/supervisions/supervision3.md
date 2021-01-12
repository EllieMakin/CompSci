##### Ellie Makin (erm67)

### Data Science example sheet 2

1. 
```py
import numpy as np
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
```

2. 

(a) Prior likelihood = density function 
$$
= \frac d {d\theta} (1 - (b_0/\theta)^{a_0}) = \frac {a_0 b_0^{a_0}} {\theta^{a_0+1}}
$$

(b) 
$$
\begin{aligned}
P(\Theta | x_1..x_n) &= \kappa P(x_1..x_n | \Theta = \theta) P(\Theta = \theta) \\
&= \kappa \frac 1 {\theta^n} \frac {a_0 b_0^{a_0}} {\theta^{a_0+1}} \\
&= \frac {\kappa a_0 b_0^{a_0}} {\theta^{n + a_0 + 1}} \\
&= \frac {a_1 b_1^{a_1}} {\theta^{a_1+1}} \\
& \{ a_1 = a_0 + n, b_1 = \left({\frac {\kappa a_0 b_0^{a_0}}{a_1}}\right)^{1/a_1} \} \\
\end{aligned}
$$

(c) 
$$
\begin{aligned}
\Pr(\Theta \le \theta_1) &= 0.95\\
\therefore 1-(b_1/\theta_1)^{a_1} &= 0.95\\
(b_1/\theta_1)^{a_1} &= 0.05\\
\theta_1 &= b_1 0.05^{-1/a_1}\\
\text{interval: } & b_0 \le \theta \le \theta_1
\end{aligned}
$$

(d) 
$$
\begin{aligned}
\Pr(\Theta > \theta_0) &= 0.95\\
\Pr(\Theta \le \theta_0) &= 0.05\\
\therefore 1-(b_1/\theta_0)^{a_1} &= 0.05\\
(b_1/\theta_0)^{a_1} &= 0.95\\
\theta_0 &= b_1 0.95^{-1/a_1}\\
\text{interval: } & \theta > \theta_0
\end{aligned}
$$
The first interval is better, because it includes the most probable values for $\theta$, and is also limited to a finite range, unlike the second interval.

3. 

$$
\begin{aligned}
\Pr_M(\mu \mid x_1..x_n) &= \kappa \Pr(x_1..x_n \mid \mu) \Pr(\mu)\\
&= \kappa \Pr(\mu) \prod_{i=1}^n \Pr(x_i \mid \mu)\\
&= \kappa \frac{e^{-(\mu-\mu_0)^2/2\rho_0^2}}{\rho_0\sqrt{2\pi}} \prod_{i=1}^n \frac{e^{-(\mu-x_i)^2/2\sigma_0^2}}{\sigma_0\sqrt{2\pi}}\\
&= \kappa e^{-(\mu-\mu_0)^2/2\rho_0^2} \prod_{i=1}^n e^{-(\mu-x_i)^2/2\sigma_0^2}\\
&= \ldots
\end{aligned}
$$

There is probably a way to get this to work properly but it is too difficult.

4. 

```python
import numpy as np
import scipy.stats
import matplotlib.pyplot as plt

xs = [4.3, 2.8, 3.9, 4.1, 9, 4.5, 3.3]

mu_sample = np.random.normal(loc=0, scale=5, size=1000)
w = list(map(
    lambda a: sum(a),
    zip(*[
        0.99*scipy.stats.norm.pdf(x, loc=mu_sample, scale=0.5)
        + 0.01*scipy.stats.cauchy.pdf(x)
        for x in xs
    ])
))

plt.hist(mu_sample, weights=w, density=True, bins=50)
plt.show()
```

5. Probability model: 
$$
\mathtt{temp} \sim \alpha + \beta_1 \sin(2 \pi t) + \beta_2 \cos(2 \pi t) + \gamma(t-2000) + \mathrm{Normal}(0, \sigma^2)
$$

Prior distributions:

$$
\begin{aligned}
\alpha           &\sim \mathrm{Normal}(10, 5^2) \\
\beta_1, \beta_2 &\sim \mathrm{Normal}(0, 5^2) \\
\gamma           &\sim \mathrm{Normal}(0, 1^2) \\
\end{aligned}
$$
