package com.tann.jamgame.screen.gameScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.map.Map;
import com.tann.jamgame.screen.gameScreen.map.Minimap;
import com.tann.jamgame.util.EscMenu;
import com.tann.jamgame.util.Screen;

public class GameScreen extends Screen{

    private Map map;
    private Minimap minimap;
    public GameScreen() {
        map = Map.get();
        addActor(map);
        addActor(minimap = new Minimap(map));
    }

    public void init(){

    }

    @Override
    public void preDraw(Batch batch) {
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
    }

    @Override
    public void postTick(float delta) {
        minimap.setPosition(Main.self.orthoCam.position.x-Main.width/2, Main.self.orthoCam.position.y-Main.height/2);

    }

    @Override
    public void keyPress(int keycode) {
        switch(keycode){
            case Input.Keys.ESCAPE:
                toggleEscMenu();
                break;
        }
    }
}
