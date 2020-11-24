##### Ellie Makin (erm67)

### Further Graphics supervision 1

#### Lecture 1-3

1. Parametric surfaces are surfaces parametrised by two real parameters using some mapping function. Implicit surfaces are the zero set of some function of $\mathbb{R}^3$. Point set surfaces are not defined by an equation, but just a set of points, usually gathered from a real-life source object.
- Parametric surfaces can be mapped to textures more easily.
- It is easier to detect if a point is inside or outside an implicit surface, as well as proximity.
- Implicit surfaces can be easily combined.

2. An exponential spiral, centred on the origin.

3. The surface normal for a parametric surface with a mapping function $s(u,v)$ is given by
$$
\mathbf{\hat n} = \frac{\mathbf{s}_u \times \mathbf{s}_v}{||\mathbf{s}_u \times \mathbf{s}_v||}
$$
where
$$
\mathbf{s}_u = \frac{\partial s(u,v)}{\partial u},
\mathbf{s}_v = \frac{\partial s(u,v)}{\partial v}
$$
For the torus equation given, we have
$$
\mathbf{s}_u = \begin{bmatrix}
-(3+\sqrt{2} \cos v) \sin u \\
 (3+\sqrt{2} \cos v) \cos u \\
 0
\end{bmatrix}
= \begin{bmatrix}
-2 \sqrt{3} \\
 2          \\
 0
\end{bmatrix}\\
\mathbf{s}_v = \begin{bmatrix}
-\sqrt{2} \sin v \cos u \\
-\sqrt{2} \sin v \sin u \\
 \sqrt{2} \cos v
\end{bmatrix}
= \begin{bmatrix}
-1/2        \\
-\sqrt{3}/2 \\
 1
\end{bmatrix}\\
\therefore \mathbf{\hat n} = \frac{1}{4 \sqrt 2}\begin{bmatrix}
2 \\
2 \sqrt 3 \\
4
\end{bmatrix}
$$

4. Parametric equation:
$$
s(\theta, \phi) = \begin{bmatrix}
2 \cos \theta \sin \phi \\
3 \sin \theta \cos \phi \\
2 \cos \phi
\end{bmatrix}
$$
$P=(2,3,-2)$ does not lie on the surface, since
$$
\frac{2^2} 4 + \frac{3^2} 9 + \frac{(-2)^2} 4 - 1 \neq 0
$$
There is therefore no surface normal at $P$.

5. Since $f(x,y) = 0$ has $2$ lines of solutions that intersect at $(0,0)$, we may encounter unexpected behaviour if we don't take this into account.

6. A closed 2-manifold is a surface that is homeomorphic to a disk at all points. A manifold with boundaries is a manifold where each boundary point is homeomorphic to a half-disk.

7. I don't understand this question.

8. 
$$
\Delta(e^{-(x - c)^2/s^2}) = \frac{4 (x - c)^2 e^{-(x - c)^2/s^2}}{s^4} - \frac{2 e^{-(x - c)^2/s^2}}{s^2} \\
\Delta(\sin(x^T d)) = dT(T - 1)x^{T - 2} \cos(d x^T) - d^2 T^2 x^{2 T - 2} \sin(x^Td)
$$

#### Lecture 4-5

1. 
a) Motion capture, key-framing, video based, etc.
b) Rigging is where a model is given a skeleton, which is stored as the joint vertices. The model is then moved according to the movements of the skeleton.
c) If the initial positions of the points are $p_{-1}, p_0, p_1$, then their new positions will be $p_{-1},\frac 1 2 Tp_0, Tp_1$

2. 
a) Matrix transformations blend translations, but do not blend rotations correctly. Dual quaternions blend both translations and rotations well.
b) I don't know.

3. I cannot do either of these questions.
