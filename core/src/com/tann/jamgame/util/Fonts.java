package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

public class Fonts {

    public static BitmapFont fontSmall;
	public static BitmapFont font;
	public static BitmapFont fontBig;
	
	public static void setup(){
	    fontSmall = new BitmapFont();
	    font = new BitmapFont();
	    fontBig = new BitmapFont();
	}

	public static void draw(Batch batch, String string, BitmapFont font, Color col, float x, float y, float width, float height){
	    draw(batch,string, font,col,x,y,width,height, Align.center);
    }

    public static void draw(Batch batch, String string, BitmapFont font, Color col, float x, float y, float width, float height, int align){
        font.setColor(col);
        font.draw(batch, string, x, y+height/2+font.getCapHeight()/2, width, align, true);
    }

}
