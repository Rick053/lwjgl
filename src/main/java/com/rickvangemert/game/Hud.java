package com.rickvangemert.game;

import com.rickvangemert.engine.graph.items.GameItem;
import com.rickvangemert.engine.graph.items.TextItem;
import com.rickvangemert.engine.Window;
import com.rickvangemert.engine.graph.FontTexture;
import com.rickvangemert.engine.graph.Material;
import com.rickvangemert.engine.graph.Mesh;
import com.rickvangemert.engine.graph.OBJLoader;
import com.rickvangemert.engine.iHud;
import org.joml.Vector4f;

import java.awt.*;

public class Hud implements iHud {

    private static final Font FONT = new Font("Arial", Font.PLAIN, 20);

    private static final String CHARSET = "ISO-8859-1";

    private static final int FONT_COLS = 16;

    private static final int FONT_ROWS = 16;

    private static final String FONT_TEXTURE = "/textures/font_texture.png";

    private final GameItem[] gameItems;

    private final TextItem statusTextItem;
    private final GameItem compassItem;

    public Hud(String statusText) throws Exception {
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        this.statusTextItem = new TextItem(statusText, fontTexture);
        this.statusTextItem.getMesh().getMaterial().setAmbientColour(new Vector4f(1, 1, 1, 1));

        Mesh compassMesh = OBJLoader.loadMesh("/models/compass.obj");
        Material material = new Material();
        material.setAmbientColour(new Vector4f(1, 0, 0, 1));
        compassMesh.setMaterial(material);

        compassItem = new GameItem(compassMesh);
        compassItem.setScale(40.0f);
        compassItem.setRotation(0f, 0f, 180f);

        gameItems = new GameItem[]{statusTextItem, compassItem};
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    public void rotateCompass(float angle) {
        this.compassItem.setRotation(0, 0, 180 + angle);
    }

    @Override
    public GameItem[] getItems() {
        return gameItems;
    }

    public void updateSize(Window window) {
        this.statusTextItem.setPosition(10f, window.getHeight() - 50f, 0);
        this.compassItem.setPosition(window.getWidth() - 40f, 50f, 0);
    }

}
