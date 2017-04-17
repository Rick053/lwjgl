package com.rickvangemert.engine.graph;

import com.rickvangemert.engine.Item;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    private final Matrix4f mProjectionMatrix;

    private final Matrix4f mModelViewMatrix;

    private final Matrix4f mViewMatrix;

    public Transformation() {
        mModelViewMatrix = new Matrix4f();
        mProjectionMatrix = new Matrix4f();
        mViewMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;
        mProjectionMatrix.identity();
        mProjectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
        return mProjectionMatrix;
    }

    public Matrix4f getModelViewMatrix(Item gameItem, Matrix4f viewMatrix) {
        Vector3f rotation = gameItem.getRotation();
        mModelViewMatrix.identity().translate(gameItem.getPosition()).
                rotateX((float)Math.toRadians(-rotation.x)).
                rotateY((float)Math.toRadians(-rotation.y)).
                rotateZ((float)Math.toRadians(-rotation.z)).
                scale(gameItem.getScale());
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(mModelViewMatrix);
    }

    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();

        mViewMatrix.identity();
        // First do the rotation so camera rotates over its position
        mViewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        mViewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return mViewMatrix;
    }
}