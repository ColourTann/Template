package com.tann.jamgame.screen.gameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.OtherScreen;
import com.tann.jamgame.util.*;

public class GameScreen extends Screen{

    public GameScreen() {
        TextButton tb = new TextButton(100, 10, "another screen??");
        addActor(tb);
        tb.setPosition(200, 50);
        tb.setRunnable(()->Main.self.setScreen(new OtherScreen(), Main.TransitionType.LEFT, Interpolation.pow2Out, .3f) );
    }

    @Override
    public void preDraw(Batch batch) {
        batch.setColor(Colours.dark);
        Draw.fillActor(batch,this);
        batch.setColor(Colours.green);
        TannFont.font.drawString(batch, "yeah boiee", 50, 50);
    }

    @Override
    public void postDraw(Batch batch) {

    }

    private void toggleEscMenu() {
        System.out.println("togg");
        if(stack.size==0 || stack.get(stack.size-1)!=EscMenu.get()){
            push(EscMenu.get());
        }
        else{
            pop();
        }
    }
    private static Actor inputBlocker;
    private static Array<Actor> stack = new Array<>();
    public void push(Actor a){

        if(inputBlocker==null){
            inputBlocker = new Actor();
            inputBlocker.setSize(Main.width, Main.height);
            inputBlocker.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    pop();
                    return true;
                }
            });
        }

        stack.add(a);

        addActor(inputBlocker);
        addActor(a);
    }
    public void pop(){
        if(stack.size>0){
            Actor a = stack.removeIndex(stack.size-1);
            a.remove();
        }
        if(stack.size==0){
            if(inputBlocker!=null){
                inputBlocker.remove();
            }
        }
        else{
            stack.get(stack.size-1).toFront();
        }
    }


    @Override
    public void preTick(float delta) {
        TextWisp textWisp = new TextWisp("WEEEEeeeeee...");
        textWisp.setPosition(Gdx.input.getX()/Main.scale, Main.height-(Gdx.input.getY())/Main.scale);
        addActor(textWisp);

    }

    @Override
    public void postTick(float delta) {

    }

    @Override
    public void keyPress(int keycode) {
        System.out.println("keypress");
        switch(keycode){
            case Input.Keys.ESCAPE:
                toggleEscMenu();
                break;
        }
    }
}
