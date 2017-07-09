package com.tann.jamgame.util;

import com.badlogic.gdx.math.*;

public class Shape {

    static Vector2 temp = new Vector2();
    static Vector2 temp2 = new Vector2();
    static Vector2 temp3 = new Vector2();
    static Polygon tempoly = new Polygon();
    static float[] tempFloats = new float[]{0,0,0,0,0,0,0,0};

    public static boolean overlaps (Shape2D s, Shape2D s2){
        if(s instanceof Circle){
            Circle d = (Circle)s;
            if(s2 instanceof Circle){
                return overlaps(d, (Circle)s2);
            }
            if(s2 instanceof Rectangle){
                return overlaps(d, (Rectangle)s2);
            }
            if(s2 instanceof Polygon){
                return overlaps(d, (Polygon)s2);
            }
        }
        if(s instanceof Rectangle){
            Rectangle d = (Rectangle) s;
            if(s2 instanceof Circle){
                return overlaps(d, (Circle)s2);
            }
            if(s2 instanceof Rectangle){
                return overlaps(d, (Rectangle)s2);
            }
            if(s2 instanceof Polygon){
                return overlaps(d, (Polygon)s2);
            }
        }
        if(s instanceof Polygon){
            Polygon d = (Polygon) s;
            if(s2 instanceof Circle){
                return overlaps(d, (Circle)s2);
            }
            if(s2 instanceof Rectangle){
                return overlaps(d, (Rectangle)s2);
            }
            if(s2 instanceof Polygon){
                return overlaps(d, (Polygon)s2);
            }
        }
        System.err.println("Can't collide "+s.getClass()+" with "+s2.getClass());
        return false;
    }

    public static boolean overlaps (Rectangle r, Rectangle r2){
        return Intersector.overlaps(r,r2);
    }

    public static boolean overlaps(Circle c1, Circle c2){
        return Intersector.overlaps(c1, c2);
    }

    public static boolean overlaps(Rectangle r, Circle c){
        return Intersector.overlaps(c,r);
    }

    public static boolean overlaps(Circle c, Rectangle r){
        return overlaps(r,c);
    }

    public static boolean overlaps(Polygon p1, Polygon p2){
        return Intersector.overlapConvexPolygons(p1, p2);
    }

    public static boolean overlaps(Circle circle, Polygon polygon) {
        return overlaps(polygon, circle);
    }

    public static boolean overlaps(Polygon polygon, Circle circle) {
        float []vertices=polygon.getTransformedVertices();
        temp.set(circle.x, circle.y);
        float squareRadius=circle.radius*circle.radius;
        for (int i=0;i<vertices.length;i+=2){
            if (i==0){
                temp2.set(vertices[vertices.length - 2], vertices[vertices.length - 1]);
                temp3.set(vertices[i], vertices[i + 1]);
                if (Intersector.intersectSegmentCircle(temp2, temp3, temp, squareRadius))
                    return true;
            } else {
                temp2.set(vertices[i-2], vertices[i-1]);
                temp3.set(vertices[i], vertices[i+1]);
                if (Intersector.intersectSegmentCircle(temp2, temp3, temp, squareRadius))
                    return true;
            }
        }
        return polygon.contains(circle.x, circle.y);
    }

    public static boolean overlaps (Polygon p, Rectangle r){
        tempFloats[2]=r.width;
        tempFloats[4]=r.width;
        tempFloats[5]=r.height;
        tempFloats[7]=r.height;
        tempoly.setVertices(tempFloats);
        tempoly.setPosition(r.x, r.y);
        return Intersector.overlapConvexPolygons(p, tempoly);
    }
    public static boolean overlaps (Rectangle r, Polygon p){
        return overlaps(p,r);
    }
}
