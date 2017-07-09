package com.tann.jamgame.screen.gameScreen.spaceScreen.Obstacle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.screen.gameScreen.spaceScreen.Damageable;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Obstacle extends Actor implements Damageable{
    int maxHP = 10;
    int hp = 10;
    boolean dead;
    public Obstacle(float x, float y) {
        setPosition(x,y);
        setSize(60, 60);
    }

    static Circle circle = new Circle();
    public Shape2D getShape(){
        circle.set(getX()+getWidth()/2, getY()+getHeight()/2, getWidth()/2);
        return circle;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.shiftedTowards(Colours.green, Colours.red, 1-((float)hp/maxHP)));
        Draw.fillEllipse(batch, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }

    @Override
    public void damage(int amount) {
        this.hp -= amount;
        if(hp<=0){
            remove();
            dead=true;
        }
    }
}
