package com.maya.androidtutorial.smartwatchtest;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Created by patrick on 08.12.15.
 */
public class Coordinates {

    int origin_x;
    int origin_y;


    public Coordinates(int origin_x, int origin_y){
        this.origin_x = origin_x;
        this.origin_y = origin_y;
    }
    public PolarCoordinates getPositionPolar(int xabs, int yabs){

        int radius = (int) sqrt(Math.pow(origin_x - xabs, 2) + Math.pow(origin_y - yabs, 2));

        double phi = atan2(yabs - origin_y, xabs - origin_x);

        if (phi > 0) {
            phi = 2 * Math.PI - phi;
        }
        phi = Math.abs(phi);

        return new PolarCoordinates(radius,Math.toDegrees(phi));

    }

    public void setOrigin(int origin_x, int origin_y) {
        this.origin_y = origin_y;
        this.origin_x = origin_x;
    }

    public void setOrigin(int radius, double angle) {
        origin_x = (int) (radius * cos(Math.toRadians(angle)));
        origin_y = (int) (radius * sin(Math.toRadians(angle)));


    }


    public static int getAngle (MotionEvent.PointerCoords p1, MotionEvent.PointerCoords p2) {

        double phi = atan2(p1.y - p2.y, p1.x - p2.x);

        if (phi > 0) {
            phi = 2 * Math.PI - phi;
        }
        phi = Math.abs(phi);
        phi = Math.toDegrees(phi);

        return (int) phi;
    }

    public CartesianCoordinates getPositionRawCartesian(int radius, double angle) {

        int x = (int) (radius * cos(Math.toRadians(angle)));
        int y = (int) (radius * sin(Math.toRadians(angle)));


        x += origin_x;
        y = origin_y - y;

        return new CartesianCoordinates(x, y);
    }

}
