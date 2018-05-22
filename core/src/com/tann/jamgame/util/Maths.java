package com.tann.jamgame.util;

public class Maths {
	public static int mult(){
		return Math.random()>.5?1:-1;
	}
	
	public static float factor(float arg){
		return (float)(Math.random()*arg);
	}
	public static final float TAU = (float)Math.PI*2;
    public static float cos(float radians){
        return (float) Math.cos(radians);
    }
    public static float sin(float radians){
        return (float) Math.sin(radians);
    }

    public static float dist(int x, int y) {
        return (float) Math.sqrt(x*x+y*y);
    }
}
