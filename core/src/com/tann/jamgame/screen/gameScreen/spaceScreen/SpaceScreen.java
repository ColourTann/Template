package com.tann.jamgame.screen.gameScreen.spaceScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.Obstacle.Obstacle;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.PlayerShip;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Tanker;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Screen;
import com.tann.jamgame.util.Shape;


public class SpaceScreen extends Screen {

    Map map;
    PlayerShip playerShip;
    Tanker tanker;

    private static SpaceScreen self;
    public static SpaceScreen get(){
        if(self==null) self = new SpaceScreen();
        return self;
    }

    private SpaceScreen() {
        map = new Map();
        addActor(map);
        tanker = new Tanker();
        map.addActor(tanker);
        playerShip = new PlayerShip();
        map.addActor(playerShip);
    }

    Vector3 temp = new Vector3();

    private Array<Bullet> bullets = new Array<>();

    public void addBullet(Bullet b){
        bullets.add(b);
    }

    private void tickBullets(){
        for(int i=bullets.size-1;i>=0;i--){
            Bullet bill = bullets.get(i);
            bill.update();
            for(Obstacle o:map.obstacles){
                if(Shape.overlaps(bill.getShape(), o.getShape())){
                    bill.dead = true;
                    o.damage(bill.getDamage());
                    break;
                }
            }


            if(bill.dead){
                bullets.removeValue(bill, true);
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        temp.set(playerShip.getX(), playerShip.getY(),0);
//        temp.set(playerShip.getX()/2+tanker.getX()/2, playerShip.getY()/2+tanker.getY()/2, 0);
        Main.orthoCam.position.interpolate(temp, .05f, Interpolation.pow2Out);
        float targetZoom = playerShip.getSpeed()*.03f+1;
//        float xDiff = tanker.getX()-playerShip.getX();
//        float yDiff = tanker.getY()-playerShip.getY();
//        targetZoom = (float) Math.sqrt(xDiff*xDiff+yDiff*yDiff)/(Main.height*.8f);
//        targetZoom = Math.max(1, targetZoom);
        Main.orthoCam.zoom=Interpolation.linear.apply(Main.orthoCam.zoom, targetZoom, .08f);;
        Main.orthoCam.update();
        tickBullets();
        map.tick();
    }

    @Override
    public void preDraw(Batch batch) {
    }

    @Override
    public void postDraw(Batch batch) {
        for(Bullet bill:bullets) {
            bill.draw(batch);
        }
    }

    @Override
    public void preTick(float delta) {

    }

    @Override
    public void postTick(float delta) {

    }

    @Override
    public void keyPress(int keycode) {
        switch(keycode){
            case Input.Keys.NUM_1:
                Main.orthoCam.zoom-=.3f;
                Main.orthoCam.update();
                break;
            case Input.Keys.NUM_2:
                Main.orthoCam.zoom+=.3f;
                Main.orthoCam.update();
                break;
        }

    }
}
