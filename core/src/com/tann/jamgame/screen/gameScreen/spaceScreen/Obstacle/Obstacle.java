package com.tann.jamgame.screen.gameScreen.spaceScreen.obstacle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.Damageable;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Obstacle extends Actor implements Damageable{
    int maxHP = 10000;
    int hp = 10000;
    public boolean dead;

    static TextureRegion tr = Main.atlas.findRegion("spikeball");

    public Obstacle(float x, float y) {
        setPosition(x,y);
        setSize(120, 120);
    }

    static Circle circle = new Circle();
    public Shape2D getShape(){
        circle.set(getX()+getWidth()/2, getY()+getHeight()/2, getWidth()/2);
        return circle;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.white);
        Draw.drawSize(batch, tr, getX(), getY(), getWidth(), getHeight());
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

    @Override
    public boolean affectedBy(Bullet.BulletType type) {
        return true;
    }

    public void drawMinimap(Batch batch, float xScale, float yScale) {
        batch.setColor(Colours.red);
        float size = 5;
        Draw.fillEllipse(batch, getX()*xScale-size/2, getY()*yScale-size/2, size, size);
    }
}
