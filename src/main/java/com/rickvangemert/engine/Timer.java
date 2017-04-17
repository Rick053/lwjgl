package com.rickvangemert.engine;

public class Timer {

    private double mLastLoopTime;

    public void init() {
        mLastLoopTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - mLastLoopTime);
        mLastLoopTime = time;
        return elapsedTime;
    }

    public double getLastLoopTime() {
        return mLastLoopTime;
    }
}