package engine;

import engine.graph.Mesh;
import org.joml.Vector3f;

public class Item {

    private final Mesh mMesh;

    private final Vector3f mPosition;

    private float mScale;

    private final Vector3f mRotation;

    public Item(Mesh mesh) {
        this.mMesh = mesh;
        mPosition = new Vector3f(0, 0, 0);
        mScale = 1;
        mRotation = new Vector3f(0, 0, 0);
    }

    public Vector3f getPosition() {
        return mPosition;
    }

    public void setPosition(float x, float y, float z) {
        this.mPosition.x = x;
        this.mPosition.y = y;
        this.mPosition.z = z;
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float scale) {
        this.mScale = scale;
    }

    public Vector3f getRotation() {
        return mRotation;
    }

    public void setRotation(float x, float y, float z) {
        this.mRotation.x = x;
        this.mRotation.y = y;
        this.mRotation.z = z;
    }

    public Mesh getMesh() {
        return mMesh;
    }
}