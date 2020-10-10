package uk.ac.cam.cl.gfxintro.erm67.tick2;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.lang.Math;
import java.nio.FloatBuffer;;

public class CubeRobot {

    // Filenames for vertex and fragment shader source code
    private final static String VSHADER_FN = "resources/cube_vertex_shader.glsl";
    private final static String FSHADER_FN = "resources/cube_fragment_shader.glsl";

    // Components of this CubeRobot

    // Component 1 : Body
	private Mesh body_mesh;				// Mesh of the body
	private ShaderProgram body_shader;	// Shader to colour the body mesh
	private Texture body_texture;		// Texture image to be used by the body shader
	private Matrix4f body_transform;	// Transformation matrix of the body object

	// Component 2: Right Arm
    private Mesh armR_mesh;
    private ShaderProgram armR_shader;
    private Texture armR_texture;
    private Matrix4f armR_transform;

	// Complete rest of the robot
    private Mesh armL_mesh;
    private ShaderProgram armL_shader;
    private Texture armL_texture;
    private Matrix4f armL_transform;

    private Mesh legR_mesh;
    private ShaderProgram legR_shader;
    private Texture legR_texture;
    private Matrix4f legR_transform;

    private Mesh legL_mesh;
    private ShaderProgram legL_shader;
    private Texture legL_texture;
    private Matrix4f legL_transform;

    private Mesh head_mesh;
    private ShaderProgram head_shader;
    private Texture head_texture;
    private Matrix4f head_transform;

/**
 *  Constructor
 *  Initialize all the CubeRobot components
 */
	public CubeRobot() {
		// Create body node

		// Initialise Geometry
		body_mesh = new CubeMesh();

		// Initialise Shader
		body_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
		// Tell vertex shader where it can find vertex positions. 3 is the dimensionality of vertex position
		// The prefix "oc_" means object coordinates
		body_shader.bindDataToShader("oc_position", body_mesh.vertex_handle, 3);
		// Tell vertex shader where it can find vertex normals. 3 is the dimensionality of vertex normals
		body_shader.bindDataToShader("oc_normal", body_mesh.normal_handle, 3);
		// Tell vertex shader where it can find texture coordinates. 2 is the dimensionality of texture coordinates
		body_shader.bindDataToShader("texcoord", body_mesh.tex_handle, 2);

		// Initialise Texturing
		body_texture = new Texture();
		body_texture.load("resources/cubemap.png");

		// Build Transformation Matrix
		body_transform = new Matrix4f();
        body_transform = body_transform.scale(1, 2, 0.75f);


        // Create right arm node
        armR_mesh = new CubeMesh();
        armR_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
        armR_shader.bindDataToShader("oc_position", armR_mesh.vertex_handle, 3);
        armR_shader.bindDataToShader("oc_normal", armR_mesh.normal_handle, 3);
        armR_shader.bindDataToShader("texcoord", armR_mesh.tex_handle, 2);

		// Initialise Texturing
        armR_texture = new Texture();
        armR_texture.load("resources/cubemap.png");


		// Build Right Arm's Transformation Matrix (rotate the right arm around its end)
        armR_transform = new Matrix4f();
        armR_transform = armR_transform
                .translate(1.25f, 0, 0)
                .translate(-0.25f, 2, 0)
                .rotateAffineXYZ(0, 0, (float)Math.PI/16)
                .translate(0.25f, -2, 0)
                .scale(0.25f, 2.0f, 0.25f)
        ;


        armL_mesh = new CubeMesh();
        armL_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
        armL_shader.bindDataToShader("oc_position", armL_mesh.vertex_handle, 3);
        armL_shader.bindDataToShader("oc_normal", armL_mesh.normal_handle, 3);
        armL_shader.bindDataToShader("texcoord", armL_mesh.tex_handle, 2);

        armL_texture = new Texture();
        armL_texture.load("resources/cubemap.png");

        armL_transform = new Matrix4f();
        armL_transform = armL_transform
                .translate(-1.25f, 0, 0)
                .translate(0.25f, 2, 0)
                .rotateAffineXYZ(0, 0, (float)-Math.PI/16)
                .translate(-0.25f, -2, 0)
                .scale(0.25f, 2.0f, 0.25f)
        ;


        legR_mesh = new CubeMesh();
        legR_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
        legR_shader.bindDataToShader("oc_position", legR_mesh.vertex_handle, 3);
        legR_shader.bindDataToShader("oc_normal", legR_mesh.normal_handle, 3);
        legR_shader.bindDataToShader("texcoord", legR_mesh.tex_handle, 2);

        legR_texture = new Texture();
        legR_texture.load("resources/cubemap.png");

        legR_transform = new Matrix4f();
        legR_transform = legR_transform
                .translate(0.5f, -2.0f, 0)
                .scale(0.25f, 2.0f, 0.25f)
        ;


        legL_mesh = new CubeMesh();
        legL_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
        legL_shader.bindDataToShader("oc_position", legL_mesh.vertex_handle, 3);
        legL_shader.bindDataToShader("oc_normal", legL_mesh.normal_handle, 3);
        legL_shader.bindDataToShader("texcoord", legL_mesh.tex_handle, 2);

        legL_texture = new Texture();
        legL_texture.load("resources/cubemap.png");

        legL_transform = new Matrix4f();
        legL_transform = legL_transform
                .translate(-0.5f, -2.0f, 0)
                .scale(0.25f, 2.0f, 0.25f)
        ;


        head_mesh = new CubeMesh();
        head_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
        head_shader.bindDataToShader("oc_position", head_mesh.vertex_handle, 3);
        head_shader.bindDataToShader("oc_normal", head_mesh.normal_handle, 3);
        head_shader.bindDataToShader("texcoord", head_mesh.tex_handle, 2);

        head_texture = new Texture();
        head_texture.load("resources/cubemap_head.png");

        head_transform = new Matrix4f();
        head_transform = head_transform
                .translate(0, 3, 0)
                .scale(0.5f, 0.5f, 0.5f)
        ;


	}


	/**
	 * Updates the scene and then renders the CubeRobot
	 * @param camera - Camera to be used for rendering
	 * @param deltaTime		- Time taken to render this frame in seconds (= 0 when the application is paused)
	 * @param elapsedTime	- Time elapsed since the beginning of this program in millisecs
	 */
    public void render(Camera camera, float deltaTime, long elapsedTime) {

		// TODO: Animate Body. Translate the body as a function of time
        body_transform = new Matrix4f();
        body_transform = body_transform
                .rotateAffineXYZ(0, (float) Math.PI * elapsedTime / 5000, 0)
                .scale(1, 2, 0.75f);

        armR_transform = new Matrix4f();
        armR_transform = armR_transform
                .rotateAffineXYZ(0, (float) Math.PI * elapsedTime / 5000, 0)
                .translate(1.25f, 0, 0)
                .translate(-0.25f, 2, 0)
                .rotateAffineXYZ(0, 0, (float) (Math.PI / 3 * (1f + Math.sin(elapsedTime / 1000f))))
                .translate(0.25f, -2, 0)
                .scale(0.25f, 2.0f, 0.25f)
        ;

        armL_transform = new Matrix4f();
        armL_transform = armL_transform
                .rotateAffineXYZ(0, (float) Math.PI * elapsedTime / 5000, 0)
                .translate(-1.25f, 0, 0)
                .translate(0.25f, 2, 0)
                .rotateAffineXYZ(0, 0, (float)-(Math.PI / 3 * (1f + Math.sin(elapsedTime / 1000f))))
                .translate(-0.25f, -2, 0)
                .scale(0.25f, 2.0f, 0.25f)
        ;

        legR_transform = new Matrix4f();
        legR_transform = legR_transform
                .rotateAffineXYZ(0, (float) Math.PI * elapsedTime / 5000, 0)
                .translate(0.5f, -2.0f, 0)
                .scale(0.25f, 2.0f, 0.25f)
        ;

        legL_transform = new Matrix4f();
        legL_transform = legL_transform
                .rotateAffineXYZ(0, (float) Math.PI * elapsedTime / 5000, 0)
                .translate(-0.5f, -2.0f, 0)
                .scale(0.25f, 2.0f, 0.25f)
        ;

        head_transform = new Matrix4f();
        head_transform = head_transform
                .rotateAffineXYZ(0, (float) Math.PI * elapsedTime / 5000, 0)
                .translate(0, 3, 0)
                .scale(0.5f, 0.5f, 0.5f)
        ;


		renderMesh(camera, body_mesh, body_transform, body_shader, body_texture);
        renderMesh(camera, armR_mesh, armR_transform, armR_shader, armR_texture);
        renderMesh(camera, armL_mesh, armL_transform, armL_shader, armL_texture);
        renderMesh(camera, legR_mesh, legR_transform, legR_shader, legR_texture);
        renderMesh(camera, legL_mesh, legL_transform, legL_shader, legL_texture);
        renderMesh(camera, head_mesh, head_transform, head_shader, head_texture);


		//TODO: Render rest of the robot

	}

	/**
	 * Draw mesh from a camera perspective
	 * @param camera		- Camera to be used for rendering
	 * @param mesh			- mesh to render
	 * @param modelMatrix	- model transformation matrix of this mesh
	 * @param shader		- shader to colour this mesh
	 * @param texture		- texture image to be used by the shader
	 */
	public void renderMesh(Camera camera, Mesh mesh , Matrix4f modelMatrix, ShaderProgram shader, Texture texture) {
		// If shaders modified on disk, reload them
		shader.reloadIfNeeded();
		shader.useProgram();

		// Step 2: Pass relevant data to the vertex shader

		// compute and upload MVP
		Matrix4f mvp_matrix = new Matrix4f(camera.getProjectionMatrix()).mul(camera.getViewMatrix()).mul(modelMatrix);
		shader.uploadMatrix4f(mvp_matrix, "mvp_matrix");

		// Upload Model Matrix and Camera Location to the shader for Phong Illumination
		shader.uploadMatrix4f(modelMatrix, "m_matrix");
		shader.uploadVector3f(camera.getCameraPosition(), "wc_camera_position");

		// Transformation by a nonorthogonal matrix does not preserve angles
		// Thus we need a separate transformation matrix for normals
		Matrix3f normal_matrix = new Matrix3f(modelMatrix);

        normal_matrix.invert().transpose();

		shader.uploadMatrix3f(normal_matrix, "normal_matrix");

		// Step 3: Draw our VertexArray as triangles
		// Bind Texture
		texture.bindTexture();
		// draw
		glBindVertexArray(mesh.vertexArrayObj); // Bind the existing VertexArray object
		glDrawElements(GL_TRIANGLES, mesh.no_of_triangles, GL_UNSIGNED_INT, 0); // Draw it as triangles
		glBindVertexArray(0);             // Remove the binding

        // Unbind texture
		texture.unBindTexture();
	}
}
