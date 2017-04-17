package com.rickvangemert.engine;


public interface GameLogic {

    void init(Window window) throws Exception;

    void input(Window window, MouseInput input);

    void update(float interval, MouseInput input);

    void render(Window window);

    void cleanup();
}