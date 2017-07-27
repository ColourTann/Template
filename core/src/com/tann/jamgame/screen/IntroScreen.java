package com.tann.jamgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tann.jamgame.Main;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Fonts;
import com.tann.jamgame.util.Screen;

public class IntroScreen extends Screen {

	final float INTRO_WIDTH = 300;
	final float CAMERA_SCROLL_SPEED = 10f;
	final float CAMERA_DISTANCE = 100f;

	Stage stage;
	PerspectiveCamera camera;
	Batch batch;
	BitmapFont font;
	String introText;

	static IntroScreen singleton = null;
	public static IntroScreen get() {
		if(singleton == null){
			singleton = new IntroScreen();
			singleton.init();
		}
		return singleton;
	}

	private void init(){
		camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new ScreenViewport(camera));
		batch = stage.getBatch();
		font = Fonts.font;

		introText = Gdx.files.internal("intro_text.txt").readString();

		// Start with the camera at the top of the font and at a 45 degree angle.  Move it down each step.
		camera.position.set(0f, -CAMERA_DISTANCE, CAMERA_DISTANCE);
		camera.lookAt(0f, 0f, 0f); // Look a bit up.
		camera.far = 1000f;
	}

	@Override
	public void preDraw(Batch batch) {}

	@Override
	public void postDraw(Batch batch) {
		Fonts.fontSmall.setColor(Color.WHITE);
		camera.update(true);
		batch.setProjectionMatrix(camera.combined);
		font.draw(batch, introText, -INTRO_WIDTH/2, 0, INTRO_WIDTH, Align.center, true);
		Draw.fillRectangle(batch, 0, 0, 1, 1f);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		camera.translate(0f, -1*CAMERA_SCROLL_SPEED*delta, 0f);
	}

	@Override
	public void preTick(float delta) {

	}

	@Override
	public void postTick(float delta) {

	}

	@Override
	public void keyPress(int keycode) {

	}
}
