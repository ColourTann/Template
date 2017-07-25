package com.tann.jamgame.util;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.Main;

public class Draw {
	
	public static void setup(){
        circle150 = Main.atlas.findRegion("circle150");
        circle300 = Main.atlas.findRegion("circle300");
	}
	
	public static Draw draww = new Draw();
	//Texture stuff//
	//noncentered stuff//

	public static void draw(Batch batch, Texture t, float x, float y) {
		drawRotatedScaled(batch, t, x, y, 1, 1, 0);
	}

	public static void drawScaled(Batch batch, Texture t, float x, float y,
                                  float scaleX, float scaleY) {
		drawRotatedScaled(batch, t, x, y, scaleX, scaleY, 0);
	}

	public static void drawRotatedScaled(Batch batch, Texture t, float x,
                                         float y, float scaleX, float scaleY, float radianRotation) {
		drawRotatedScaledFlipped(batch, t, x, y, scaleX, scaleY,
				radianRotation, false, false);
	}

	public static void drawRotatedScaledFlipped(Batch batch, Texture t,
                                                float x, float y, float scaleX, float scaleY, float radianRotation,
                                                boolean xFlip, boolean yFlip) {
		batch.draw(t, x, y, 0, 0, t.getWidth(), t.getHeight(), scaleX, scaleY,
				rad2deg(radianRotation), 0, 0, t.getWidth(), t.getHeight(),
				xFlip, yFlip);
	}

	//centered stuff//

	public static void drawCentered(Batch batch, Texture t, float x, float y) {
		drawCenteredRotatedScaled(batch, t, x, y, 1, 1, 0);
	}

	public static void drawCenteredScaled(Batch batch, Texture t, float x,
                                          float y, float scaleX, float scaleY) {
		drawCenteredRotatedScaled(batch, t, x, y, scaleX, scaleY, 0);
	}

	public static void drawCenteredRotated(Batch batch, Texture t, float x,
                                           float y, float radianRotation) {
		drawCenteredRotatedScaled(batch, t, x, y, 1, 1, radianRotation);
	}

	public static void drawCenteredRotatedScaled(Batch batch, Texture t,
                                                 float x, float y, float xScale, float yScale, float radianRotation) {
		drawCenteredRotatedScaledFlipped(batch, t, x, y, xScale, yScale,
				radianRotation, false, false);
	}

	public static void drawCenteredRotatedScaledFlipped(Batch batch, Texture t,
                                                        float x, float y, float xScale, float yScale, float radianRotation,
                                                        boolean xFlip, boolean yFlip) {
		batch.draw(t, (x - t.getWidth() / 2), (y - t.getHeight() / 2),
				t.getWidth() / 2f, t.getHeight() / 2f, t.getWidth(),
				t.getHeight(), xScale, yScale, rad2deg(radianRotation), 0, 0,
				t.getWidth(), t.getHeight(), xFlip, yFlip);
	}

	//TextureRegion stuff//
	
	public static void draw(Batch batch, TextureRegion t, float x, float y) {
		drawRotatedScaled(batch, t, x, y, 1, 1, 0);
	}

	public static void drawScaled(Batch batch, TextureRegion t, float x,
                                  float y, float scaleX, float scaleY) {
		drawRotatedScaled(batch, t, x, y, scaleX, scaleY, 0);
	}


	public static void drawRotatedScaled(Batch batch, TextureRegion t, float x,
                                         float y, float scaleX, float scaleY, float radianRotation) {
		batch.draw(t, x, y, 0f, 0f,
				(t.getRegionWidth()),
				(t.getRegionHeight()),
				scaleX, scaleY, rad2deg(radianRotation));
	}

	//centered stuff//

	public static void drawCentered(Batch batch, TextureRegion t, float x,
                                    float y) {
		drawCenteredRotatedScaled(batch, t, x, y, 1, 1, 0);
	}

	public static void drawCenteredScaled(Batch batch, TextureRegion t,
                                          float x, float y, float scaleX, float scaleY) {
		drawCenteredRotatedScaled(batch, t, x, y, scaleX, scaleY, 0);
	}


	public static void drawCenteredRotated(Batch batch, TextureRegion t,
                                           float x, float y, float radianRotation) {
		drawCenteredRotatedScaled(batch, t, x, y, 1, 1, radianRotation);
	}

	public static void drawCenteredRotatedScaled(Batch batch, TextureRegion t,
                                                 float x, float y, float xScale, float yScale, float radianRotation) {
		drawCenteredRotatedScaledFlipped(batch, t, x, y, xScale, yScale,
				radianRotation);
	}

	public static void drawCenteredRotatedScaledFlipped(Batch batch,
                                                        TextureRegion t, float x, float y, float scaleX, float scaleY,
                                                        float radianRotation) {
		batch.draw(t, x - t.getRegionWidth() / 2f,
				y - t.getRegionHeight() / 2f, t.getRegionWidth() / 2f,
				t.getRegionHeight() / 2f, t.getRegionWidth(),
				t.getRegionHeight(), scaleX, scaleY, rad2deg(radianRotation));
	}

	//geometric stuff//
	
	public static void drawRectangle(Batch batch, float x, float y,
                                     float width, float height, int lineWidth) {
		drawScaled(batch, getSq(), x, y, width, lineWidth);
		drawScaled(batch, getSq(), x, y + height - lineWidth, width, lineWidth);
		drawScaled(batch, getSq(), x, y + lineWidth, lineWidth, height
				- lineWidth * 2);
		drawScaled(batch, getSq(), x + width - lineWidth, y + lineWidth,
				lineWidth, height - lineWidth * 2);
	}

	public static void fillRectangle(Batch batch, float x, float y, float width, float height) {
        Draw.drawScaled(batch, Draw.getSq(), x, y, width, height);
    }

    public static TextureRegion circle150;
    public static TextureRegion circle300;
	public static void fillEllipse(Batch batch, float x, float y, float width, float height){
		Draw.drawScaled(batch, circle300, x, y, width/300f, height/300f);
	}
	
	public static void drawLine(Batch batch, float x, float y, float tX,
                                float tY, float width) {
		float dist = (float) Math.sqrt((tX - x) * (tX - x) + (tY - y)
				* (tY - y));
		float radians = (float) Math.atan2(tY - y, tX - x);

		x += Math.sin(radians) * width / 2f;
		y -= Math.cos(radians) * width / 2f;

		Draw.drawRotatedScaled(batch, getSq(), x, y, dist, width, radians);
	}

	
	
	
	//utlities//
	
	public static float rad2deg(float rad) {
		return (float) (rad * 180f / Math.PI);
	}

	private static TextureRegion whiteSquare;

	public static TextureRegion getSq() {
		if (whiteSquare == null) {
			whiteSquare = Main.atlas.findRegion("pixel");
		}
		return whiteSquare;
	}
	
	public static Pixmap getPixmap(Texture t){
		t.getTextureData().prepare();
		return t.getTextureData().consumePixmap();
	}
	
	public static Pixmap getPixmap(TextureRegion t){
		return getPixmap(t.getTexture());
//		t.getTexture().getTextureData().prepare();
//		return t.getTexture().getTextureData().consumePixmap();
	}

	public static void drawSize(Batch batch, TextureRegion textureRegion, float x, float y, float width, float height) {
		batch.draw(textureRegion, x, y, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), width/textureRegion.getRegionWidth(), height/textureRegion.getRegionHeight(), 0);
	}

	public static void drawSizeCentered(Batch batch, TextureRegion textureRegion, float x, float y, float width, float height) {
		batch.draw(textureRegion, x-width/2, y-height/2, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), width/textureRegion.getRegionWidth(), height/textureRegion.getRegionHeight(), 0);
	}

	public static void fillActor(Batch batch, Actor a) {
        Draw.fillRectangle(batch, a.getX(), a.getY(), a.getWidth()*a.getScaleX(), a.getHeight()*a.getScaleY());
    }

	public static void fillActor(Batch batch, Actor a, Color bg, Color border, float borderSize) {
		batch.setColor(border);
		Draw.fillRectangle(batch, a.getX()-borderSize, a.getY()-borderSize, a.getWidth()*a.getScaleX()+borderSize*2*a.getScaleX(), a.getHeight()*a.getScaleY()+borderSize*2*a.getScaleY());
		batch.setColor(bg);
		Draw.fillRectangle(batch, a.getX(), a.getY(), a.getWidth()*a.getScaleX(), a.getHeight()*a.getScaleY());
	}

	public static void drawArrow(Batch batch, float x, float y, float x1, float y1, int width){
	    Draw.drawLine(batch, x,y,x1,y1,width);
	    double angle = Math.atan2(y1-y, x1-x);
	    int angleDiff = -40;
	    int dist = 20;
	    double angle1 = angle + angleDiff;
        double angle2 = angle - angleDiff;
        Draw.drawLine(batch, x1, y1, x1+(float)(Math.cos(angle1)*dist), y1+(float)(Math.sin(angle1)*dist), width);
        Draw.drawLine(batch, x1, y1, x1+(float)(Math.cos(angle2)*dist), y1+(float)(Math.sin(angle2)*dist), width);
    }

    public static void drawLoadingAnimation(Batch batch, float x, float y, float radius, float size, float speed, int numDots){
	    for(int i=0;i<numDots;i++){
	        double angle = ((Math.PI*2)/numDots)*i;
	        angle += Main.ticks*speed;
            float drawX = (float)(x + Math.sin(angle)*radius);
            float drawY = (float)(y + Math.cos(angle)*radius);
	        Draw.fillEllipse(batch, drawX-size/2, drawY-size/2, size, size);
        }

    }

}
