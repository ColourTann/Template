package com.tann.jamgame.screen.gameScreen.spaceScreen.map;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.Obstacle.Obstacle;
import com.tann.jamgame.screen.gameScreen.spaceScreen.Star;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.PlayerShip;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Tanker;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.EnemyFighter;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.EnemyShip;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.formation.BasicFormation;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.formation.Formation;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Particle;

public class Map extends Group{

    public Array<Obstacle> obstacles = new Array<>();
    Texture bg;
    public PlayerShip playerShip;
    public Tanker tanker;
    public Array<Ship> ships = new Array<>();
    public Array<Formation> formations = new Array<>();
    public DropZone dropZone;
    static final int texSize = 2048;
    public Map() {
        setSize(25000,15000);
        Pixmap p = new Pixmap(texSize, texSize, Pixmap.Format.RGBA4444);
        for(int i=0;i<500;i++){
            p.setColor(Colours.light);
            p.fillRectangle((int)(Math.random()*texSize), (int)(Math.random()*texSize), 3, 3);
        }
        bg = new Texture(p);
        for(int i=0;i<20;i++){
            Obstacle o = new Obstacle(Particle.rand(0, getWidth()), Particle.rand(0, getHeight()));
            obstacles.add(o);
            addActor(o);
        }

        tanker = new Tanker();
        tanker.setPosition(200, getHeight()/2);
        addActor(tanker);
        ships.add(tanker);
        playerShip = new PlayerShip();
        addActor(playerShip);
        playerShip.setPosition(200, getHeight()/2);
        ships.add(playerShip);
        control(playerShip);

        for(int i=0;i<50;i++){
            EnemyShip e = new EnemyFighter();
            ships.add(e);
            e.setPosition((float)(Math.random()*getWidth()), (float)(Math.random()*getHeight()));
            addActor(e);
        }
        setTransform(false);
        dropZone = new DropZone(getWidth()*.98f, getHeight()*.5f, 1400);

        for(int i=0;i<8;i++){
            Formation f = new BasicFormation(Particle.rand(0,getWidth()), Particle.rand(0,getHeight()));
            formations.add(f);
            for(Ship s:f.ships){
                ships.add(s);
                addActor(s);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Main.logTime(null);
        batch.setColor(Colours.dark);
        Draw.fillActor(batch,this);
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
        for(int i=obstacles.size-1;i>=0;i--){
            if(obstacles.get(i).dead){
                obstacles.removeValue(obstacles.get(i), true);
            }
        }
        for(int i=ships.size-1;i>=0;i--){
            if(ships.get(i).dead){
                ships.removeValue(ships.get(i), true);
            }
        }
        if(dropZone.inside(tanker)){
            System.out.println("you win!");
        }
        for(int i=formations.size-1;i>=0;i--){
            Formation f =formations.get(i);
            f.checkAggro(tanker);
            if(f.dead){
                formations.removeValue(f,true);
            }
        }
    }

    private Ship controlledShip;
    public void control(Ship ship){
        if(controlledShip!=null){
            controlledShip.setControl(false);
        }
        controlledShip=ship;
        controlledShip.setControl(true);
    }

    public void swapShips() {
        if(playerShip.isControlled()){
            control(tanker);
        }
        else{
            control(playerShip);
        }
    }

    public Ship getControlledShip(){
        return controlledShip;
    }
}
