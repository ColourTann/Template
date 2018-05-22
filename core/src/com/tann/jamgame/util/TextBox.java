package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class TextBox extends Group{
	

	
	String text;
	TannFont font = TannFont.font;
	Color textCol = Colours.light;
	boolean wiggle, sin;
	public TextBox(String text){
		this(text, false, false);
	}

	public TextBox(String text, boolean wiggle, boolean sin){
		this.text=text;
		this.wiggle = wiggle;
		this.sin = sin;
		setup(text);
	}

	public void setup(String text){
	    this.text=text;
        setSize(font.getWidth(text), font.getHeight());
    }

	public void setTextColour(Color col){
        this.textCol = col;
    }

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(textCol);
		font.drawString(batch, text, (int)(getX()), (int) getY(), false, wiggle, sin);
		super.draw(batch, parentAlpha);
	}
	
}
