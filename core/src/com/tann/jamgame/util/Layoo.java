package com.tann.jamgame.util;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;
import java.util.List;

public class Layoo {
	private Group parent;
	private Row currentRow;
	private Row actuallyAColumn = new Row();
    public boolean debug=false;
	public Layoo(Group parent) {
		this.parent=parent;
	}
	private static final boolean intPos = true;

	public void row(float g){
		if(currentRow!=null){
			actuallyAColumn.add(currentRow);
		}
		actuallyAColumn.add(g);	
		currentRow = new Row();
	}
	
	public void absRow(float g){
		if(currentRow!=null){
			actuallyAColumn.add(currentRow);
		}
		actuallyAColumn.abs(g);	
		currentRow = new Row();
	}
	
	public void gap(float g){
		if(currentRow==null){
			currentRow=new Row();
		}
		currentRow.add(g);
	}
	
	public void abs(float g){
		if(currentRow==null){
			currentRow=new Row();
		}
		currentRow.abs(g);
	}
	
	public void actor(Actor a){
		if(currentRow==null){
			currentRow=new Row();
		}
		currentRow.add(a);
	}
	
	public void add(float gap1, Actor a, float gap2){
		gap(gap1); actor(a); gap(gap2);
	}

	public void add(float gap1, Actor a1, float gap2, Actor a2, float gap3){
		gap(gap1); actor(a1); gap(gap2); actor(a2); gap(gap3);
	}
	
	public void add(float gap1, Actor a1, float gap2, Actor a2, float gap3, Actor a3, float gap4){
		gap(gap1); actor(a1); gap(gap2); actor(a2); gap(gap3); actor(a3); gap(gap4);
	}

    public void layoo(){
	    layoo(false);
    }

	public void layoo(boolean slide){
        if(currentRow!=null){
			actuallyAColumn.add(currentRow);
		}
		actuallyAColumn.add(Row.INITIAL_GAP);
		//calculate
		float totalHeight=0;
		float totalGap=0;
		for(Element e:actuallyAColumn.elements){
			if(e.gap!=0) totalGap+=e.gap;
			if(e.r!=null) totalHeight+=e.r.getHeight();
			if(e.absGap!=0){
				totalHeight += e.absGap;
			}
		}
		//set y of rows
		float heightLeft = parent.getHeight()-totalHeight;
		float gapFactor = heightLeft/totalGap;
		float currentY=parent.getHeight();
		for(Element e:actuallyAColumn.elements){
			if(e.gap!=0) currentY-=e.gap*gapFactor;
			if(e.r!=null) {
				currentY -= e.r.getHeight();
				e.r.setY(currentY);
			}
			if(e.absGap!=0){
				currentY -= e.absGap;
			}
			
		}
		//tell rows to lay themselves out
		for(Element e:actuallyAColumn.elements){
			if(e.r!=null) e.r.layoo(slide);
		}
	}

	class Row{
		public static final float INITIAL_GAP=.000000001f;
		List<Element> elements = new ArrayList<>();
		float y;
		public Row() {
			if(debug) System.out.println("creating row");
			add(INITIAL_GAP);
		}
		public float getHeight() {
			float max = 0;
			for(Element e:elements){
				if(e.a!=null) max= Math.max(max, e.a.getHeight());
			}
			return max;
		}
		
		public void abs(float g){
			elements.add(new Element(g, true));
		}
		
		public void add(Row r){
			elements.add(new Element(r));
		}
		public void add(Actor a){
			if(debug) System.out.println("adding actor");
			parent.addActor(a);
			elements.add(new Element(a));
		}
		public void add(float g){
			if(debug) System.out.println("adding gap: "+g);
			elements.add(new Element(g));
		}
		public void setY(float y){
			this.y=y;
		}
		
		public void layoo(boolean slide) {
			add(INITIAL_GAP);
			float totalWidth=0;
			float totalGap=0;
			for(Element e:elements){
				if(e.gap!=0) totalGap+=e.gap;
				if(e.a!=null) totalWidth+=e.a.getWidth();
				if(e.absGap!=0) totalWidth+=e.absGap;
			}
			
			float widthLeft = parent.getWidth()-totalWidth;
			float gapFactor = widthLeft/totalGap;
			float currentX=0;
			for(Element e:elements){
				if(e.gap!=0){
					currentX+=e.gap*gapFactor;
				}
				if(e.a!=null) {
				    float newX = currentX;
				    float newY = y+getHeight()/2-e.a.getHeight()/2;
                    if(intPos){
                        newX = (int)newX;
                        newY = (int)newY;
                    }
				    if(slide){
				        e.a.addAction(Actions.moveTo(newX, newY, .5f, Interpolation.pow2Out));
                    }
                    else{
                        e.a.setPosition(newX, newY);
                    }
					currentX += e.a.getWidth();
				}
				if(e.absGap!=0){
					currentX += e.absGap;
				}
			}
		}
		
	}
	
	static class Element{
		float gap;
		float absGap;
		Actor a;
		Row r;
		public Element(Actor a) {
			this.a=a;
		}
		public Element(float gap) {
			this.gap=gap;
		}
		public Element(Row r) {
			this.r=r;
		}
		public Element(float g, boolean b) {
			this.absGap=g;
		}
	}
}
