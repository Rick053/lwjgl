package com.rickvangemert.engine;

import com.rickvangemert.engine.graph.items.GameItem;

public interface iHud {

    GameItem[] getItems();

    default void cleanup() {
        GameItem[] items = getItems();
        for (GameItem i : items) {
            i.getMesh().cleanUp();
        }
    }
}
