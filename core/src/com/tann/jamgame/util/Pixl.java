package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.List;

public class Pixl {
    Group g;
    int baseGap;
    int forceWidth;
    List<Row> rows = new ArrayList<>();
    Row currentRow;
    public Pixl(Group g, int baseGap) {
        this(g, baseGap, -1);
    }

    public Pixl(Group g, int baseGap, int forceWidth) {
        this.g = g;
        this.baseGap = baseGap;
        this.forceWidth = forceWidth;
        currentRow = new Row(baseGap);
    }

    public Pixl row(){
        row(baseGap);
        return this;
    }

    public Pixl row(int gap){
        if(currentRow.elementList.size()==0){
            currentRow.aboveRowGap = gap;
            return this;
        }
        currentRow.finish();
        rows.add(currentRow);
        currentRow = new Row(gap);
        return this;
    }

    public Pixl gap(int gap){
        currentRow.addGap(gap);
        return this;
    }

    public Pixl actor(TextureRegion tr){
        currentRow.addActor(new ImageActor(tr));
        return this;
    }

    public Pixl actor(Actor a){
        currentRow.addActor(a);
        return this;
    }

    public void pix(){
        if(!currentRow.elementList.isEmpty()){
            row();
        }
        currentRow.finish();
        rows.add(currentRow);
        int maxWidth = 0;
        int totalHeight = 0;
        for(Row r:rows){
            totalHeight += r.getHeight();
            maxWidth = Math.max(r.getWidth(), maxWidth);
        }
        if(forceWidth != -1){
            maxWidth = forceWidth;
        }
        g.setSize(maxWidth, totalHeight);
        int currentY = (int) g.getHeight();
        for(Row r:rows){
            currentY -= r.aboveRowGap;
            int usedWidth =0;
            for(Element e:r.elementList){
                if(e.a!=null){
                    usedWidth += (int) e.a.getWidth();
                }
                else{
                    usedWidth += e.gap;
                }
            }
            int currentX = (int) ((g.getWidth()-usedWidth)/2);
            int rowHeight = r.getHeight();
            int rowHeightExcludingAboveGap = rowHeight-r.aboveRowGap;
            for(Element e:r.elementList){
                if(e.a!=null){
                    g.addActor(e.a);
                    e.a.setPosition(currentX, (int)(currentY-rowHeightExcludingAboveGap/2f-e.a.getHeight()/2f));
                    currentX += e.a.getWidth();
                }
                else{
                    currentX += e.gap;
                }
            }
            currentY -= rowHeight;
            currentY += r.aboveRowGap;
        }
    }

    public Pixl text(String s) {
        return actor(new TextWriter(s));
    }

    public Pixl image(TextureRegion image) {
        return actor(new ImageActor(image));
    }

    class Row{
        List<Element> elementList = new ArrayList<>();
        int aboveRowGap;
        public Row(int aboveRowGap) {
            this.aboveRowGap = aboveRowGap;
        }
        void addActor(Actor a){
            if(elementList.size()==0 || elementList.get(elementList.size()-1).a!=null){
                addGap(baseGap);
            }
            elementList.add(new Element(a));
        }
        void addGap(int gap){
            elementList.add(new Element(gap));
        }

        public int getWidth() {
            int total = 0;
            for(Element e:elementList){
                total += e.getWidth();
            }
            return total;
        }


        public int getHeight() {
            int maxActorHeight = 0;
            for(Element e:elementList){
                maxActorHeight = (int) Math.max(maxActorHeight, e.a==null?0:e.a.getHeight());
            }
            return maxActorHeight+aboveRowGap;
        }

        public void finish() {
            if(elementList.size()>0 && elementList.get(elementList.size()-1).a!=null){
                elementList.add(new Element(baseGap));
            }
        }
    }

    static class Element{
        int gap;
        Actor a;

        public Element(Actor a) {
            this.a = a;
        }

        public Element(int gap) {
            this.gap = gap;
        }

        public int getWidth() {
            if(a==null){
                return gap;
            }
            return (int) a.getWidth();
        }
    }

}
