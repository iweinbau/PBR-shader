package renderEngineObject.gameobjects;

public class Mesh {

    private int vaoID;
    private int vertexCount;

    /**
     *
     * @param vaoID the id of the linked vao.
     * @param vertexCount the number of vertices.
     */
    public Mesh(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    /**
     *
     * @return Get the VAO id.
     */
    public int getVaoID() {
        return vaoID;
    }

    /**
     *
     * @return get the vertex count.
     */
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public boolean equals(Object obj) {
        if ( ! (obj instanceof Mesh))
            return false;
        return vaoID == ((Mesh) obj).getVaoID();
    }
}
