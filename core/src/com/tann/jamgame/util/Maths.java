package com.tann.jamgame.util;

import com.badlogic.gdx.math.Vector2;

public class Maths {
    public static Vector2 v = new Vector2();
    public static float distance(float x1, float y1, float x2, float y2){
        float xDiff = x1-x2;
        float yDiff = y1-y2;
        return (float) Math.sqrt(xDiff*xDiff+yDiff*yDiff);
    }
    public static float angleDiff(float a1, float a2)
    {
        double difference = a1 - a2;
        while (difference < -Math.PI) difference += Math.PI*2;
        while (difference > Math.PI) difference -= Math.PI*2;
        return (float)difference;
    }
}
