package objConverter;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;

public class Vertex {
	
	private static final int NO_INDEX = -1;
	
	private Vector3f position;
	private int textureIndex = NO_INDEX;
	private int normalIndex = NO_INDEX;
	private Vertex duplicateVertex = null;
	private int index;
	private float length;
	private List<Vector3f> tangents = new ArrayList<Vector3f>();
	private Vector3f averagedTangent = new Vector3f(0, 0, 0);
	
	protected Vertex(int index, Vector3f position){
		this.index = index;
		this.position = position;
		this.length = position.length();
	}
	
	protected void addTangent(Vector3f tangent){
		tangents.add(tangent);
	}
	
	//NEW
	protected Vertex duplicate(int newIndex){
		Vertex vertex = new Vertex(newIndex, position);
		vertex.tangents = this.tangents;
		return vertex;
	}
	
	protected void averageTangents(){
		if(tangents.isEmpty()){
			return;
		}
		for(Vector3f tangent : tangents){
			averagedTangent.add(tangent);
		}
		averagedTangent.normalize();
	}
	
	protected Vector3f getAverageTangent(){
		return averagedTangent;
	}
	
	protected int getIndex(){
		return index;
	}
	
	protected float getLength(){
		return length;
	}
	
	protected boolean isSet(){
		return textureIndex!=NO_INDEX && normalIndex!=NO_INDEX;
	}
	
	protected boolean hasSameTextureAndNormal(int textureIndexOther,int normalIndexOther){
		return textureIndexOther==textureIndex && normalIndexOther==normalIndex;
	}
	
	protected void setTextureIndex(int textureIndex){
		this.textureIndex = textureIndex;
	}
	
	protected void setNormalIndex(int normalIndex){
		this.normalIndex = normalIndex;
	}

	protected Vector3f getPosition() {
		return position;
	}

	protected int getTextureIndex() {
		return textureIndex;
	}

	protected int getNormalIndex() {
		return normalIndex;
	}

	protected Vertex getDuplicateVertex() {
		return duplicateVertex;
	}

	protected void setDuplicateVertex(Vertex duplicateVertex) {
		this.duplicateVertex = duplicateVertex;
	}

}
