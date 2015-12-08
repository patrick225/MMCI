package com.maya.androidtutorial.smartwatchtest;

import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;

/**
 * Created by patrick on 08.12.15.
 */
public class Coordinates {

    int screenSize;


    public Coordinates(int screenSize){
        this.screenSize = screenSize;
    }
    public PolarCoordinates getPositionPolar(int xabs, int yabs){

        int radius = (int) sqrt(xabs^2+yabs^2);
        int xnew = xabs - radius;
        int ynew = yabs + radius;
        double phi = atan2(ynew, xnew);

        return new PolarCoordinates(radius,phi);

    }
}
