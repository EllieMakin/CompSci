package uk.ac.cam.cl.gfxintro.erm67.tick1star;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BumpySphere extends Sphere {

    private float BUMP_FACTOR = 5f;
    private float[][] bumpMap;
    private int bumpMapHeight;
    private int bumpMapWidth;

    public BumpySphere(Vector3 position, double radius, ColorRGB colour, String bumpMapImg) {
        super(position, radius, colour);
        try {
            BufferedImage inputImg = ImageIO.read(new File(bumpMapImg));
            bumpMapHeight = inputImg.getHeight();
            bumpMapWidth = inputImg.getWidth();
            bumpMap = new float[bumpMapHeight][bumpMapWidth];
            for (int row = 0; row < bumpMapHeight; row++) {
                for (int col = 0; col < bumpMapWidth; col++) {
                    float height = (float) (inputImg.getRGB(col, row) & 0xFF) / 0xFF;
                    bumpMap[row][col] = BUMP_FACTOR * height;
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating bump map");
            e.printStackTrace();
        }
    }

    public double[] getDerivativeAt(int u, int v) {
        double du = bumpMap[v % bumpMapHeight][(u+1) % bumpMapWidth]
            - bumpMap[v % bumpMapHeight][u % bumpMapWidth];
        double dv = bumpMap[(v+1) % bumpMapHeight][u % bumpMapWidth]
            - bumpMap[v % bumpMapHeight][u % bumpMapWidth];
        return new double[] {du, dv};
    }

    // Get normal to surface at position
    @Override
    public Vector3 getNormalAt(Vector3 position) {
        Vector3 radialPosition = position.subtract(getPosition());

        Vector3 N = radialPosition.normalised();

        double r = radialPosition.magnitude();
        double u = Math.atan2(-radialPosition.x, radialPosition.z);
        double v = Math.acos(radialPosition.y/r);

        Vector3 P_u = new Vector3(
            -Math.sin(u),
            Math.cos(u)*Math.cos(v),
            Math.cos(u)*Math.sin(v)
        );

        Vector3 P_v = new Vector3(
            0,
            -Math.sin(v),
            Math.cos(v)
        );

        int u_map = (int)((u / Math.PI + 1) / 2 * bumpMapWidth);
        int v_map = (int)((v / Math.PI) * bumpMapHeight);

        double[] B = getDerivativeAt(u_map, v_map);
        double B_u = B[0];
        double B_v = B[1];

        N = N.add(N.cross(P_v).scale(B_u)).add(N.cross(P_u).scale(B_v)).normalised();

        return N;
    }
}
