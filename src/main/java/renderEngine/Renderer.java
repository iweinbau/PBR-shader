package renderEngine;

import camera.Camera;
import java.awt.Point;
import objects.DirectionalLight;
import objects.Light;
import objects.PointLight;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import renderEngineObject.gameobjects.Mesh;
import renderEngineObject.gameobjects.GameObject;

import java.util.HashMap;
import java.util.Map;
import renderEngineObject.material.Material;
import shader.ShaderProgram;
import utils.transforms.Transformation;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer{

    private final Transformation transformation;

    private ShaderProgram shaderProgram;

    public Renderer() {
        transformation = new Transformation();
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        glEnable(GL11.GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void init() throws Exception {
        // Create shader
        shaderProgram = new ShaderProgram();

        // Create uniforms for modelView and projection matrices and texture
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("viewMatrix");
        shaderProgram.createUniform("transformationMatrix");
        shaderProgram.createDirectionalLightUniforms("light");
        shaderProgram.createMaterialUniform("material");
        shaderProgram.createUniform("viewPosition");
    }

    public void render(Camera camera, PointLight light) {

        clear();

        shaderProgram.bind();

        // Update projection Matrix
        Matrix4f projectionMatrix = camera.getProjectionMatrix();
        Matrix4f viewMatrix = camera.getViewMatrix();


        shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        shaderProgram.setUniform("viewMatrix", viewMatrix);
        shaderProgram.setUniform("light", light);
        shaderProgram.setUniform("viewPosition",camera.getPosition());

        // Update view Matrix

        // Render each gameItem
        for (Map.Entry<Mesh,HashMap<String, GameObject>> gameObjects : ObjectStore.getModelHashMap().entrySet()) {
            for (Map.Entry<String, GameObject> gameObject: gameObjects.getValue().entrySet()) {
                // Set model view matrix for this item
                bindMesh(gameObject.getValue().getMesh());
                Matrix4f modelViewMatrix = transformation.getModelMatrix(gameObject.getValue());
                shaderProgram.setUniform("transformationMatrix", modelViewMatrix);
                // Render the mesh for this game item
                render(gameObject.getValue());
                unbindMesh();
            }
        }

        shaderProgram.unbind();
    }


    private void render(GameObject gameObject){
        Material material = gameObject.getMaterial();
        Mesh mesh = gameObject.getMesh();

        shaderProgram.setUniform("material",material);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);

        if(material.isTextured()){
            glActiveTexture(GL_TEXTURE0);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, material.getTexture().getID());
        }
        if(material.hasNormalMap()){
            glActiveTexture(GL_TEXTURE1);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, material.getNormalMap().getID());
        }
        if(material.hasMetallicMap()){
            glActiveTexture(GL_TEXTURE2);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, material.getMetallicMap().getID());
        }
        if(material.hasRoughnesMap()){
            glActiveTexture(GL_TEXTURE3);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, material.getRoughnessMap().getID());
        }
        glDrawElements(GL_TRIANGLES,mesh.getVertexCount(),GL_UNSIGNED_INT, 0);

        glEnableVertexAttribArray(3);

        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);

    }
    private static void bindMesh(Mesh mesh){
        glBindVertexArray(mesh.getVaoID());
    }

    private static void unbindMesh(){
        glBindVertexArray(0);
    }
}
