package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.Main;
import java.util.HashMap;

public class TannFont {
    public static TannFont font = new TannFont(Main.atlas.findRegion("font/font"));
    HashMap<Character, TextureRegion> glyphs = new HashMap<Character, TextureRegion>();
    private static final int kerningGap = 1;
    //this is not really ideal, I could make it dynamically-detect heights but I think I'll only ever be using one font
    int[] heights= new int[]{5,5,5};
    public TannFont(TextureRegion font) {
        Pixmap p = Draw.getPixmap(font);
        //all the characters split by rows
        String[] chars =  new String[]{"0123456789.,!?:()\"+-/_%='@<>","ABCDEFGHIJKLMNOPQRSTUVWXYZ","abcdefghijklmnopqrstuvwxyz"};
        int x=0;
        int y=0;
        for(int row=0;row<3;row++){
            for(char c:chars[row].toCharArray()){
                for(int dx=0;true;dx++){
                    //check to see if the column contains no set pixels
                    //this will only work if no characters in the font have vertical gaps
                    boolean empty=true;
                    for(int dy=0;dy<heights[row];dy++){
                        int col = p.getPixel(font.getRegionX()+x+dx, font.getRegionY()+y+dy);
                        if(col!=-256 && col != 0){
                            empty=false;
                            break;
                        }
                    }
                    if(!empty)continue;
                    glyphs.put(c, new TextureRegion(font, x, y, dx, heights[row]));
                    //move over by glyphWidth + 1
                    x+=dx+1;
                    break;
                }
            }
            x=0;
            y+=heights[row]+1;
        }
        //hacky workaround for spaces :D
        int spaceWidth=1;
        glyphs.put(' ', new TextureRegion(font, font.getRegionWidth()-spaceWidth, 0, spaceWidth, 0));
    }


    public void drawString(Batch batch, String text, int x, int y){
        drawString(batch, text, x, y, false);
    }


    public void drawString(Batch batch, String text, int x, int y, boolean fixedWidth){
        drawString(batch, text, x, y, fixedWidth, false, false);
    }

    public static float bonusSin=0;
    private static final float freq = 6.5f, amp=2, letterAdd=.08f;

    public void drawString(Batch batch, String text, int x, int y, boolean fixedWidth, boolean wiggle, boolean sin){
        float noise = (Main.getNoiseFromTicks()+1)/2;
        for(int i=0;i<text.length();i++){
            char c = text.charAt(i);
            TextureRegion t= glyphs.get(c);
            int plusY=0;
            if(wiggle){
                if(Math.random()*noise>.5) {
                    plusY = (int) (Math.random() * 2) - 0;
                }
            }
            if(sin){
                plusY = (int)(Math.sin((Main.ticks+bonusSin)*freq)*amp);
                bonusSin += letterAdd;
            }
            Draw.draw(batch, t, x, y+plusY);
            if(fixedWidth) x+= getDefaultWidth()+kerningGap;
            else x+=t.getRegionWidth()+kerningGap;
        }
    }

    public void drawString(Batch batch, String text, int x, int y, int align) {
        if(align== Align.center){
            drawString(batch, text, x-getWidth(text)/2, y-getHeight()/2, false);
        }

    }

    public int getWidth(String text){
        return getWidth(text, false);
    }

    public int getWidth(String text, boolean fixedWidth){
        //need to take into account spaces
        if(fixedWidth) return text.length()*getDefaultWidth();
        int total=0;
        for(int i=0;i<text.length();i++){
            char c = text.charAt(i);
            total+=glyphs.get(c).getRegionWidth();
//            if(c==' ')total+=1;
            if(i<text.length()-1)total+=1;
        }
        return total;
    }

    private int getDefaultWidth() {
        return 3;
    }
    public int getHeight(){
        return 5;
    }
    public int getLineHeight(){
        return 6;
    }
    public int getSpaceWidth(){
        return 4;
    }

}