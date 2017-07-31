package com.tann.jamgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.tann.jamgame.screen.IntroScreen;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.util.*;

public class Main extends ApplicationAdapter {
    public static int width = 1000, height = 700;
    public static String version = "0";
    SpriteBatch batch;
    Stage stage;
    public static OrthographicCamera orthoCam;
    public static TextureAtlas atlas;
    public static Main self;
    public static boolean debug = false;
    public static boolean showFPS = false;
    public static boolean chadwick = false;
    Screen currentScreen;
    Screen previousScreen;
    public static float ticks;

    public enum MainState {
        Normal, Paused
    }

    private static long previousTime;
    public static void logTime(String id){
        if(!chadwick) return;
        long currentTime = System.currentTimeMillis();
        if(id!=null){
            System.out.println(id+": "+(currentTime-previousTime));
        }
        previousTime = System.currentTimeMillis();
    }

    public Main(){};

    public Main(int width, int height){
        Main.width = width;
        Main.height=height;
    }

    @Override
    public void resize(int width, int height) {
        Main.width = width;
        Main.height=height;
        orthoCam.setToOrtho(false, width, height);
        stage.getViewport().update(width, height);
        Fonts.setup();
    }

    @Override
    public void create() {
        Main.width = Gdx.graphics.getWidth();
        Main.height = Gdx.graphics.getHeight();
        Sounds.setup();
        atlas = new TextureAtlas(Gdx.files.internal("atlas_image.atlas"));
        self = this;
        Draw.setup();
        Fonts.setup();
        stage = new Stage();
        orthoCam = (OrthographicCamera) stage.getCamera();
        batch = (SpriteBatch) stage.getBatch();
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                currentScreen.keyPress(keycode);
                return true;
            }
        });

        TitleScreen jamTitle = new TitleScreen(new Texture(Gdx.files.internal("singleImages/jamlogo.png")));
        TitleScreen gameTitle = new TitleScreen(new Texture(Gdx.files.internal("singleImages/blasterlogo.png")));
        jamTitle.setRunnable(()->setScreen(gameTitle, TransitionType.LEFT, Interpolation.pow2Out, .4f));
        gameTitle.setRunnable(()->setScreen(IntroScreen.get(), TransitionType.LEFT, Interpolation.pow2Out, .4f));

//        SpaceScreen.get().map.setup();
//        setScreen(SpaceScreen.get().map.getMissionInstruction());
        setScreen(jamTitle, true);
//        setScreen(SpaceScreen.get());
//        setScreen(IntroScreen.get());
//        setScreen(new IncomingMissionScreen());
    }

    private MainState state = MainState.Normal;

    public MainState getState() {
        return state;
    }

    public void setState(MainState state) {
        this.state = state;
    }

    public enum TransitionType {
        LEFT, RIGHT
    }

    public void setScreen(final Screen screen, TransitionType type, Interpolation interp, float speed) {
        if (screen == currentScreen)
            return;
        setScreen(screen, false);
        RunnableAction ra = Actions.run(new Runnable() {
            public void run() {
                screen.setActive(true);
            }
        });
        switch (type) {
            case LEFT:
                screen.setPosition(Main.width, 0);
                screen.addAction(Actions.sequence(Actions.moveTo(0, 0, speed, interp), ra));
                if(previousScreen!=null)previousScreen.addAction(Actions.moveTo(-Main.width, 0, speed, interp));
                break;
            case RIGHT:
                screen.setPosition(-Main.width, 0);
                screen.addAction(Actions.sequence(Actions.moveTo(0, 0, speed, interp), ra));
                if(previousScreen!=null)previousScreen.addAction(Actions.moveTo(Main.width, 0, speed, interp));
                break;

        }
        if(previousScreen!=null){
            previousScreen.removeFromScreen();
            previousScreen.addAction(Actions.after(Actions.removeActor()));
        }
    }

    public void setScreen(Screen screen, boolean activate) {
        if (previousScreen != null) {
            previousScreen.clearActions();
            previousScreen.removeFromScreen();
            previousScreen.remove();
        }
        if (currentScreen != null) {
            currentScreen.clearActions();
            previousScreen = currentScreen;
            currentScreen.setActive(false);
        }
        currentScreen = screen;
        stage.addActor(screen);
        if(activate) screen.setActive(true);
    }


    @Override
    public void render() {

        logTime(null);

        long startTime = System.currentTimeMillis();

        update(Gdx.graphics.getDeltaTime());
        logTime("update");
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        logTime("draw");
//		BulletStuff.render();


        if (Main.showFPS) {
            batch.begin();
            batch.setColor(Colours.white);
            drawFPS(batch);
            batch.end();
        }

        if(Main.showFPS){
            updateFPS(System.currentTimeMillis()-startTime);
        }
        logTime("fps");
    }

    int sampleSize=60;
    long[] values = new long[sampleSize];
    int currentSample;
    private void updateFPS(long value){
        values[(currentSample++)%sampleSize]=value;
    }

    private void drawFPS(Batch batch) {
        Fonts.fontSmall.setColor(Colours.white);


        long average = 0;
        for(long l:values){
            average+=l;
        }
        average/=values.length;

        Fonts.fontSmall.draw(batch, String.valueOf(average)+":"+Gdx.graphics.getFramesPerSecond(), orthoCam.position.x, orthoCam.position.y+Main.height/2);
    }

    private static float tickMult=1;

    public void update(float delta) {
        ticks += delta;
        Sounds.tickFaders(delta);
        stage.act(delta);
    }

    public static float w(float factor){
        return Main.width/100f*factor;
    }

    public static float h(float factor){
        return Main.height/100f*factor;
    }
}
