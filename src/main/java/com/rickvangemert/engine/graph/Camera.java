package com.rickvangemert.engine.graph;

import org.joml.Vector3f;

public class Camera {

    private final Vector3f mPosition;

    private final Vector3f mRotation;

    public Camera() {
        mPosition = new Vector3f(0, 0, 0);
        mRotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.mPosition = position;
        this.mRotation = rotation;
    }

    public Vector3f getPosition() {
        return mPosition;
    }

    public void setPosition(float x, float y, float z) {
        mPosition.x = x;
        mPosition.y = y;
        mPosition.z = z;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            mPosition.x += (float)Math.sin(Math.toRadians(mRotation.y)) * -1.0f * offsetZ;
            mPosition.z += (float)Math.cos(Math.toRadians(mRotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            mPosition.x += (float)Math.sin(Math.toRadians(mRotation.y - 90)) * -1.0f * offsetX;
            mPosition.z += (float)Math.cos(Math.toRadians(mRotation.y - 90)) * offsetX;
        }
        mPosition.y += offsetY;
    }

    public Vector3f getRotation() {
        return mRotation;
    }

    public void setRotation(float x, float y, float z) {
        mRotation.x = x;
        mRotation.y = y;
        mRotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        mRotation.x += offsetX;
        mRotation.y += offsetY;
        mRotation.z += offsetZ;
    }
}