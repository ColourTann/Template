package com.tann.jamgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Fonts;
import com.tann.jamgame.util.Screen;

public class IntroScreen extends Screen {

	final float INTRO_WIDTH = 300;
	final float CAMERA_SCROLL_SPEED_SLOW = 15f;
	final float CAMERA_SCROLL_SPEED_FAST = 250f;
	final float CAMERA_DISTANCE = 100f;

	Stage stage;
	PerspectiveCamera camera;
	Batch batch;
	BitmapFont font;
	String introText;
	float scrollLimit = 250f; // When the camera is past this scroll limit, the scene is over.

	static IntroScreen singleton = null;
	public static IntroScreen get() {
		if(singleton == null){
			singleton = new IntroScreen();
			singleton.init();
		}
		return singleton;
	}

	private void init(){
		camera = new PerspectiveCamera(65, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(new ScreenViewport(camera));
		batch = stage.getBatch();
		font = Fonts.fontSmall;

		// Load the intro text and, based on its length, find out how far the camera has to scroll before we switch scenes.
		introText = Gdx.files.internal("intro_text.txt").readString();
		for(char c : introText.toCharArray()) {
			if(c == '\n') {
				scrollLimit -= 20.0f; // Approximated.  Maybe changes with camera distance.
			}
		}
		System.out.println(scrollLimit);

		// Start with the camera at the top of the font and at a 45 degree angle.  Move it down each step.
		camera.position.set(0f, -CAMERA_DISTANCE, CAMERA_DISTANCE);
		camera.lookAt(0f, 0f, 0f); // Look a bit up.
		camera.far = 1000f;
	}

	@Override
	public void preDraw(Batch batch) {}

	@Override
	public void postDraw(Batch batch) {
		//Fonts.fontSmall.setColor(Color.YELLOW);
		camera.update(true);
		batch.setProjectionMatrix(camera.combined);
		font.setColor(Colours.light);
		font.draw(batch, introText, -INTRO_WIDTH/2, 0, INTRO_WIDTH, Align.center, true);
	}

	boolean done = false;

	@Override
	public void act(float delta) {
		super.act(delta);
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			camera.translate(0f, -1 * CAMERA_SCROLL_SPEED_FAST * delta, 0f);
		} else {
			camera.translate(0f, -1 * CAMERA_SCROLL_SPEED_SLOW * delta, 0f);
		}

		if(!done && camera.position.y < scrollLimit) {
		    done = true;
		    SpaceScreen.get().map.setup();
			Main.self.setScreen(SpaceScreen.get().map.getMissionInstruction(), Main.TransitionType.LEFT, Interpolation.pow2Out, .5f);
		}
	}

	@Override
	public void preTick(float delta) {

	}

	@Override
	public void postTick(float delta) {

	}

	@Override
	public void keyPress(int keycode) {
		// No key release?
	}
}
