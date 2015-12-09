package com.maya.androidtutorial.smartwatchtest;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

/**
 * Created by patrick on 08.12.15.
 */
public class ContentContainer extends RelativeLayout implements View.OnTouchListener {


    public static int SCREENRADIUS;
    Context context;


    private ArrayList<Character> text;

    Coordinates coordinates = new Coordinates(SCREENRADIUS, SCREENRADIUS);

    int textPadding;

    Circle circleTextBackground;
    Pie pieAll;
    Pie pieSingle;
    CircleText circleText;

    public ContentContainer(Context context) {
        super(context);

        this.context = context;

        getDisplaySize();
        text = new ArrayList<>();

        // white Circle
        Paint paintText = new Paint();
        paintText.setColor(Color.parseColor(String.valueOf(Colors.HANNELORE)));
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);
        circleTextBackground = new Circle(context, SCREENRADIUS, paintText);
        addView(circleTextBackground);


        // pie
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textPadding = SCREENRADIUS / 6;
        pieAll = new Pie(context, textPadding, Pie.TYPE_ALL);
        addView(pieAll);


        // Output text
        circleText = new CircleText(context);
        addView(circleText);

        
        // character labels


        setOnTouchListener(this);

    }



    private void getDisplaySize() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        wm.getDefaultDisplay().getMetrics(metrics);

        SCREENRADIUS = metrics.widthPixels / 2;
    }


    private int getPiePart (MotionEvent event) {

        PolarCoordinates pc = coordinates.getPositionPolar((int) event.getX(), (int) event.getY());

        int pie = -1;
        for (int i = 0; i < 6; i++) {
            if (pc.angle > i * 60 && pc.angle < i * 60 + 60 && pc.radius < SCREENRADIUS - textPadding) {
                pie = i;
            }
        }

        return pie;
    }



    private void refreshText() {

        Character[] textAr = text.toArray(new Character[text.size()]);
        circleText.setText(new String(ArrayUtils.toPrimitive(textAr)));

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                int piePart = getPiePart(event);
                if (piePart != -1) {
                    pieSingle = new Pie(context, textPadding, piePart);
                    Log.i("action down", "on pie");
                    addView(pieSingle);
                }
                // TODO clicks on other elements, do action on action up

                break;
            case MotionEvent.ACTION_MOVE:
                if (pieSingle != null) {
                    // TODO do any feedback
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("action up", "everywhere");
                // check if first click was in range of characters
                if (pieSingle != null) {
                    removeView(pieSingle);
                    pieSingle = null;



                    text.add('a');
                    refreshText();

                    // TODO get Character and save to the current Cursor position
                }
                break;
        }

        return true;
    }
}
