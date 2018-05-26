package com.tann.jamgame.screen.gameScreen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.map.entities.Drone;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Minimap extends Actor {
    Map map;
    float scale;
    public static final TextureRegion minimap = Main.atlas.findRegion("minimap");
    public Minimap(Map map) {
        this.map = map;
        int size = 100;
        setSize(size, size);
        scale = size/map.getWidth();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.white);
        batch.draw(minimap, getX(), getY());
        for(Drone d:map.drones){
            batch.setColor(Colours.withAlpha(d.white?Colours.grey[4]:Colours.grey[0], .35f));
            Draw.fillEllipse(batch, getX()+d.getX()*scale, getY()+d.getY()*scale, 5, 5);
        }
        batch.setColor(Colours.blue);
        Draw.fillEllipse(batch, getX()+Map.get().player.getX()*scale, getY()+Map.get().player.getY()*scale, 5, 5);
        super.draw(batch, parentAlpha);
    }
}
