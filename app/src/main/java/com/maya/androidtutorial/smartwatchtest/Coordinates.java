package com.maya.androidtutorial.smartwatchtest;

import android.graphics.Point;

import static java.lang.Math.atan2;
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

        int radius = (int) sqrt(xabs^2+yabs^2);
        int xnew = xabs - radius;
        int ynew = yabs + radius;
        double phi = atan2(ynew, xnew);

        return new PolarCoordinates(radius,phi);

    }

    public void setOrigin(int origin_x, int origin_y) {
        this.origin_y = origin_y;
        this.origin_x = origin_x;
    }

    public CartesianCoordinates getPositionRawCartesian(int radius, double angle) {

        // TODO die soll mir kartesiche koordinaten zurückgeben wobei die parameter relativ zum Origin dieser klasse sind
        // bsp: ich setz mit setOrigin den ursprung auf die mitte des bildschirms und
        // die funktion gibt mir für radius = 0 und angle = 0 den Punkt (Hälfte des Bildschirms, Hälfte des Bildschirms) zurück

        // wenn Origin auf 20, 20 gesetz wurde soll die funktion mit den gleichen parametern wie eben
        // (20, 20) zurück geben.

        // bei origin auf 20, 20 soll für parameter (radius = 10, angle = 90) der punkt
        // (10, 20) zurück kommen

        return new CartesianCoordinates();
    }

}
