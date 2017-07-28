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
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.ui.ShipHealth;
import com.tann.jamgame.screen.spaceScreen.ui.WeaponIcon;
import com.tann.jamgame.util.*;

import javax.xml.soap.Text;


public class SpaceScreen extends Screen {

    public Map map;
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
        reset();
    }

    public ShipUpgradeGroup sug;
    TextButton confirm;

    private void showShipUpgrade(){
        Layoo l = new Layoo(this);
        if(sug == null){
            sug = new ShipUpgradeGroup();
        }
        l.row(3);
        l.actor(sug);
        l.row(1);
        confirm = new TextButton(200, 50, "Confirm");
        l.actor(confirm);
        l.row(3);
        l.layoo();
        confirm.setRunnable(()->
        {
            if(sug.isValid()) {
                closeShipUpgrade();
                startLevel();
            }
        }
        );
    }

    private void closeShipUpgrade() {
        sug.remove();
        confirm.remove();
    }

    public void refreshWeaponIcons(){
        SpaceScreen.get().addWeaponIcons(map.defender);
    }

    private void startLevel() {
        paused=false;
        refreshWeaponIcons();
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
                        bill.impact(s.damage(bill.getDamage()));
                        break;
                    }
                }
            }
            if(bill.dead){
                bullets.removeValue(bill, true);
            }
        }
    }

    boolean paused=true;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(paused) return;
        spaceStage.act(delta);
        Ship ship = map.getControlledShip();
        temp.set(ship.getX(), ship.getY(),0);
        spaceCam.position.interpolate(temp, .05f, Interpolation.pow2Out);

        float baseZoom = ship.getBaseZoom();

        float targetZoom = map.defender.getSpeed()*.05f+baseZoom;
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
            case Input.Keys.SPACE:
                if(finished) {
                    reset();
                }
                break;
        }
        if(map.defender !=null){
            map.defender.keyPress(keycode);
        }

    }

    Array<WeaponIcon> currentIcons = new Array<>();
    public void addWeaponIcons(PlayerShip ship) {
        for(WeaponIcon w:currentIcons){
            w.remove();
        }
        currentIcons.clear();
        Layoo l = new Layoo(this);
        for(WeaponIcon w:ship.getWeaponIcons()){
            l.actor(w);
            currentIcons.add(w);
            l.gap(1);
            l.row(1);
        }
        l.row(10);
        l.layoo();
    }

    boolean finished;
    boolean victory;

    public void defeat(){
        finished=true;
        paused=true;
        showDefeatDialog();
    }

    private static final int dialogWidth = 400;
    TextBox endBox;

    private void showDefeatDialog() {
        endBox = new TextBox("Mission failed\nPress space to restart", Fonts.font, dialogWidth, Align.center);
        addActor(endBox);
        endBox.setBackgroundColour(Colours.grey);
        endBox.setPosition(getWidth()/2, getHeight()/2, Align.center);
    }

    private void showVictoryDialog() {
        System.out.println("showing victory");
        endBox = new TextBox("Mission success\nPress space to continue", Fonts.font, dialogWidth, Align.center);
        addActor(endBox);
        endBox.setBackgroundColour(Colours.grey);
        endBox.setPosition(getWidth()/2, getHeight()/2, Align.center);
    }



    public void victory() {
        finished=true;
        paused=true;
        victory=true;
        Map.nextLevel();
        sug.unlockNext();
        showVictoryDialog();
    }

    ShipHealth tankerHealth;
    ShipHealth playerHealth;
    private void reset() {
        finished=false;
        paused=true;
        victory=false;
        if(endBox!=null){
            endBox.remove();
        }
        map.setup();
        setupHealth();
        spaceCam.position.set(map.tanker.getX(), map.tanker.getY(), 0);
        showShipUpgrade();
    }

    private void setupHealth(){
        if(tankerHealth !=null) tankerHealth.remove();
        tankerHealth = new ShipHealth(map.tanker, "tanker", 500);

        if(playerHealth !=null) playerHealth.remove();
        playerHealth = new ShipHealth(map.defender, "player", 200);

        Layoo l = new Layoo(this);
        l.row(1);
        l.add(1,playerHealth,1,tankerHealth,1);
        l.row(.012f);
        l.layoo();
    }

}
