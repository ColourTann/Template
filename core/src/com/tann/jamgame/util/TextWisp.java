package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class TextWisp extends Group{
	public String text;
	Color c = Colours.light;
	static final float defaultDuration = .6f;
	float initialDuration;
	float duration;
	public TextWisp(String text) {
		this(text, defaultDuration);
		setTouchable(Touchable.disabled);
	}

	public TextWisp(String text, float initialDuration) {
		this.text=text;
		this.initialDuration = initialDuration;
		this.duration = initialDuration;
		setSize(TannFont.font.getWidth(text), TannFont.font.getHeight());
	}

	public void setColor(Color c){
		this.c=c;
	}
	
	static float speed=0;
	@Override
	public void act(float delta) {
		setY(getY()+delta*speed);
		duration-=delta;
		if(duration<=0){
			getParent().removeActor(this);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
	    float alp = duration/initialDuration;
	    alp = (float) (1- Math.pow(1-alp, 4));
		batch.setColor(Colours.withAlpha(Colours.light, alp));
		TannFont.font.drawString(batch, text, (int)getX(), (int) getY());
	}
	
}
