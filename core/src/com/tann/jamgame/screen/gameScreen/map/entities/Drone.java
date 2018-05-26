package com.tann.jamgame.screen.gameScreen.map.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.screen.gameScreen.map.Map;
import com.tann.jamgame.screen.gameScreen.map.Path;
import com.tann.jamgame.screen.gameScreen.map.entities.weapons.Weapon;
import com.tann.jamgame.util.Tann;

public abstract class Drone extends Actor{
    int hp = 10;
    Path path;
    Vector2 nextPoint;
    int pathIndex = 0;
    public boolean white;
    Drone target;
    public boolean dead;
    Weapon weapon;

    public Drone(Path path) {
        this.path = path;
        this.white = path.white;
        moveTowards(1);

        posX = path.points[0].x;
        posY = path.points[0].y;
        Vector2 dist = path.points[1].cpy().sub(path.points[0]).nor().scl(500);
        posX += dist.x; posY += dist.y;
        int wiggle = 100;
        posX += Tann.randomBetween(-wiggle, wiggle);
        posY += Tann.randomBetween(-wiggle, wiggle);
    }

    void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    float posX, posY;
    float speed = (float) (120 + Math.random()*30);

    private void arrive() {
        pathIndex ++;
        if(path.points.length>pathIndex+1){
            moveTowards(pathIndex+1);
        }
    }

    private void moveTowards(int i) {
        int wiggle = 100;
        nextPoint = path.points[i].cpy().add((float)(Math.random()-.5)*wiggle, (float)(Math.random()-.5)*wiggle);
    }

    public void damage(int amount){
        hp -= amount;
        if(hp <= 0){
            die();
            dead = true;
        }
    }

    public abstract void  die();


    @Override
    public void act(float delta) {
        tick(delta);
        weapon.tick(delta);
        target = Map.get().findTarget(this);
        if(target != null){
                attack(target);
        }
        else{
            move(delta);
        }
        super.act(delta);
    }

    protected abstract void tick(float delta);


    public abstract int getRange();

    private void move(float delta){
        if(Tann.dist(posX, posY, nextPoint.x, nextPoint.y)<3){
            arrive();
        }
        Vector2 targetDiff = nextPoint.cpy().sub(getX(), getY()).nor();
        posX += targetDiff.x*speed*delta;
        posY += targetDiff.y*speed*delta;
        setPosition((int)posX, (int)posY);
    }


    public void attack(Drone target){
        weapon.attemptAttack(target);
    }

    public float getDistance(Drone drone) {
        return Tann.dist(posX, posY, drone.posX, drone.posY);
    }

    public Vector2 getBulletOffset(){
        return Vector2.Zero;
    };
}
