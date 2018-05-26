package com.tann.jamgame.screen.gameScreen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.map.entities.Drone;
import com.tann.jamgame.screen.gameScreen.map.entities.Hover;
import com.tann.jamgame.screen.gameScreen.map.entities.Player;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.ImageActor;
import com.tann.jamgame.util.Images;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Map extends Group {
    List<Orb> orbs = new ArrayList<>();
    public List<Drone> drones = new ArrayList<>();
    private static Map self;
    public static Map get(){
        if(self==null){
            self = new Map();
        }
        return self;
    }


    Player player;
    List<Path> pathList = new ArrayList<>();

    public static final int SIZE = 2700;

    private Map() {
        setSize(SIZE,SIZE);
        for(int i=0;i<1000;i++){
            ImageActor ia = new ImageActor(Images.box);
            addActor(ia);
            ia.setPosition((int)(Math.random()*getWidth()), (int) (Math.random()*getHeight()));
        }
        int dist = (int) (getWidth()/13);
        pathList.add(new Path(
                new Vector2(dist, getHeight()-dist),
                new Vector2(dist, dist),
                new Vector2(getWidth()-dist, dist)
        ));
        pathList.add(new Path(
                new Vector2(dist, getHeight()-dist),
                new Vector2(getWidth()-dist, dist)
        ));
        pathList.add(new Path(
                new Vector2(dist, getHeight()-dist),
                new Vector2(getWidth()-dist, getHeight()-dist),
                new Vector2(getWidth()-dist, dist)
        ));
        for(int i=0;i<3;i++){
            pathList.add(pathList.get(i).invert());
        }
        for(Path p:pathList){
            addActor(p);
        }

        Orb dark = new Orb(false);
        orbs.add(dark);
        dark.setPosition(getHeight()+dark.getHeight()/2, -dark.getWidth()/2);

        Orb light = new Orb(true);
        orbs.add(light);
        light.setPosition(0, getHeight());

        for(Orb o:orbs){
            addActor(o);
        }



        player = new Player();
        addActor(player);
        player.moveTo((int) (getWidth()/6), getHeight()-dist);
        Main.self.orthoCam.position.set(player.getX(), player.getY(), 0);
    }

    final float spawnRate = 10;
    float nextSpawn = 0;

    void spawn(){
        for(Path p:pathList) {
            for (int i = 0; i < 10; i++) {
                Hover h = new Hover(p);
                drones.add(h);
                addActor(h);
            }
        }
        Collections.shuffle(drones);
    }

    @Override
    public void act(float delta) {
        nextSpawn -= delta;
        if(nextSpawn<=0){
            nextSpawn = spawnRate;
            spawn();
        }
        int x = (int) Math.max(Math.min(player.getX(), getWidth()-Main.width/2), Main.width/2);
        int y = (int) Math.max(Math.min(player.getY(), getHeight()-Main.height/2), Main.height/2);
        float camX = Main.self.orthoCam.position.x, camY = Main.self.orthoCam.position.y;
        float factor = .2f;
        Main.self.orthoCam.position.set((int)(camX+(x-camX)*factor), (int)(camY+(y-camY)*factor), 0);
        Main.self.orthoCam.update();
        for(int i=drones.size()-1; i>=0; i--){
            Drone d = drones.get(i);
            if(d.dead){
                d.remove();
                drones.remove(d);
            }
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.teal);
        Draw.fillActor(batch, this);

        super.draw(batch, parentAlpha);

        int orbSize = (int) (getWidth()/2);
    }

    public Drone findTarget(Drone drone) {
        int range = drone.getRange();
        for(Drone d:drones){
            if(d.white != drone.white && !d.dead && d.getDistance(drone)<range ){
                return d;
            }
        }
        return null;
    }
}
