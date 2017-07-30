package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.jamgame.Main;

public class ImageActor extends Group{
	
	public TextureRegion tr;
    public ImageActor(TextureRegion tr, float width, float height) {
        this.tr=tr;
        setSize(width, height);
    }

    public ImageActor(TextureRegion tr) {
        this(tr, tr.getRegionWidth(), tr.getRegionHeight());
    }

    int borderSize;
    Color borderCol;//lie dog hahah
    public void setBorder(int size, Color col){
        this.borderCol=col;
        this.borderSize=size;
    }

    static final int extra_gap = 5;
    static final int extra_horizonal_gap = 50;

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if(this.borderCol!=null){
		    batch.setColor(borderCol);
		    Draw.drawRectangle(batch, getX()-borderSize-extra_horizonal_gap, getY()-borderSize-extra_gap, getWidth()+(extra_gap+borderSize+extra_horizonal_gap)*2, getHeight()+(extra_gap+borderSize)*2, borderSize);
        }

        batch.setColor(getColor());
        if(flash){
            float freq = 3.5f;
            batch.setColor(Colours.withAlpha(getColor(), (float)(Math.sin(Main.ticks*freq)+1)/2));
        }
        Draw.drawSize(batch, tr, getX(), getY(), getWidth(), getHeight());
	}

    boolean flash;
    public void flash(boolean flash) {
        this.flash=flash;
    }

}
