package com.maya.androidtutorial.smartwatchtest;

/**
 * Created by privat on 05.12.2015.
 */
public class CircleSegment {

    private int id;
    private float angle;
    private float arcStart;
    private float arcEnd;
    private float midPoint;

    public CircleSegment(int id, float angle, float arcStart, float arcEnd, float midPoint) {
        this.id = id;
        this.angle = 60;
        this.arcStart = arcStart;
        this.arcEnd = arcEnd;
        this.midPoint = midPoint;
    }
}
