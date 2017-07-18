package com.tann.jamgame.screen.gameScreen.spaceScreen.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.Damageable;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.particle.EngineParticle;
import com.tann.jamgame.screen.gameScreen.spaceScreen.particle.ExplosionParticle;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.player.PlayerShip;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Weapon;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Particle;


public abstract class Ship extends Group implements Damageable{

    public float dx, dy;

    protected Array<Weapon> weapons = new Array<>();

    static final float DRAG = .985f;
    static final float THRUST_DRAG = .9f;
    static final float THRUST_FACTOR = .6f;
    protected final float accel;

    protected float thrustAmount;
    protected final float maxSpeed;
    protected final float turnSpeed;

    protected boolean makeParticle = false;

    protected static TextureRegion[] thruster;
    static{
        thruster = new TextureRegion[8];
        for(int i=0;i<8;i++){
            thruster[i]= Main.atlas.findRegion("ship/thruster/thruster"+i);
        }
    }

    public Ship(float accel, float maxSpeed, float turnSpeed) {
        this.accel = accel;
        this.maxSpeed =maxSpeed;
        this.turnSpeed=turnSpeed;
    }

    int hp = 1, maxHp=1;
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
        for(int i=0;i<2;i++) {
            ExplosionParticle ep = Pools.obtain(ExplosionParticle.class);
            ep.setup();
            ep.x = getX();
            ep.y = getY();
            SpaceScreen.get().addParticle(ep);
        }

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
        for(Weapon w:weapons){
            w.update();
        }
        internalAct(delta);
        thrustAmount *= THRUST_DRAG;

        if(makeParticle){
            float maxParticles =1;
            for(int i=0;i<maxParticles;i++) {
                EngineParticle ep = Pools.obtain(EngineParticle.class);
                ep.setup();
                ep.x = getButtX() + dx * 1.5f;
                ep.y = getButtY() + dy * 1.5f;
                float rand = 0.9f;
                float speedRotation = (float) Math.atan2(dy, dx);
                float angleDiff = Math.abs(Maths.angleDiff(getRotation(), speedRotation));
                float initialMult = 0+ angleDiff*3;

                float faceX = (float) Math.cos(getRotation());
                float faceY = (float) Math.sin(getRotation());
                ep.dx = -faceX * initialMult + Particle.rand(-rand, rand);
                ep.dy = -faceY * initialMult + Particle.rand(-rand, rand);
                ep.x -= dx*i/maxParticles;
                ep.y -= dy*i/maxParticles;
                ep.col = (this instanceof PlayerShip)? Colours.blue:Colours.red;
                SpaceScreen.get().addParticle(ep);
            }
        }
        makeParticle = false;

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
        thrustAmount += amount*THRUST_FACTOR;
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

    protected float flash;
    public void flash(){
        flash = 1;
    }


    public abstract Shape2D getShape();

    public abstract void drawMinimap(Batch batch, float x, float y);

    protected boolean control;
    public void setControl(boolean control) {
        this.control = control;
    }

    public boolean isControlled() {
        return control;
    }

    public float getBaseZoom() {
        return 1;
    }

    protected void setHp(int hp) {
        this.maxHp=hp;
        this.hp=hp;
    }

    protected void drawThrustAt(Batch batch, float x, float y, float intensity){
        TextureRegion thrust = thruster[(int)(Main.ticks*30)%thruster.length];
        float scaleX = intensity;
        float scaleY = 1+intensity*.4f;
        batch.setColor(Colours.white);
        Draw.drawRotatedScaled(batch, thrust,
                (float) (x-Math.cos(getRotation())*thrust.getRegionWidth()*scaleX + Math.sin(getRotation())*thrust.getRegionHeight()/2*scaleY),
                (float) (y-Math.sin(getRotation())*thrust.getRegionWidth()*scaleX - Math.cos(getRotation())*thrust.getRegionHeight()/2*scaleY),
                scaleX, scaleY, getRotation());
    }

    public float getHealthRatio() {
        return (float)hp/maxHp;
    }

    public void addWeapon (Weapon w){
        weapons.add(w);
        w.setShip(this);
        w.friend = this instanceof PlayerShip;
    }


    public static String keyFromWeapon(int index){
        switch (index){
            case 0: return "z";
            case 1: return "x";
            case 2: return "c";
        }
        return "q";
    }
}
