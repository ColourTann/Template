package com.tann.jamgame.screen.spaceScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.screen.spaceScreen.map.Map;
import com.tann.jamgame.screen.spaceScreen.ship.player.PlayerShip;
import com.tann.jamgame.screen.spaceScreen.shipUpgrade.ShipUpgradeGroup;
import com.tann.jamgame.screen.spaceScreen.ui.MiniMap;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.ui.TankerHealth;
import com.tann.jamgame.screen.spaceScreen.ui.WeaponIcon;
import com.tann.jamgame.util.Layoo;
import com.tann.jamgame.util.Screen;
import com.tann.jamgame.util.Shape;


public class SpaceScreen extends Screen {

    public Map map;
    public MiniMap miniMap;
    private static SpaceScreen self;
    public static SpaceScreen get(){
        if(self==null){
            self = new SpaceScreen();
            self.init();
        }
        return self;
    }

    Stage spaceStage;
    OrthographicCamera spaceCam;
    Batch spaceBatch;

    private SpaceScreen() {
    }

    private void init(){
        spaceStage = new Stage();
        spaceCam = (OrthographicCamera) spaceStage.getCamera();
        spaceBatch = (SpriteBatch) spaceStage.getBatch();
        map = new Map();
        spaceStage.addActor(map);
        specialStage = spaceStage;
        miniMap = new MiniMap(map);
        miniMap.setPosition(4,4);
        addActor(miniMap);
        TankerHealth th = new TankerHealth(map.tanker);
        addActor(th);
        spaceCam.position.set(map.tanker.getX(), map.tanker.getY(), 0);
        ShipUpgradeGroup sug = new ShipUpgradeGroup();
        addActor(sug);
        sug.setPosition(getWidth()/2, getHeight()/2, Align.center);
    }

    Vector3 temp = new Vector3();

    public Array<Bullet> bullets = new Array<>();

    public void addBullet(Bullet b){
        bullets.add(b);
    }

    private void tickBullets(){
        for(int i=bullets.size-1;i>=0;i--){
            Bullet bill = bullets.get(i);
            boolean done = false;
            while(!done) {
                done = bill.update();
                for (Ship s : map.ships) {
                    if (!s.affectedBy(bill.type) || s.dead) continue;
                    if (Shape.overlaps(bill.getShape(), s.getShape())) {
                        bill.impact();
                        s.damage(bill.getDamage());
                        break;
                    }
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
        spaceStage.act(delta);
        Ship ship = map.getControlledShip();
        temp.set(ship.getX(), ship.getY(),0);
        spaceCam.position.interpolate(temp, .05f, Interpolation.pow2Out);

        float baseZoom = ship.getBaseZoom();

        float targetZoom = map.fighter.getSpeed()*.05f+baseZoom;
        spaceCam.zoom=Interpolation.linear.apply(spaceCam.zoom, targetZoom, .08f);;
        spaceCam.update();
        tickBullets();
        map.tick();
    }

    @Override
    public void preDraw(Batch batch) {
    }

    @Override
    public void postDraw(Batch batch) {

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
            case Input.Keys.TAB:
                map.swapShips();
                break;
        }
        if(map.fighter!=null){
            map.fighter.keyPress(keycode);
        }

    }

    public void addWeaponIcons(PlayerShip ship) {
        Layoo l = new Layoo(this);
        for(WeaponIcon w:ship.getWeaponIcons()){
            l.actor(w);
            l.gap(500);
            l.row(1);
        }
        l.row(10);
        l.layoo();
    }
}
