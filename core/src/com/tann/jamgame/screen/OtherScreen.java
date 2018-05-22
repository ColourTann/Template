package com.tann.jamgame.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.GameScreen;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Screen;
import com.tann.jamgame.util.TextButton;

public class OtherScreen extends Screen {
    @Override
    public void preDraw(Batch batch) {
        batch.setColor(Colours.light);
        Draw.fillActor(batch, this);
        TextButton tb = new TextButton(100, 10, "back again!");
        addActor(tb);
        tb.setPosition(20, 50);
        tb.setRunnable(()-> Main.self.setScreen(new GameScreen(), Main.TransitionType.RIGHT, Interpolation.pow2Out, .3f) );
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

    }
}
