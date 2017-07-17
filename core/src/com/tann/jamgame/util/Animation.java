package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;

public class Animation {
    private float animation_speed;
    public final Array<TextureAtlas.AtlasRegion> frames;
    float baseWidth;
    float baseHeight;
    public Animation(String baseName, float fps) {
        System.out.println(baseName);
        frames = Main.atlas.findRegions(baseName);
        System.out.println(frames);
        animation_speed = fps;
        baseWidth = frames.get(0).getRegionWidth();
        baseHeight = frames.get(0).getRegionHeight();
    }
    public void draw(Batch batch, float x, float y){
        draw(batch,x,y,(int)((Main.ticks*animation_speed)%frames.size),baseWidth, baseHeight, 0);
    }

    public void draw(Batch batch, float x, float y, int frame, float width, float height, float rotation){
        Draw.drawCenteredRotatedScaled(batch, frames.get(frame), x, y, width/baseWidth, height/baseHeight, rotation);
    }
}
