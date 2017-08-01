package com.tann.jamgame.screen.spaceScreen.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.IncomingMissionScreen;
import com.tann.jamgame.screen.MissionInstructionScreen;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.*;
import com.tann.jamgame.screen.spaceScreen.ship.player.Defender;
import com.tann.jamgame.screen.spaceScreen.ship.player.PlayerShip;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.player.Tanker;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.formation.Formation;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.ui.WeaponIcon;
import com.tann.jamgame.util.*;

public class Map extends Group{

    private static final float PLANET_SIZE = 500;
    Texture nebula;
    Texture stars;
    public Defender defender;
    public Tanker tanker;
    public Array<Ship> ships = new Array<>();
    public Array<Formation> formations = new Array<>();
    static final int texSize = 700;
    public static final float HEIGHT = 4000;
    public static int level = 0;
    public Map() {
        setSize(13000,HEIGHT);
        Pixmap p = new Pixmap(texSize, texSize, Pixmap.Format.RGBA8888);

        float amp = 1;
        float freq=.0045f;
        int octaves = 5;
        for(int x=0;x<texSize;x++){
            for(int y=0;y<texSize;y++){
                float xSin = (float) Math.sin(((float)x/texSize)*Math.PI*2);
                float xCos = (float) Math.cos(((float)x/texSize)*Math.PI*2);
                float ySin = (float) Math.sin(((float)y/texSize)*Math.PI*2);
                float yCos = (float) Math.cos(((float)y/texSize)*Math.PI*2);
                float total =0;
                for(int i=0;i<octaves;i++){
                    float freqFactor = (float) Math.pow(2, i) * freq;
                    total += (float) (Noise.noise(xSin*freqFactor*texSize/2,ySin*freqFactor*texSize/2,xCos*freqFactor*texSize/2,yCos*freqFactor*texSize/2)) * Math.pow(.5f, i);
                }
                float n = Math.max(-1,Math.min(1,total));
//                float n = total;
                n+=1;
                n/=2;

                float mult = .4f;


                float r = (float) Math.pow(n,9)*mult;
                float g = (float) Math.pow(n,5)*mult;
                float b = (float) Math.pow(n,3)*mult;

                r =  Math.max(0,Math.min(1,r));
                g =  Math.max(0,Math.min(1,g));
                b =  Math.max(0,Math.min(1,b));

                p.setColor(r,g,b,1);
                p.drawPixel(x,y);

            }
        }
        nebula = new Texture(p);

        p = new Pixmap(texSize, texSize, Pixmap.Format.RGBA8888);
        p.setColor(new Color(1,1,1,.5f));
        for(int i=0;i<500;i++){
            int size = (int)(Math.random()*4);
//            p.fillCircle((int)(Math.random()*texSize), (int)(Math.random()*texSize),  size);
            p.fillRectangle((int)(Math.random()*texSize), (int)(Math.random()*texSize), size, size);
        }
        stars = new Texture(p);

        setTransform(false);
    }

    private static final JsonReader jr = new JsonReader();

    Array<TextBox> boxes = new Array<>();
    ImageActor planet;

    public String spiel;
    public TextureRegion alcoholTexture;
    public TextureRegion planetTexture;

    static final float start = 700;
    static final float end = 700;

    public void setup() {
        for (TextBox tb : boxes) {
            tb.remove();
        }
        boxes.clear();

        JsonValue levelContents = jr.parse(Gdx.files.internal("levels/" + level + ".json"));
        setWidth(levelContents.getInt("width"));

        spiel = levelContents.getString("spiel");
        String alcoholName = levelContents.getString("drink");
        String planetName = levelContents.getString("planet");

        int numSpeeders = levelContents.get("speeders").asInt();
        int numHulks = levelContents.get("hulks").asInt();
        int numBombers = levelContents.get("bombers").asInt();
        int numCarriers = levelContents.get("carriers").asInt();


        if (planet != null) {
            planet.remove();
        }

        planetTexture = Main.atlas.findRegion("planet/" + planetName);
        alcoholTexture = Main.atlas.findRegion("drink/" + alcoholName);
        planet = new ImageActor(planetTexture);
        planet.setSize(PLANET_SIZE, PLANET_SIZE);
        planet.setPosition(getWidth() - planet.getWidth() / 2 - end, getHeight() / 2 - planet.getHeight() / 2);
        addActor(planet);

        for (Ship s : ships) {
            s.destroy(true);
        }
        formations.clear();
        tanker = new Tanker();
        tanker.setPosition(start, getHeight() / 2);
        addActor(tanker);
        ships.add(tanker);
        defender = new Defender();
        addActor(defender);
        defender.setPosition(start, getHeight() / 2 + 70);
        ships.add(defender);
        control(defender);

        if (levelContents.has("words")) {
            JsonValue words = levelContents.get("words");
            for (int i = 0; i < words.size; i++) {
                String s = words.get(i).asString();
                TextBox tb = new TextBox(s, Fonts.fontBig, 5000, Align.center);
                addActor(tb);
                boxes.add(tb);
                tb.setPosition(tanker.getX() + 1000 * (i) - tb.getWidth() / 2, tanker.getY() + 500);
            }
        }

        for (int i = 0; i < numSpeeders; i++) {
            addShip(new Speeder());
        }
        for (int i = 0; i < numHulks; i++) {
            addShip(new Hulk());
        }
        for (int i = 0; i < numBombers; i++) {
            addShip(new Bomber());
        }
        for (int i = 0; i < numCarriers; i++) {
            addShip(new Carrier());
        }


        if (levelContents.has("zones")) {
            JsonValue zones = levelContents.get("zones");
            for (int i = 0; i < zones.size; i++) {
                JsonValue zone = zones.get(i);
                String message = zone.getString("message");
                float start = zone.getFloat("start");
                float middle = getWidth() * start;
                int zoneSpeeders = zone.get("speeders").asInt();
                int zoneHulks = zone.get("hulks").asInt();
                int zoneBombers = zone.get("bombers").asInt();
                int zoneCarriers = zone.get("carriers").asInt();
                Formation f = new Formation(message, middle, zoneSpeeders, zoneHulks, zoneBombers, zoneCarriers);
                formations.add(f);
            }
        }
    }

    public static void nextLevel(){
        level++;
    }

    public void addShip(Ship ship){
        ships.add(ship);
        float minX = 2300;
        ship.setPosition(
                (float)(Math.random()*(getWidth()-minX*1.2f))+minX,
                (float)(Math.random()* EnemyShip.AGGRO_RANGE*.9f * (Math.random()>.5?1:-1)+getHeight()/2)); // don't spawn so high up then won't aggro
        addActor(ship);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Main.logTime(null);
        batch.setColor(Colours.dark);
        float border = Main.width*2;
        Draw.fillRectangle(batch, getX()-border, getY()-border, getWidth()+border*2, getHeight()+border*2);
        batch.setColor(Colours.white);
        float scale = 5.5f;
        for(int x=-1000;x<getWidth()+1000;x+=texSize*scale){
            for(int y=-1000;y<getHeight()+1000;y+=texSize*scale){
                Draw.drawScaled(batch, nebula, x,y, scale, scale);
            }
        }
        scale = 1;
        for(int x=-1000;x<getWidth()+1000;x+=texSize*scale){
            for(int y=-1000;y<getHeight()+1000;y+=texSize*scale){
                Draw.drawScaled(batch, stars, x,y, scale, scale);
            }
        }

        Main.logTime("stars");
        super.draw(batch, parentAlpha);
        Main.logTime("other");
        for(Formation f:formations){
            f.draw(batch);
        }
    }

    public void tick() {
        for(int i=ships.size-1;i>=0;i--){
            if(ships.get(i).dead){
                ships.removeValue(ships.get(i), true);
            }
        }
        if(tanker.getX() > getWidth()-end-140){
            SpaceScreen.get().victory();
        }
        for (Formation f:formations){
            f.checkTrigger();
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
                    s.damage(4);
                }
            }
        }
    }

    public Screen getMissionInstruction(){
        Screen missionInstruction = new MissionInstructionScreen(alcoholTexture, planetTexture, spiel,
                ()->{
                    Main.self.setScreen(SpaceScreen.get(), true);
                    SpaceScreen.get().start();
                });
        IncomingMissionScreen incoming = new IncomingMissionScreen();
        incoming.setRunnable(()-> Main.self.setScreen(missionInstruction, Main.TransitionType.LEFT, Interpolation.pow2Out, .5f));
        return incoming;
    }

    public float getProgress() {
        return Math.min(1,(tanker.getX()-start)/(getWidth()-start-end));
    }
}
