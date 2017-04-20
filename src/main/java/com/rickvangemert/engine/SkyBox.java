package com.rickvangemert.engine;

import com.rickvangemert.engine.graph.Material;
import com.rickvangemert.engine.graph.Mesh;
import com.rickvangemert.engine.graph.OBJLoader;
import com.rickvangemert.engine.graph.Texture;

public class SkyBox extends GameItem {

    public SkyBox(String objModel, String textureFile) throws Exception {
        super();
        Mesh skyboxmesh = OBJLoader.loadMesh(objModel);
        Texture texture = new Texture(textureFile);
        skyboxmesh.setMaterial(new Material(texture, 0.0f));
        setMesh(skyboxmesh);
        setPosition(0, 0, 0);
    }

}
