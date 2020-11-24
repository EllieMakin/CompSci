##### Ellie Makin (erm67)

### Data Science supervision 1

#### Example sheet 1

1.

![](images/es1q1.png =600x)
<br>
<br>
<br>
<br>


![](images/es1q2.png =500x)

3.

```python
import scipy.optimize
import numpy as np

x = [3,2,8,1,5,0,8]
n = len(x)

def logPr(t):
    l = np.exp(t)   # use exponential transform so that l > 0
    return np.log(l)*sum(x) - n*l - sum(map(
        lambda x_i: np.log(np.math.factorial(x_i)),
        x
    ))


[tMax] = scipy.optimize.fmin(lambda t: -logPr(t), 1)

lMax = np.exp(tMax)     # undo the transform
print(lMax)
```
<br><br><br><br>
4.

![](images/es1q4.png =600x)



![](images/es1q5.png =600x)



![](images/es1q6.png =600x)

7.

We can use the following model formula to make sure that the two lines meet at the inflection point:

$$
f(x) = \begin{cases}
m_1(x-x_{inf})+c    &   x \le x_{inf}   \\
m_2(x-x_{inf})+c    &   x_{inf} < x
\end{cases}
$$

where $m_1$ and $m_2$ are the gradients of the two lines, $(x_{inf}, c)$ is the position of the inflection point. Assuming we are given the points as a labelled dataset $(x_1, y_1), \ldots, (x_n, y_n)$, we can adapt this to account for a random component:

$$
Y_i \sim \operatorname{Normal}(f(x_i), \sigma^2)
$$

This has likelihood

$$
\Pr(y_i; x_i, x_{inf}, m_1, m_2, c, \sigma) = \frac {1} {\sigma \sqrt{2 \pi}} e^{-(y_i - f(x_i))^2 / 2 \sigma ^2}
$$

which can then be $\log$'d and optimised numerically over parameters $m_1$, $m_2$, $c$, and $\sigma$ in order to fit the dataset, probably including an exponential transform over $\sigma$ to keep it positive.
