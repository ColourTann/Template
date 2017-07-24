package com.tann.jamgame.screen.spaceScreen.map;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.Hulk;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.Rammer;
import com.tann.jamgame.screen.spaceScreen.ship.player.Defender;
import com.tann.jamgame.screen.spaceScreen.ship.player.PlayerShip;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.player.Tanker;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.Speeder;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.formation.BasicFormation;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.formation.Formation;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.*;
import com.tann.jamgame.screen.spaceScreen.ui.WeaponIcon;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Particle;

public class Map extends Group{

    Texture bg;
    public Defender defender;
    public Tanker tanker;
    public Array<Ship> ships = new Array<>();
    public Array<Formation> formations = new Array<>();
    public DropZone dropZone;
    static final int texSize = 2048;
    public static final float HEIGHT = 5000;
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

    public void setup() {
        for(Ship s:ships){
            s.destroy();
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

        for(int i=0;i<15;i++){
            addShip(new Speeder());
        }
        for(int i=0;i<3;i++){
            addShip(new Hulk());
        }
        for(int i=0;i<10;i++){
            addShip(new Rammer());
        }
        for(int i=0; i < 10; i++) {
            addShip(new Bomber());
        }

        dropZone = new DropZone(getWidth()*.98f, getHeight()*.5f, 1400);

        int numFormations = 1;
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

    private void addShip(Ship ship){
        ships.add(ship);
        ship.setPosition((float)(Math.random()*getWidth()), (float)(Math.random()*getHeight()));
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


}
