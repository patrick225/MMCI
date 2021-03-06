package com.maya.androidtutorial.smartwatchtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.wearable.view.DismissOverlayView;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * Created by patrick on 09.12.15.
 *
 * erzeugt content container und bearbeitet den long click
 */
public class MainActivity extends Activity {

    private TextView mTextView;
    private DrawView drawView;

    private GestureDetectorCompat gestureDetector;
    private ContentContainer cc;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        cc = new ContentContainer(this);
        ViewGroup.LayoutParams lp =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(cc, lp);

        gestureDetector = new GestureDetectorCompat(this, new LongPressListener());

        sensorData();

    }

    /**
     * registiert einen gyroscop listener
     */
    public void sensorData(){
        Gyroscope listener = new Gyroscope(cc);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(listener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.dispatchTouchEvent(event);
    }

    /**
     * ermittelt ob ein long click vorliegt und beendet die activity
     */
    public class LongPressListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent event) {

            Intent intent = new Intent();
            intent.putExtra(IncomingMessageActivity.INTENT_STRING_RESULT, cc.getResultString());
            setResult(IncomingMessageActivity.INTENT_GETTEXT, intent);
            finish();

        }

    }
}
