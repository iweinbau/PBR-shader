package shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import objects.PointLight;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;
import renderEngineObject.material.Material;

public class ShaderProgram {

  private final int programId;

  private int vertexShaderId;

  private int fragmentShaderId;

  private final Map<String, Integer> uniforms;

  public ShaderProgram() throws Exception {
    programId = glCreateProgram();
    if (programId == 0) {
      throw new Exception("Could not create Shader");
    }
    this.createVertexShader("vertexShader.glsl");
    this.createFragmentShader("fragmentShader.glsl");
    this.bindAttributes();
    this.link();
    uniforms = new HashMap<>();
  }

  public void createUniform(String uniformName) throws Exception {
    int uniformLocation = glGetUniformLocation(programId, uniformName);
    if (uniformLocation < 0) {
      throw new Exception("Could not find uniform:" + uniformName);
    }
    uniforms.put(uniformName, uniformLocation);
  }

  public void createDirectionalLightUniforms(String uniformName) throws Exception {
    createUniform(uniformName + ".color");
    createUniform(uniformName + ".position");
  }

  public void createMaterialUniform(String uniformName) throws Exception {
    //createUniform(uniformName + ".albedo");
    //createUniform(uniformName + ".metallic");
    //createUniform(uniformName + ".roughness");
    createUniform(uniformName + ".normalMap");
    createUniform(uniformName + ".albedoMap");
    createUniform(uniformName + ".roughnessMap");
    createUniform(uniformName + ".metallicMap");


  }

  public void setUniform(String uniformName, Matrix4f value) {
    try (MemoryStack stack = MemoryStack.stackPush()) {
      // Dump the matrix into a float buffer
      FloatBuffer fb = stack.mallocFloat(16);
      value.get(fb);
      glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
    }
  }

  protected void bindAttributes() {
    bindAttribute(0, "position");
    bindAttribute(1, "texCoord");
    bindAttribute(2, "vertexNormal");
    bindAttribute(3, "tangent");

  }

  public void setUniform(String uniformName, int value) {
    glUniform1i(uniforms.get(uniformName), value);
  }

  public void setUniform(String uniformName, float value) {
    glUniform1f(uniforms.get(uniformName), value);
  }

  public void setUniform(String uniformName, Vector3f value) {
    glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
  }

  public void setUniform(String uniformName, Vector4f value) {
    glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
  }

  public void setUniform(String uniformName, PointLight light) {
    setUniform(uniformName + ".color", light.getColor());
    setUniform(uniformName + ".position", light.getPosition());
  }

  public void setUniform(String uniformName, Material material) {
    //setUniform(uniformName + ".albedo", material.getAlbedo());
    //setUniform(uniformName + ".metallic", material.getMetallic());
    //setUniform(uniformName + ".roughness", material.getRoughness());
    setUniform(uniformName + ".albedoMap", 0);
    setUniform(uniformName + ".normalMap", 1);
    setUniform(uniformName + ".metallicMap", 2);
    setUniform(uniformName + ".roughnessMap", 3);
  }

  public void createVertexShader(String shaderCode) throws Exception {
    vertexShaderId = loadShader(shaderCode, GL_VERTEX_SHADER);
    glAttachShader(programId, vertexShaderId);
  }

  public void createFragmentShader(String shaderCode) throws Exception {
    fragmentShaderId = loadShader(shaderCode, GL_FRAGMENT_SHADER);
    glAttachShader(programId, fragmentShaderId);
  }

  /**
   *
   * @param attribute
   * @param variableName
   */
  protected void bindAttribute(int attribute, String variableName) {
    GL20.glBindAttribLocation(programId, attribute, variableName);
  }


  /**
   * @return int ID of the shader
   */
  private static int loadShader(String file, int type) {
    StringBuilder shaderSource = new StringBuilder();
    try {
      InputStream in = ShaderProgram.class.getResourceAsStream(file);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line;
      while ((line = reader.readLine()) != null) {
        shaderSource.append(line).append("//\n");
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
    int shaderID = GL20.glCreateShader(type);
    GL20.glShaderSource(shaderID, shaderSource);
    GL20.glCompileShader(shaderID);
    if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
      System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
      System.err.println("Could not compile shader!");
      System.exit(-1);
    }
    return shaderID;
  }

  public void link() throws Exception {
    glLinkProgram(programId);
    if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
      throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
    }

    if (vertexShaderId != 0) {
      glDetachShader(programId, vertexShaderId);
    }
    if (fragmentShaderId != 0) {
      glDetachShader(programId, fragmentShaderId);
    }

    glValidateProgram(programId);
    if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
      System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
    }
  }

  public void bind() {
    glUseProgram(programId);
  }

  public void unbind() {
    glUseProgram(0);
  }

  public void cleanup() {
    unbind();
    if (programId != 0) {
      glDeleteProgram(programId);
    }
  }
}
