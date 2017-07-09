package com.tann.jamgame.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

public class Colours {

	
	
	public static final Color black = Color.BLACK;
    public static final Color white = Color.WHITE;
    public static final Color blue = new Color(.3f,.4f,.8f,1);
    public static final Color light = new Color(.9f,.91f,.94f,1);
    public static final Color dark = new Color(.01f,.05f,.08f,1);
    public static final Color green = new Color(.2f,.6f, .31f,1);
    public static final Color red = new Color(.7f,.2f, .1f,1);
    public static final Color yellow = new Color(.7f,.6f, .1f,1);
	public static final Color transparent = new Color(0,0,0,0);
	private static Pixmap p;

    static{
//		Texture t = new Texture(Gdx.files.internal("palette.png"));
//		p = Draw.getPixmap(t);
//
//		light = palette(0,0);
//		sand = palette(1,0);
//		dark = palette(2,0);
//		fate_darkest = palette(3,0);
//		fate_light = palette(4,0);
//		fate_lightest = palette(5,0);
//		blue_light = palette(6,0);
//		blue_dark = palette(7,0);
//		green_light = palette(8,0);
//		green_dark = palette(9,0);
//		grey = palette(10,0);
//		brown_light = palette(11,0);
//		brown_dark = palette(12,0);
//		red = palette(13,0);
//		double_dark = palette(14,0);
//		sun = palette(15,0);
	}
	
	
	public static Color palette(int x, int y){
		return new Color(p.getPixel(x, y));
	}
	
	public static Color withAlpha(Color c, float alpha) {
		return new Color(c.r, c.g, c.b, alpha);
	}

	public static Color shiftedTowards(Color source, Color target, float amount) {
		if (amount > 1)
			amount = 1;
		if (amount < 0)
			amount = 0;
		float r = source.r + ((target.r - source.r) * amount);
		float g = source.g + (target.g - source.g) * amount;
		float b = source.b + (target.b - source.b) * amount;
		return new Color(r, g, b, 1);
	}

	public static Color multiply(Color source, Color target) {
		return new Color(source.r * target.r, source.g * target.g, source.b
				* target.b, 1);
	}

	private static Color make(int r, int g, int b) {
		return new Color((float) (r / 255f), (float) (g / 255f),
				(float) (b / 255f), 1);
	}

	public static Color monochrome(Color c) {
		float brightness = (c.r + c.g + c.b) / 3;
		return new Color(brightness, brightness, brightness, c.a);
	}

	public static boolean equals(Color a, Color b) {
		return a.a == b.a && a.r == b.r && a.g == b.g && a.b == b.b;
	}

	public static boolean wigglyEquals(Color a, Color aa) {
		float r = Math.abs(a.r - aa.r);
		float g = Math.abs(a.g - aa.g);
		float b = Math.abs(a.b - aa.b);
		float wiggle = .01f;
		return r < wiggle && g < wiggle && b < wiggle;
	}
	
	public static void setBatchColour(Batch batch, Color c, float a) {
		batch.setColor(c.r, c.g, c.b, a);
	}
	
	public static Vector3 v3(Color col){
		return new Vector3(col.r, col.g, col.b);
	}
}