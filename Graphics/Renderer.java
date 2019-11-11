package uk.ac.cam.cl.gfxintro.erm67.tick1star;

import java.awt.image.BufferedImage;
import java.util.List;

public class Renderer {

    // Distributed shadow ray constants
    private final int SHADOW_RAY_COUNT = 10; // no. of spawned shadowRays
    private final double LIGHT_SIZE = 0.4; // size of spherical light source

    // Distributed depth-of-field constants
    private final int DOF_RAY_COUNT = 50; // no. of spawned DoF rays
    private final double DOF_FOCAL_PLANE = 3.51805; // focal length of camera
    private final double DOF_AMOUNT = 0.1; // amount of DoF effect

    // The width and height of the image in pixels
    private int width, height;

    // Bias factor for reflected and shadow rays
    private final double EPSILON = 0.0001;

    // The number of times a ray can bounce for reflection
    private int bounces;

    // Background colour of the image
    private ColorRGB backgroundColor = new ColorRGB(0.001);

    public Renderer(int width, int height, int bounces) {
        this.width = width;
        this.height = height;
        this.bounces = bounces;
    }

    /*
     * Trace the ray through the supplied scene, returning the colour to be rendered.
     * The bouncesLeft parameter is for rendering reflective surfaces.
     */
    protected ColorRGB trace(Scene scene, Ray ray, int bouncesLeft) {

        // Find closest intersection of ray in the scene
        RaycastHit closestHit = scene.findClosestIntersection(ray);

        // If no object has been hit, return a background colour
        SceneObject object = closestHit.getObjectHit();
        if (object == null){
            return backgroundColor;
        }

        // Otherwise calculate colour at intersection and return
        // Get properties of surface at intersection - location, surface normal
        Vector3 P = closestHit.getLocation();
        Vector3 N = closestHit.getNormal();
        Vector3 O = ray.getOrigin();

        // Calculate direct illumination at the point
        ColorRGB directIllumination = this.illuminate(scene, object, P, N, O);

        // Get reflectivity of object
        double reflectivity = object.getReflectivity();

        if (bouncesLeft == 0 || reflectivity == 0) {
            // Base case - if no bounces left or non-reflective surface
            return directIllumination;
        }
        else {
            // Recursive case
            ColorRGB reflectedIllumination;

            Vector3 R = new Vector3(0).subtract(ray.getDirection()).reflectIn(N).normalised();

            Ray reflectedRay = new Ray(P.add(R.scale(EPSILON)), R);

            reflectedIllumination = trace(scene, reflectedRay, bouncesLeft - 1);
            //TODO: Calculate the direction R of the bounced ray
            //TODO: Spawn a reflectedRay with bias
            //TODO: Calculate reflectedIllumination by tracing reflectedRay

            // Scale direct and reflective illumination to conserve light
            directIllumination = directIllumination.scale(1.0 - reflectivity);
            reflectedIllumination = reflectedIllumination.scale(reflectivity);

            // Return total illumination
            return directIllumination.add(reflectedIllumination);
        }
    }

    /*
     * Illuminate a surface on and object in the scene at a given position P and surface normal N,
     * relative to ray originating at O
     */
    private ColorRGB illuminate(Scene scene, SceneObject object, Vector3 P, Vector3 N, Vector3 O) {

        ColorRGB colourToReturn = new ColorRGB(0);

        ColorRGB I_a = scene.getAmbientLighting(); // Ambient illumination intensity

        ColorRGB C_diff = object.getColour(); // Diffuse colour defined by the object

        // Get Phong coefficients
        double k_d = object.getPhong_kD();
        double k_s = object.getPhong_kS();
        double alpha = object.getPhong_alpha();

        colourToReturn = colourToReturn.add(C_diff.scale(I_a));

        // Loop over each point light source
        List<PointLight> pointLights = scene.getPointLights();
        for (int jLight = 0; jLight < pointLights.size(); jLight++) {
            PointLight light = pointLights.get(jLight); // Select point light

            for (int jRay = 0; jRay < SHADOW_RAY_COUNT; jRay++) {
                Vector3 lightPosition = light.getPosition().add(Vector3.randomInsideUnitSphere().scale(LIGHT_SIZE));

                // Calculate point light constants
                double distanceToLight = lightPosition.subtract(P).magnitude();
                ColorRGB C_spec = light.getColour();
                ColorRGB I = light.getIlluminationAt(distanceToLight);

                // Direction to light from surface point
                Vector3 L = lightPosition.subtract(P).normalised();
                // Direction to incident ray origin
                Vector3 V = O.subtract(P).normalised();
                // The reflection of L in N
                Vector3 R = L.reflectIn(N);

                // Cast shadow ray
                Ray shadowRay = new Ray(P.add(L.scale(EPSILON)), L);

                RaycastHit closestObstruction = scene.findClosestIntersection(shadowRay);
                double distanceToObject = closestObstruction.getDistance();

                if (distanceToObject > distanceToLight) {
                    ColorRGB diffuse = C_diff.scale(k_d).scale(I).scale(Math.max(0, N.dot(L)));
                    ColorRGB specular = C_spec.scale(k_s).scale(I).scale(Math.pow(Math.max(0, R.dot(V)), alpha));

                    colourToReturn = colourToReturn.add(diffuse.add(specular).scale(1/(double)SHADOW_RAY_COUNT));
                }
            }
        }
        return colourToReturn;
    }

    // Render image from scene, with camera at origin
    public BufferedImage render(Scene scene) {

        // Set up image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Set up camera
        Camera camera = new Camera(width, height);

        // Loop over all pixels
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Ray ray = camera.castRay(x, y); // Cast ray through pixel

                Plane focalPlane = new Plane(new Vector3(0, 0, DOF_FOCAL_PLANE), new Vector3(0, 0, 1), new ColorRGB(0));
                Vector3 focalPoint = focalPlane.intersectionWith(ray).getLocation();

                ColorRGB linearRGB = new ColorRGB(0);
                for (int jRay = 0; jRay < DOF_RAY_COUNT; jRay++) {
                    Vector3 aperturePoint = new Vector3(
                        (Math.random() * 2 - 1) * DOF_AMOUNT,
                        (Math.random() * 2 - 1) * DOF_AMOUNT,
                        0
                    );

                    Ray dofRay = new Ray(aperturePoint, focalPoint.subtract(aperturePoint).normalised());
                    linearRGB = linearRGB.add(trace(scene, dofRay, bounces).scale(1/(double)DOF_RAY_COUNT)); // Trace path of cast ray and determine colour
                }
                ColorRGB gammaRGB = tonemap( linearRGB );
                image.setRGB(x, y, gammaRGB.toRGB()); // Set image colour to traced colour
            }
            // Display progress every 10 lines
            if( y % 10 == 0 )
                System.out.println(String.format("%.2f", 100 * y / (float) (height - 1)) + "% completed");
        }
        return image;
    }


    // Combined tone mapping and display encoding
    public ColorRGB tonemap( ColorRGB linearRGB ) {
        double invGamma = 1./2.2;
        double a = 2;  // controls brightness
        double b = 1.3; // controls contrast

        // Sigmoidal tone mapping
        ColorRGB powRGB = linearRGB.power(b);
        ColorRGB displayRGB = powRGB.scale( powRGB.add(Math.pow(0.5/a,b)).inv() );

        // Display encoding - gamma
        ColorRGB gammaRGB = displayRGB.power( invGamma );

        return gammaRGB;
    }


}
