package com.tann.jamgame;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_ONE;
import static com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA;
import static com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tann.jamgame.screen.gameScreen.GameScreen;
import com.tann.jamgame.util.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    public static int scale;
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;
    public static int width, height;
    public static String version = "0.3.0";
    public static String versionName = "v" + version;
    SpriteBatch batch;
    SpriteBatch bufferDrawer;
    public static Stage stage;
    public OrthographicCamera orthoCam;
    public static TextureAtlas atlas;
    public static Main self;
    private static boolean showFPS = true;
    private static boolean printCalls = false;
    public static boolean debug = false;
    Screen currentScreen;
    Screen previousScreen;
    public static float ticks;
    public static int frames;

    FrameBuffer fb;

    public static Screen getCurrentScreen() {
        return self.currentScreen;
    }

    static float noiseFromTicks = 0;
    public static float getNoiseFromTicks() {
        return noiseFromTicks;
    }

    public enum MainState {
        Normal, Paused
    }

    //Callbacks

    @Override
    public void create() {

        if (printCalls) {
            System.out.println("create");
        }

        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        scale = SCREEN_HEIGHT / 180;
        width = SCREEN_WIDTH / scale;
        height = SCREEN_HEIGHT / scale;
        Sounds.setup();
        atlas = new TextureAtlas(Gdx.files.internal("2d/atlas_image.atlas"));
        self = this;
        Draw.setup();
        TextWriter.setup();
        stage = new Stage(new FitViewport(Main.width, Main.height));
        orthoCam = (OrthographicCamera) stage.getCamera();
        batch = new SpriteBatch();
        bufferDrawer = new SpriteBatch();
        fb = FrameBuffer.createFrameBuffer(Pixmap.Format.RGBA8888, width, height, true);
        fb.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        Gdx.input.setInputProcessor(stage);

        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                currentScreen.keyPress(keycode);
                return true;
            }
        });

        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        frames++;
        int sc = Main.scale;
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClear(GL_DEPTH_BUFFER_BIT);

        fb.bind();
        fb.begin();
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        stage.draw();
        fb.end();
        bufferDrawer.begin();
        Draw.drawRotatedScaledFlipped(bufferDrawer, fb.getColorBufferTexture(), 0, 0, sc, sc, 0, false, true);
        bufferDrawer.end();
    }

    private static final int SAMPLE_MAX = 60;
    private final ArrayList<Long> samples = new ArrayList<>();
    private long averageRenderTime = 0;
    private void logRenderTime(long sample) {
        samples.add(sample);
        if(samples.size()>SAMPLE_MAX){
            samples.remove(0);
        }
        long total = 0;
        for(Long l:samples){
            total += l;
        }
        averageRenderTime = total/samples.size();
    }

    public void update(float delta) {
        noiseFromTicks = (float) Noise.noise(Main.ticks,0);
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            delta *= .1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            delta *= 3;
        }
        stage.act(delta);
        Sounds.tickFaders(delta);
        ticks += delta;
        TannFont.bonusSin=0;
    }

    @Override
    public void pause() {
        super.pause();
        if (printCalls) {
            System.out.println("pause");
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        if (printCalls) {
            System.out.println("resize");
        }
    }
    private void drawFPSAndVersion() {
        batch.setColor(Colours.blue);
        int x = width/2-21;
        TannFont.font.drawString(batch, versionName, x, 1);
        x+=26;
        TannFont.font.drawString(batch, Gdx.graphics.getFramesPerSecond() + "fps", x, 1);
        if(debug) {
            x += 22;
            TannFont.font.drawString(batch, averageRenderTime + "ms", x, 1);
            x += 18;
            TannFont.font.drawString(batch, ((SpriteBatch) stage.getBatch()).renderCalls + "rc", x, 1);
        }
    }

    public static float w(float factor) {
        return Main.width / 100f * factor;
    }

    public static float h(float factor) {
        return Main.height / 100f * factor;
    }

    // phase stuff

    // screen stuff

    public enum TransitionType {
        LEFT, RIGHT
    }

    public void setScreen(final Screen screen, TransitionType type, Interpolation interp,
        float speed) {
        if (screen == currentScreen) {
            return;
        }
        setScreen(screen);
        RunnableAction ra = Actions.run(new Runnable() {
            public void run() {
                screen.setActive(true);
            }
        });
        switch (type) {
            case LEFT:
                screen.setPosition(Main.width, 0);
                screen.addAction(Actions.sequence(Actions.moveTo(0, 0, speed, interp), ra));
                if (previousScreen != null) {
                    previousScreen.addAction(Actions.moveTo(-Main.width, 0, speed, interp));
                }
                break;
            case RIGHT:
                screen.setPosition(-Main.width, 0);
                screen.addAction(Actions.sequence(Actions.moveTo(0, 0, speed, interp), ra));
                if (previousScreen != null) {
                    previousScreen.addAction(Actions.moveTo(Main.width, 0, speed, interp));
                }
                break;

        }
        if (previousScreen != null) {
            previousScreen.removeFromScreen();
            previousScreen.addAction(Actions.after(Actions.removeActor()));
        }
    }

    public void setScreen(Screen screen) {
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
        screen.setActive(true);
        currentScreen = screen;
        stage.addActor(screen);

    }

    // benchmarking

}
