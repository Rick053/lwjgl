package com.rickvangemert.game;

import com.rickvangemert.engine.Engine;
import com.rickvangemert.engine.GameLogic;

public class Main {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");

        try {
            boolean vSync = true;
            GameLogic gameLogic = new Game();
            Engine gameEng = new Engine("GAME", 1280, 720, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}