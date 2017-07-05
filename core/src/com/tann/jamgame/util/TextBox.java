package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;

public class TextBox extends Group{
	
	public static HashMap<BitmapFont, Float> fontHeights;
	static{
		fontHeights = new HashMap<>();
		for(BitmapFont font:new BitmapFont[]{Fonts.font, Fonts.fontBig, Fonts.fontSmall}){
			TextBox tb = new TextBox("hi", font, 500, Align.center);
			fontHeights.put(font, tb.getHeight());
		}
	}
	
	
	String text;
	GlyphLayout layout = new GlyphLayout();
	BitmapFont font;
	int align;
	Color textCol = Colours.black;
	Color bgCol = Colours.transparent;
	float maxWidth;
	public TextBox(String text, BitmapFont font, float maxWidth, int align){
		if(maxWidth==-1) maxWidth = 99999;
		this.maxWidth=maxWidth;
		this.align=align;
		this.text=text;
		this.font=font;
		setup(text);
	}

	public void setup(String text){
	    this.text=text;
        layout.setText(font, text, Colours.black, maxWidth, align, true);
        setSize(Math.min(maxWidth, layout.width), layout.height);
    }
	
	public void setBackgroundColour(Color col){
		this.bgCol=col;
	}
	
	public void setTextColour(Color col){
		this.textCol = col;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
//		batch.setColor(1,0,1,.5f);
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(bgCol);
		Draw.fillRectangle(batch, getX()-50, getY()-50, getWidth()+100, getHeight()+100);
		font.setColor(textCol);
		font.draw(batch, text, getX(), getY()+getHeight(), layout.width,  align, true);
		super.draw(batch, parentAlpha);
	}
	
}
