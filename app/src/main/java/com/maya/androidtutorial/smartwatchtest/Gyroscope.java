package com.maya.androidtutorial.smartwatchtest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.util.Log;


/**
 * Created by nicolas on 03.02.2016.
 */
public class Gyroscope implements SensorEventListener {

    public final static int KICK_FRONT = 1;
    public final static int KICK_REAR = 2;

    private long lastKick;
    private ContentContainer cc;

    public Gyroscope(ContentContainer cc){
        this.cc = cc;
        lastKick = System.currentTimeMillis();
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (System.currentTimeMillis() - lastKick > 1000) {
            if (event.values[0] > 2.0) {
                lastKick  = System.currentTimeMillis();
                cc.switchCharset(KICK_REAR);
            }
            if (event.values[0] < -5.0) {
                lastKick = System.currentTimeMillis();
                cc.switchCharset(KICK_FRONT);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
