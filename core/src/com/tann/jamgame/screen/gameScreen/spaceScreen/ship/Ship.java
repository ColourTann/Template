package com.tann.jamgame.screen.gameScreen.spaceScreen.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.jamgame.screen.gameScreen.spaceScreen.Damageable;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Weapon;


public abstract class Ship extends Group implements Damageable{

    public float dx, dy;
    protected Weapon weapon1;
    protected Weapon weapon2;

    static final float DRAG = .985f;
    protected final float accel;

    protected final float maxSpeed;

    public Ship(float accel, float maxSpeed) {
        this.accel = accel;
        this.maxSpeed =maxSpeed;
    }

    int hp = 8, maxHp=8;
    public void damage(int amount){
        flash();
        hp -= amount;
        if(hp<=0){
            destroy();
        }
    }

    public boolean dead;
    public void destroy(){
        dead=true;
        remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        flash-=.07f;
        flash = Math.max(0, flash);
        dx *= DRAG;
        dy *= DRAG;
        if(getSpeed()>maxSpeed){
            dx *= maxSpeed/getSpeed();
            dy *= maxSpeed/getSpeed();
        }
        setPosition(getX()+dx,getY()+dy);
        float x = getX();
        float y = getY();
        x = Math.max(0,Math.min(SpaceScreen.get().map.getWidth(), x));
        y = Math.max(0,Math.min(SpaceScreen.get().map.getHeight(), y));
        setPosition(x,y);
        if(weapon1!=null)weapon1.update();
        if(weapon2!=null)weapon2.update();
        internalAct(delta);
    }

    protected abstract void internalAct(float delta);

    public float getNoseX(){
        return getX() + (float)Math.cos(getRotation())*getWidth()/2;
    }

    public float getNoseY(){
        return getY() + (float)Math.sin(getRotation())*getWidth()/2;
    }

    public float getButtX(){
        return getX() - (float)Math.cos(getRotation())*getWidth()/2;
    }

    public float getButtY(){
        return getY() - (float)Math.sin(getRotation())*getWidth()/2;
    }

    public float getSpeed() {
        return (float)Math.sqrt(dx*dx+dy*dy);
    }

    Polygon p = new Polygon();
    float[] points = new float[]{0,0,0,0,0,0,0,0};

    protected void accelerate(float amount){
        float dxChange = (float)Math.cos(getRotation());
        float dyChange = (float)Math.sin(getRotation());
        dx += amount * dxChange;
        dy += amount * dyChange;
    }

    protected Shape2D getDefaultShape(){
        for(int i=0;i<points.length;i++){
            points[i]=(i%2==0)?-getWidth()/2:-getHeight()/2;
        }
        points[2]+= getWidth();
        points[4]+= getWidth();
        points[5]+=getHeight();
        points[7]+= getHeight();
        p.setVertices(points);
        p.setRotation((float) (getRotation()*360/(Math.PI*2)));
        p.setPosition(getX(), getY());
        return p;
    }

    float flash;
    public void flash(){
        flash = 1;
    }


    public abstract Shape2D getShape();

    public abstract void drawMinimap(Batch batch, float x, float y);

    boolean control;
    public void setControl(boolean control) {
        this.control = control;
    }

    public boolean isControlled() {
        return control;
    }

    public float getBaseZoom() {
        return 1;
    }

    void setHp(int hp) {
        this.maxHp=hp;
        this.hp=hp;
    }

    public float getHealthRatio() {
        return (float)hp/maxHp;
    }
}
