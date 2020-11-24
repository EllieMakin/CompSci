# Further Graphics

## Geometry representations

### Parametric surfaces

Parametric surfaces are surfaces parametrised by two variables. This is done using a mapping function:

$$
f: X \rightarrow Y \{ X \subseteq \mathbb{R}^2, Y \subseteq \mathbb{R}^3 \}
$$

The tangent components for a surface given by the function $s(u,v)$ are

$$
\mathbf{s}_u = \frac{\partial s(u,v)}{\partial u},
\mathbf{s}_v = \frac{\partial s(u,v)}{\partial v}
$$

The unit normal for the surface is then 

$$
\mathbf{\hat n} = \frac{\mathbf{s}_u \times \mathbf{s}_v}{||\mathbf{s}_u \times \mathbf{s}_v||}
$$

Advantages

+ Easy to generate points on a curve/surface
+ Easy point-wise differential properties
+ Easy to control by hand

Disadvantages

- Hard to determine inside/outside
- Hard to determine if a point is on a curve/surface
- Hard to generate by reverse engineering

### 

## Something else

### Laplace Operator



$$
\Delta f = \operatorname{div} \nabla f = \frac{\partial^2 f}{\partial x^2} + \frac{\partial^2 f}{\partial y^2} + \cdots
$$

### Laplace-Beltrami Operator

$$
\Delta_\mathcal{M} f = \operatorname{div}_\mathcal{M} \nabla_\mathcal{M} f
$$

#### Discrete Laplace-Beltrami
