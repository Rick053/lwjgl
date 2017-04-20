package com.rickvangemert.engine;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final String mTitle;

    private int mWidth;

    private int mHeight;

    private long mWindowHandle;

    private boolean mResized;

    private boolean vSync;

    public Window(String title, int width, int height, boolean vSync) {
        this.mTitle = title;
        this.mWidth = width;
        this.mHeight = height;
        this.vSync = vSync;
        this.mResized = false;
    }

    public void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        // Create the window
        mWindowHandle = glfwCreateWindow(mWidth, mHeight, mTitle, NULL, NULL);
        if (mWindowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup resize callback
        glfwSetWindowSizeCallback(mWindowHandle, (window, width, height) -> {
            this.mWidth = width;
            this.mHeight = height;
            this.setResized(true);
        });

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(mWindowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                mWindowHandle,
                (vidmode.width() - mWidth) / 2,
                (vidmode.height() - mHeight) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(mWindowHandle);

        if (isvSync()) {
            // Enable v-sync
            glfwSwapInterval(1);
        }

        // Make the window visible
        glfwShowWindow(mWindowHandle);

        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);

//        Enable to see the polygon lines of the mesh
//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        // Support for transparencies
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }

    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(mWindowHandle, keyCode) == GLFW_PRESS;
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(mWindowHandle);
    }

    public String getTitle() {
        return mTitle;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public long getWindowHandle() {
        return mWindowHandle;
    }

    public boolean isResized() {
        return mResized;
    }

    public void setResized(boolean resized) {
        this.mResized = resized;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }

    public void update() {
        glfwSwapBuffers(mWindowHandle);
        glfwPollEvents();
    }
}
