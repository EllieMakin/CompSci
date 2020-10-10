# Data Science

## Probability Modelling

### Maximum Likelihood Estimation

Given a model, the likelihood of this model being correct is the probability of seeing the observed data using this model.

In order to fit, the model, we use maximum likelihood estimation. The **maximum likelihood estimator** (mle) is the parameter value which maximizes the likelihood.

If we have a simple enough likelihood function, we can do this simply by differentiating the function and finding the maximum (taking into account the bounds for the parameter).

However, if this function is too complicated, or not known in an explicit form, we can use gradient descent instead. This is conveniently included in the `scipy` library:

```py
import scipy.optimize

def f(x):
    return x*(x - 1)

x0 = 0
xMin = scipy.optimize.fmin(f, x0)
```

#### Transforms

If the function has certain constraints on its parameters, we can use transforms in order to satisfy these constraints when optimizing.

For example, if we want to find tha maximum for $\sigma > 0$ of

$$
f(\sigma) = \frac{1}{\sqrt{2 \pi \sigma^2}}e^{-3/2\sigma^2}
$$

then we can use the transform $\sigma = e^\tau$, and then optimise over $\tau \in \mathbb{R}$. This exponentiation has the effect of transforming the inputs so that $\sigma > 0$ is always satisfied.

##### Softmax

If we have a function that requires a vector of inputs in $(0,1]$ which sum to $1$, then we can use the **softmax** transform to achieve this.

For an input vector $v$, the transformed vector $\operatorname{softmax}(v)$ is defined by:

$$
\operatorname{softmax}(v_i) = \frac {e^{v_i}} {\sum_{k=0}^{|v|}e^{v_k}}
$$

This allows us to optimise over $v_i \in \mathbb{R}$ without constraint, since now the softmaxed vector will satisfy the constraints for any input vector.
