package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TextWriter extends Group {
    String[] split;
    public String text;
    TannFont font = TannFont.font;

    int x;
    int y;
    Color borderCol;
    int borderSize;
    TextureRegion image;

    public TextWriter(String text) {
        this(text, Integer.MAX_VALUE);
    }
    public TextWriter(String text, TextureRegion image) {
        this(text, Integer.MAX_VALUE, null, 0, image);
    }

    public TextWriter(String text, int width) {
        this(text, width, null, 0);
    }

    public TextWriter(String text, int width, Color borderCol, int borderSize) {
        this(text, width, borderCol, borderSize, null);
    }

    public TextWriter(String text, int width, Color borderCol, int borderSize, TextureRegion image) {
        setTransform(false);
        this.text=text;
        this.borderSize = borderSize;
        this.borderCol = borderCol;
        this.image = image;
        setWidth(width);
        layout();
    }



    private static Map<String, TextureRegion> textureMap = new HashMap<>();
    private static Map<String, Color> colorMap = new HashMap<>();

    public static void setup(){
//        textureMap.put("heart", Images.heart);
//        colorMap.put("red", Colours.red);
    }

    public static String getColourTagForColour(Color c){
        for(Entry<String, Color> entry :colorMap.entrySet()){
            if(entry.getValue().equals(c)){
                return "["+entry.getKey()+"]";
            }
        }
        return "";
    }


    public void layout() {
        split = text.split("[\\[\\]]");
        Color currentColour = Colours.light;
        Color previousColour = Colours.light;
        clearChildren();
        int index = 0;
        int max = 0;
        x = 0;
        y = 0;
        boolean wiggle=false, sin=false;
        for(String s:split){
            if(s.isEmpty()){
                index++;
                continue;
            }
            if(index%2==0){
                // text
                String[] words = s.split(" ");
                if(words.length==0){
                    x += font.getSpaceWidth();
                }
                for(int i=0;i<words.length;i++){
                    String word = words[i];
                    TextBox tb = new TextBox(word, wiggle, sin);
                    tb.setTextColour(currentColour);
                    addActor(tb);
                    if(x + tb.getWidth()>=getWidth()){
                        nextLine();
                    }
                    tb.setPosition(x, y);
                    x += tb.getWidth();
                    max = Math.max(x, max);
                    if(i<words.length-1 || s.endsWith(" ")){
                        x += font.getSpaceWidth();
                    }
                }
            }
            else{
                // image or colour
                boolean image = false;
                switch(s){
                    case "h": x += font.getSpaceWidth()/2; break;
                    case "n": nextLine(); break;
                    case "nh": nextLine(); y -= font.getLineHeight()/3; break;
                    case "wiggle": wiggle=!wiggle; break;
                    case "sin": sin=!sin; break;
                    default: image = true;
                }
                Color c = colorMap.get(s);
                if(c!=null){
                    if(c == currentColour){
                        currentColour = previousColour;
                        previousColour = c;
                    }
                    else {
                        previousColour = currentColour;
                        currentColour = c;
                    }
                    image = false;
                }
                if(image) {
                    TextureRegion tr = textureMap.get(s);
                    if(s.equals("image")){
                        tr = this.image;
                    }
                    if (tr == null) {
                        System.err.println("unable to find texture '" + s + "' for string " + text);
                        return;
                    }

                    ImageActor ia = new ImageActor(tr, tr.getRegionWidth(), tr.getRegionHeight());
                    ia.setColor(currentColour);
                    addActor(ia);
                    if(x + ia.getWidth() > getWidth()){
                        nextLine();
                    }
                    ia.setX(x);
                    ia.setY((int)(y + ia.getHeight()/2f - font.getHeight()/2f));
                    x += ia.getWidth();
                    max = Math.max(x, max);
                }
            }
            index++;
        }
        setSize(max + borderSize * 2, -y + font.getHeight() + borderSize * 2);
        for(Actor a:getChildren()){
            a.setX(a.getX()+borderSize);
            a.setY(a.getY()+getHeight()-a.getHeight()-borderSize);
        }
    }

    private void nextLine() {
        x = 0;
        y -= font.getLineHeight();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(borderCol != null){
            Draw.fillActor(batch, this, Colours.dark, borderCol, 1);
        }
        super.draw(batch, parentAlpha);
    }

    public void setText(String text){
        this.text = text;
        setWidth(Integer.MAX_VALUE);
        layout();
    }
}

