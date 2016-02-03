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
public class Gyroscope extends Activity{
    private SensorManager mSensorManager;
    private Sensor mSensor;

    public void Gyroscope(){

    }

    public void sensorData(){
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(gyroListener, mSensor, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);

    }


    private SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            Log.e("gyroscopevalue: ", String.valueOf(event.values[0]));
            Log.e("gyroscopevalue: ", String.valueOf(event.values[1]));
            Log.e("gyroscopevalue: ", String.valueOf(event.values[2]));
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
