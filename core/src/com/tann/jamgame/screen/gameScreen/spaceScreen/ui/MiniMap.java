package com.tann.jamgame.screen.gameScreen.spaceScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.jamgame.screen.gameScreen.spaceScreen.map.Map;
import com.tann.jamgame.screen.gameScreen.spaceScreen.obstacle.Obstacle;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.formation.Formation;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class MiniMap extends Group {

    Map map;
    public MiniMap(Map map) {
        this.map = map;
        float widthScale = map.getWidth()/map.getHeight();
        setSize(180*widthScale, 180);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float alpha = .5f;
        batch.setColor(Colours.blue);
        int gap = 4;
        Draw.drawRectangle(batch, getX()-gap, getY()-gap, getWidth()+gap*2, getHeight()+gap*2, gap);
        batch.setColor(Colours.withAlpha(Colours.dark, alpha));
        Draw.fillActor(batch,this);
        float xScale = getWidth()/map.getWidth();
        float yScale = getHeight()/map.getHeight();
        batch.setColor(1,1,1,alpha);
        for(Ship s:map.ships){
            s.drawMinimap(batch, s.getX()*xScale, s.getY()*yScale);
        }
        for(Formation f:map.formations){
            f.drawMinimap(batch, xScale, yScale);
        }
        for(Obstacle o:map.obstacles){
            o.drawMinimap(batch, xScale, yScale);
        }
        map.dropZone.drawMinimap(batch, xScale, yScale);
        super.draw(batch, parentAlpha);
    }
}
