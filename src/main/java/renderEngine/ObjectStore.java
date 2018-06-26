package renderEngine;

import renderEngineObject.gameobjects.GameObject;
import renderEngineObject.gameobjects.GameObject3D;
import renderEngineObject.gameobjects.Mesh;

import java.util.HashMap;
import java.util.Map;

public class ObjectStore {


    private static HashMap<Mesh,HashMap<String, GameObject>> modelHashMap = new HashMap<>();

    //Here we have to create a batch of objects with the same mesh. so we only have to bind the mesh once

    //we create a prefab3D.
    public static void addGameObject(GameObject gameObject){

        gameObject.createMesh();

        Mesh mesh = gameObject.getMesh();

        //if there is no hash map with the same mesh then create one
        if(modelHashMap.get(mesh) == null) {
            HashMap<String, GameObject> newHashMap = new HashMap<>();
            newHashMap.put(gameObject.getID(),gameObject);
            modelHashMap.put(mesh, newHashMap);
        }else{
            modelHashMap.get(mesh).put(gameObject.getID(),gameObject);
            //create new game object from child.
        }
        for (GameObject child : gameObject.getChildren()) {
            child.createMesh();
            addGameObject(child);
        }
    }

    public static GameObject getGameObjectFromID(String ID){
        for (Map.Entry<Mesh, HashMap<String, GameObject>> objMap: modelHashMap.entrySet()){
            HashMap<String, GameObject> map = objMap.getValue();
            for (Map.Entry<String, GameObject> obj: map.entrySet()) {
                String id = obj.getKey();
                if(id == ID){
                    GameObject gameObj = obj.getValue();
                    return  gameObj;
                }

            }
        }
        return null;
    }

    public static HashMap<Mesh,HashMap<String, GameObject>> getModelHashMap() {
        return modelHashMap;
    }
}
