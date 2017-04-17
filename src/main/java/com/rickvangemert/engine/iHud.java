package com.rickvangemert.engine;

public interface iHud {

    Item[] getItems();

    default void cleanup() {
        Item[] items = getItems();
        for (Item i : items) {
            i.getMesh().cleanUp();
        }
    }
}
