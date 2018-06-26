package loader;

import static org.lwjgl.opengl.ARBInternalformatQuery2.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import renderEngineObject.gameobjects.Mesh;

/**
 * This class will help me to load objects into VAO based on float array with the positions of the
 * vertices, the texture coordinates, and the indices array.
 *
 * It can also load textures from a file path or from a byte array.
 */
public class Loader {

  private static List<Integer> vaos = new ArrayList<Integer>();
  private static List<Integer> vbos = new ArrayList<Integer>();
  private static List<Integer> textures = new ArrayList<Integer>();

  /**
   * @param positions List of vertex positions in 3d model.
   * @param textureCoords UV map of the model.
   * @param indices indices array.
   * @return a new Prefab3D obj.
   */
  public static Mesh loadToVAO(float[] positions, float[] textureCoords, float[] normals,
      float[] tangents, int[] indices) {
    int vaoID = createVAO();
    bindIndicesBuffer(indices);
    storeDataInAttributeList(0, 3, positions);
    storeDataInAttributeList(1, 2, textureCoords);
    storeDataInAttributeList(2, 3, normals);
    storeDataInAttributeList(3, 3, tangents);
    unbindVAO();
    return new Mesh(vaoID, indices.length);
  }

  /**
   * @param positions List of vertex positions in 3d model.
   * @param textureCoords UV map of the model.
   * @param indices indices array.
   * @return a new Prefab3D obj.
   */
  public static Mesh loadToVAO(float[] positions, float[] textureCoords,
      int[] indices) {
    int vaoID = createVAO();
    bindIndicesBuffer(indices);
    storeDataInAttributeList(0, 3, positions);
    storeDataInAttributeList(1, 2, textureCoords);
    unbindVAO();
    return new Mesh(vaoID, indices.length);
  }

  public static Mesh loadToVAO(float[] positions, int[] indices) {
    int vaoID = createVAO();
    bindIndicesBuffer(indices);
    storeDataInAttributeList(0, 3, positions);
    unbindVAO();
    return new Mesh(vaoID, indices.length);
  }

  public static Mesh loadToVAO(float[] positions) {
    int vaoID = createVAO();
    storeDataInAttributeList(0, 2, positions);
    unbindVAO();
    return new Mesh(vaoID, positions.length / 2);
  }

  public static int loadToVAO(float[] positions, float[] textureCoords) {
    int vaoID = createVAO();
    storeDataInAttributeList(0, 2, positions);
    storeDataInAttributeList(1, 2, textureCoords);
    unbindVAO();
    return vaoID;
  }

  /**
   * @param path of the image.
   * @return int ID of the texture.
   */
  public static int loadTexture(String path) {
    int[] pixels = null;
    int width = 0;
    int height = 0;
    try {
      InputStream resourceBuff = Loader.class.getResourceAsStream(path);
      BufferedImage image = ImageIO.read(resourceBuff);
      width = image.getWidth();
      height = image.getHeight();
      pixels = new int[width * height];
      image.getRGB(0, 0, width, height, pixels, 0, width);
    } catch (IOException e) {
      e.printStackTrace();
    }

    int[] data = new int[width * height];
    for (int i = 0; i < width * height; i++) {
      int a = (pixels[i] & 0xff000000) >> 24;
      int r = (pixels[i] & 0xff0000) >> 16;
      int g = (pixels[i] & 0xff00) >> 8;
      int b = (pixels[i] & 0xff);

      data[i] = a << 24 | b << 16 | g << 8 | r;
    }

    int result = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, result);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE,
        storeDataInIntBuffer((data)));
    glBindTexture(GL_TEXTURE_2D, 0);
    return result;
  }

  public static int generateTexture(byte[] data, int width, int height) {
    ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
    buffer.put(data);
    buffer.position(0);
    int result = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, result);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
    glBindTexture(GL_TEXTURE_2D, 0);
    return result;
  }

  /**
   * clean up method.
   */
  public static void cleanUp() {
    for (int vao : vaos) {
      GL30.glDeleteVertexArrays(vao);
    }
    for (int vbo : vbos) {
      GL15.glDeleteBuffers(vbo);
    }
    for (int texture : textures) {
      GL11.glDeleteTextures(texture);
    }
  }

  /**
   * @return int with the new created vao.
   */
  private static int createVAO() {
    int vaoID = GL30.glGenVertexArrays();
    vaos.add(vaoID);
    GL30.glBindVertexArray(vaoID);
    return vaoID;
  }

  /**
   * @param attributeNumber the index of in a vao objects.
   * @param coordinateSize the size of the vectors. for vertices 3, UV map 2.
   * @param data the data to store in the vbo of the vao
   */
  private static void storeDataInAttributeList(int attributeNumber, int coordinateSize,
      float[] data) {
    int vboID = GL15.glGenBuffers();
    vbos.add(vboID);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
    FloatBuffer buffer = storeDataInFloatBuffer(data);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
  }

  /**
   * unbind the vao.
   */
  private static void unbindVAO() {
    GL30.glBindVertexArray(0);
  }

  /**
   * @param indices bind the indices array to the vao objects.
   */
  private static void bindIndicesBuffer(int[] indices) {
    int vboID = GL15.glGenBuffers();
    vbos.add(vboID);
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
    IntBuffer buffer = storeDataInIntBuffer(indices);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
  }

  /**
   * @param data the data to store in the buffer.
   * @return an IntBuffer objects.
   */
  private static IntBuffer storeDataInIntBuffer(int[] data) {
    IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  /**
   * @param data the data to store.
   * @return a Float buffer objects.
   */
  private static FloatBuffer storeDataInFloatBuffer(float[] data) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }


}
