package com.tann.jamgame.util;

public class Maths {
    public static float distance(float x1, float y1, float x2, float y2){
        float xDiff = x1-x2;
        float yDiff = y1-y2;
        return (float) Math.sqrt(xDiff*xDiff+yDiff*yDiff);
    }
}
