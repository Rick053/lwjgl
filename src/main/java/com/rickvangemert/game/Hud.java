package com.rickvangemert.game;

import com.rickvangemert.engine.Item;
import com.rickvangemert.engine.TextItem;
import com.rickvangemert.engine.Window;
import com.rickvangemert.engine.graph.Material;
import com.rickvangemert.engine.graph.Mesh;
import com.rickvangemert.engine.graph.OBJLoader;
import com.rickvangemert.engine.iHud;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Hud implements iHud {

    private static final int FONT_COLS = 16;

    private static final int FONT_ROWS = 16;

    private static final String FONT_TEXTURE = "/textures/font_texture.png";

    private final Item[] gameItems;

    private final TextItem statusTextItem;
    private final Item compassItem;

    public Hud(String statusText) throws Exception {
        this.statusTextItem = new TextItem(statusText, FONT_TEXTURE, FONT_COLS, FONT_ROWS);
        this.statusTextItem.getMesh().getMaterial().setAmbientColour(new Vector4f(1, 1, 1, 1));

        Mesh compassMesh = OBJLoader.loadMesh("/models/compass.obj");
        Material material = new Material();
        material.setAmbientColour(new Vector4f(1, 0, 0, 1));

        compassItem = new Item(compassMesh);
        compassItem.setScale(40.0f);
        compassItem.setRotation(0f, 0f, 180f);

        gameItems = new Item[]{statusTextItem, compassItem};
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    public void rotateCompass(float angle) {
        this.compassItem.setRotation(0, 0, 180 + angle);
    }

    @Override
    public Item[] getItems() {
        return gameItems;
    }

    public void updateSize(Window window) {
        this.statusTextItem.setPosition(10f, window.getHeight() - 50f, 0);
    }

}
