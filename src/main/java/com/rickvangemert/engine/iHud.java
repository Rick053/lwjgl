package com.rickvangemert.engine;

public interface iHud {

    GameItem[] getItems();

    default void cleanup() {
        GameItem[] items = getItems();
        for (GameItem i : items) {
            i.getMesh().cleanUp();
        }
    }
}
