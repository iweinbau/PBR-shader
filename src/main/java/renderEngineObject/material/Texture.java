package renderEngineObject.material;

public class Texture {

    private int textureID;

    /**
     *
     * @param texture the texture id.
     */
    public Texture(int texture){
        this.textureID = texture;
    }

    /**
     *
     * @return int ID of the texture.
     */
    public int getID(){
        return textureID;
    }

}
