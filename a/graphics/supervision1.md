# Introduction to Graphics - Supervision 1 exercises

## Warm up questions

1. *An image has the width of $w$ pixels, the height of $h$ pixels and is stored in a column-major order with interleaved RGB colour channels. Write the formula for the memory index of a pixel at the coordinates $(x, y)$.*

$3 (hx + y) + c$

2. *Explain the difference between sampling and quantization.*

Sampling is taking samples of a continuous function at discrete intervals; Quantisation is coercing the results of sampling into a finite bit depth, where the continuous range of the function is reduced to a finite number of possible values.

3. *Which depth cues can be reproduced on a 2D monitor and which require a special 3D display?*

Occlusion, relative size, shading, colour, relative brightness, shadow, foreshortening, distance to horizon, focus, familiar size, and texture gradient can all be used on a 2D monitor. Only parallax, or focal depth require a specialised 3D display.

4. *Explain why, in a typical ray tracing algorithm, the rays are traced from the eye to the scene and not the other way around.*

By tracing the rays from the eye, only the rays that are required to make the image are traced, which saves on computation time. If the rays were traced from light sources for example, it may be that most of the rays produced by the light sources do not collide with the camera screen.

5. *What is the value of the ray parameters at the intersection points between the ray $[1,1,1] +s[1,1,1]$ and the sphere centred at the origin with radius $1$?*

$O = \pmatrix{1\\1\\1} \\
D = \pmatrix{1\\1\\1} \\
C = \pmatrix{0\\0\\0} \\
r = 1 \\
a = D \cdot D = \pmatrix{1\\1\\1} \cdot \pmatrix{1\\1\\1} = 3 \\
b = 2D \cdot (O - C) = 2 \pmatrix{1\\1\\1} \cdot \pmatrix{1\\1\\1} = 6 \\
c = (O - C) \cdot (O - C) - r^2 = \pmatrix{1\\1\\1} \cdot \pmatrix{1\\1\\1} - 1 = 2 \\
s = \frac{-b \pm \sqrt{b^2 - 4ac}}{2a} = \frac{-6 \pm \sqrt{36 - 24}}{6} = 1 \pm \frac{\sqrt{2}}{2}$

6. *How to avoid aliasing when ray tracing?*

Trace several rays through each pixel and take an average, rather than just one. Known as super-sampling.

7. *What is the difference between a finite-aperture camera and a pinhole camera? How to simulate one and another in ray-tracing?*

A finite-aperture camera has an aperture that is a hole of some non-negligible size. A pinhole camera has an aperture that is small enough that it can be considered as just a point. In raytracing, a pinhole camera can be simulated by tracing all rays starting from the same point in the camera. A finite-aperture camera can be simulated by tracing several rays from random points in some aperture area, and taking the average of their results.

8. *We often use triangles in computer graphics to represent 3D objects. Why are they good? Why are they bad? Can you think of any alternatives?*

Triangles are good because the three points on their corners will always be coplanar, so they always define a flat surface (or fewer dimensions), which makes rendering simpler. They are limited, because in order to properly approximate a curved surface, many triangles must be used. An alternative would be to define curved surfaces such as spheres using vector equations, as we did for ray tracing, but this would require

9. *Why do we need to use different transformations for vertices and normal vectors?*

For normal vectors, only their direction is relevant - the position of the point they refer to in 3D space is irrelevant, so they will not change under translation or scaling. For vertices, their position is the important attribute, so they should be affected by translation and scaling.

## Longer questions

1. *Derive a formula for the intersection of a ray with a cylinder that has its base centered in $[x_b, y_b, z_b]$ and extends along $z$-axis so that the centre of its top is at $[x_b, y_b, z_b+h]$, where $h$ is the height of the cylinder. Derive only the formula for the intersection with the sides, not the base and the top. The ray equation is $[x, y, z] = [x_o, y_o, z_o] +s[x_d, y_d, z_d]$, where $[x_o, y_o, z_o]$ is the origin of the ray and $[x_d, y_d, z_d]$ is its direction.*

This question does not give a radius for the cylinder - suppose it has radius $r$.

The distance $d$ from a point at $[x, y, z]$ to the axis of the cylinder is given by $d^2 = (x - x_b)^2 + (y - y_b)^2$. We then have
$$
r^2 = (sx_d + x_o - x_b)^2 + (sy_d + y_o - y_b)^2 \\
= s^2x_d^2 + 2sx_d(x_o-x_b) + (x_o-x_b)^2 + s^2y_d^2 + 2sy_d(y_o-y_b) + (y_o-y_b)^2 \\
\therefore (x_d^2 + y_d^2)s^2 + 2(x_d(x_o-x_b)+y_d(y_o-y_b))s + ((x_o-x_b)^2 + (y_o-y_b)^2 - r^2) = 0 \\
$$

from which we can get $s$ by using the quadratic formula.

2. *Explain the Phong reflection model:*
- *Explain each reflection component.*
- *Why is there a cosine term in the diffuse component?*
- *What does the ambient illumination component approximate?*
- *When the camera moves, which of the reflection components change and which stay constant?*

The ambient component represents the light that is scattered evenly on all surfaces in the scene by ambient light. The diffuse component represents the light scattered by a surface when it is lit by a light source; The specular component represents glossy highlights, caused by reflection of a light source in a shiny surface, and will change depending on which direction the object is viewed from.

The cosine term in the diffuse component represents the way that light incoming at a steeper angle to a surface will have a lower intensity.

The ambient illumination term approximates light reflected from surrounding surfaces onto an abject.

The direction at which the object is viewed has no effect on the ambient or diffuse components, since they depend only on the colour of the material, and the incident angle of the light source, but the specular component will change, to account for the angle at which the light source is reflected.

3. *Explain how Ray tracing can achieve the following effects:*
- *reflections*
- *refraction*
- *shadows*

Reflections can be simulated by casting a new ray from the point where another ray hits a surface, in the direction that it would be reflected. The colour resulting from this new ray can then be blended with the colour from the surface shading, to give the colour at that point.

Refraction would work similarly to reflection, but now two new vectors must be cast: one for the reflected component, and one for the refracted compoent. The refracted ray must also then be traced differently than the reflected ray, as it will exit the object through another surface, or undergo total internal reflection.

Shadows can be simulated by checking if a point on a surface has a straight view to each light source or not, and adjusting the diffuse and specular components of the illumination model accordingly.

4. *Explain why we use homogeneous coordinates.*

So that we can interpolate colours across the surface of a rasterized triangle, without having to calculate the lighting at each point on the surface - only the 3 corners.

5. *Discuss the modelling, view and projection transformations used in a typical graphics pipeline.*

The modelling transformation is used to transform the local coordinates of vertices in an object model into global positions in the scene space. This transformation involved scaling the object correctly, setting its orientation, and its location, and wil be different for each object in the scene.

The view transformation translates the objects in the scene so that they are correctly positioned relative to the camera. This can also be thought of as rotating the camera so its correct orientation, but the camera stays stationary while the world moves around it, since this makes the following projection easier.

The projection transformation projects the vertices in 3D space onto the 2D screen of the camera, so that they can be rendered on a display. This transformation depends on various settings within the camera, such as FoV, zoom, screen shape, etc.
