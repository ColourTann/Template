package com.tann.jamgame.screen.spaceScreen.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.*;
import com.tann.jamgame.screen.spaceScreen.ship.player.Defender;
import com.tann.jamgame.screen.spaceScreen.ship.player.PlayerShip;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.player.Tanker;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.formation.BasicFormation;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.formation.Formation;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.ui.WeaponIcon;
import com.tann.jamgame.util.*;

public class Map extends Group{

    Texture bg;
    public Defender defender;
    public Tanker tanker;
    public Array<Ship> ships = new Array<>();
    public Array<Formation> formations = new Array<>();
    public DropZone dropZone;
    static final int texSize = 2048;
    public static final float HEIGHT = 4000;
    public static int level = 1; //todo not start level 1
    public Map() {
        setSize(13000,HEIGHT);
        Pixmap p = new Pixmap(texSize, texSize, Pixmap.Format.RGBA4444);
        for(int i=0;i<500;i++){
            p.setColor(Colours.light);
            p.fillRectangle((int)(Math.random()*texSize), (int)(Math.random()*texSize), 3, 3);
        }
        bg = new Texture(p);
        setTransform(false);
    }

    private static final JsonReader jr = new JsonReader();


    public void setup() {
        JsonValue levelContents = jr.parse(Gdx.files.internal("levels/"+level+".json"));
        setWidth(levelContents.getInt("width"));

        int numSpeeders = levelContents.get("speeders").asInt();
        int numHulks = levelContents.get("hulks").asInt();
        int numBombers = levelContents.get("bombers").asInt();
        int numCarriers = levelContents.get("carriers").asInt();

        for(Ship s:ships){
            s.destroy(true);
        }
        formations.clear();
        tanker = new Tanker();
        tanker.setPosition(700, getHeight()/2);
        addActor(tanker);
        ships.add(tanker);
        defender = new Defender();
        addActor(defender);
        defender.setPosition(700, getHeight()/2+70);
        ships.add(defender);
        control(defender);

        if(levelContents.has("words")){
            JsonValue words = levelContents.get("words");
            for(int i=0;i<words.size;i++){
                String s = words.get(i).asString();
                TextBox tb = new TextBox(s, Fonts.fontBig, 5000, Align.center);
                addActor(tb);
                tb.setPosition(tanker.getX()+1000*(i)-tb.getWidth()/2, tanker.getY()+500);
            }
        }


        for(int i=0;i<numSpeeders;i++){
            addShip(new Speeder());
        }
        for(int i=0;i<numHulks;i++){
            addShip(new Hulk());
        }
        for(int i=0; i < numBombers; i++) {
            addShip(new Bomber());
        }
        for(int i=0; i < numCarriers; i++) {
            addShip(new Carrier());
        }

        dropZone = new DropZone(getWidth()*.98f, getHeight()*.5f, 1400);

        int numFormations = 0;
        float xRand = 200;
        int offset = 0;
        for(int i=0;i<numFormations;i++){
            float x= getWidth()/(numFormations+1+offset)*(i+1+offset);
            x += Particle.rand(-xRand, xRand);
            Formation f = new BasicFormation(x);
            formations.add(f);
            for(Ship s:f.ships){
                ships.add(s);
                addActor(s);
            }
        }
    }

    public static void nextLevel(){
        level++;
    }

    public void addShip(Ship ship){
        ships.add(ship);
        float minX = 2600;
        ship.setPosition(
                (float)(Math.random()*(getWidth()-minX*2)*.95f)+minX,
                (float)(Math.random()* EnemyShip.AGGRO_RANGE * (Math.random()>.5?1:-1)+getHeight()/2)); // don't spawn so high up then won't aggro
        addActor(ship);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Main.logTime(null);
        batch.setColor(Colours.dark);
        float border = Main.width*2;
        Draw.fillRectangle(batch, getX()-border, getY()-border, getWidth()+border*2, getHeight()+border*2);
        batch.setColor(Colours.white);
        for(int x=0;x<getWidth();x+=texSize){
            for(int y=0;y<getHeight();y+=texSize){
                Draw.draw(batch, bg, x,y);
            }
        }
        Main.logTime("stars");
        super.draw(batch, parentAlpha);
        Main.logTime("other");
        for(Formation f:formations){
            f.draw(batch);
        }
        dropZone.draw(batch);
    }

    public void tick() {
        for(int i=ships.size-1;i>=0;i--){
            if(ships.get(i).dead){
                ships.removeValue(ships.get(i), true);
            }
        }
        if(dropZone.inside(tanker)){
            SpaceScreen.get().victory();
        }
        for(int i=formations.size-1;i>=0;i--){
            Formation f =formations.get(i);
            f.checkAggro(tanker);
            f.checkAggro(defender);
            if(f.dead){
                formations.removeValue(f,true);
            }
        }
    }

    private PlayerShip controlledShip;
    public void control(PlayerShip ship){
        if(controlledShip!=null){
            controlledShip.setControl(false);
            for(WeaponIcon w:controlledShip.getWeaponIcons()){
                w.remove();
            }
        }
        controlledShip=ship;
        controlledShip.setControl(true);
    }

    public void swapShips() {
        if(defender.isControlled()){
            control(tanker);
        }
        else{
            control(defender);
        }
    }

    public Ship getControlledShip(){
        return controlledShip;
    }


    public void flamingShip(Ship ship) {
        for(Ship s:ships){
            if(ship!=s && s.affectedBy(Bullet.BulletType.Friendly)){
                if(Shape.overlaps(s.getShape(), ship.getShape())){
                    s.damage(2);
                }
            }
        }
    }
}
