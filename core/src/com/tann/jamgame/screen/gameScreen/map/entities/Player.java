package com.tann.jamgame.screen.gameScreen.map.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Player extends Actor {
    public Player() {
    }

    float dx, dy;
    float posX, posY;

    @Override
    public void act(float delta) {

        boolean left = Gdx.input.isKeyPressed(Input.Keys.A)||Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D)||Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.W)||Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.S)||Gdx.input.isKeyPressed(Input.Keys.DOWN);

        int keyX = (right?1:0) + (left?-1:0);
        int keyY = (up?1:0) + (down?-1:0);

        dx += keyX;
        dy += keyY;

        posX += dx;
        posY += dy;

        setX((int)posX);
        setY((int)posY);

        dx *= .9f;
        dy *= .9f;

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.blue);
        Draw.fillEllipse(batch, getX(), getY(), 20, 20);
        super.draw(batch, parentAlpha);
    }

    public void moveTo(float x, float y) {
        posX = x; posY = y;
        setX((int)posX);
        setY((int)posY);
    }
}
