package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;

public class Animation {
    public static Animation pulseShot = new Animation(Main.atlas.findRegion("effects/pulse"), 5 ,1, 10);
    private float animation_speed;
    public final Array<TextureAtlas.AtlasRegion> frames;
    float baseWidth;
    float baseHeight;
    public Animation(String baseName, float fps) {
        frames = Main.atlas.findRegions(baseName);
        animation_speed = fps;
        baseWidth = frames.get(0).getRegionWidth();
        baseHeight = frames.get(0).getRegionHeight();
    }

    public Animation(TextureAtlas.AtlasRegion baseTexture, int across, int down, float fps) {
        frames = new Array<>();
        baseWidth = baseTexture.getRegionWidth()/across;
        baseHeight = baseTexture.getRegionHeight()/down;
        for(int x=0;x<across;x++){
            for(int y=0;y<down;y++){
                frames.add(new TextureAtlas.AtlasRegion(baseTexture.getTexture(),
                        (int)(baseTexture.getRegionX()+x*baseWidth), (int)(baseTexture.getRegionY()+y*baseHeight),
                        (int)baseWidth, (int)baseHeight));
            }
        }
        animation_speed = fps;
    }

    public void draw(Batch batch, float x, float y){
        draw(batch,x,y,1);

    }

    public void draw(Batch batch, float x, float y, float scaleXY){
        draw(batch,x,y,(int)((Main.ticks*animation_speed)%frames.size),baseWidth*scaleXY, baseHeight*scaleXY, 0);
    }

    public void draw(Batch batch, float x, float y, int frame, float width, float height, float rotation){
        Draw.drawCenteredRotatedScaled(batch, frames.get(frame), x, y, width/baseWidth, height/baseHeight, rotation);
    }

    public TextureRegion getFrame() {
        return frames.get((int)((Main.ticks*animation_speed)%frames.size));
    }
}
