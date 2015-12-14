package com.maya.androidtutorial.smartwatchtest;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

    Coordinates coordinates;

    int textPadding;
    int deleteButtonRadius;

    Circle circleTextBackground;
    Pie pieAll;
    Pie pieSingle;
    CircleText circleText;
    char[][] chars = {
            {'E', 'Ä', 'A', 'B', 'C', 'D'},
            {'W', 'S', 'T', 'Ü', 'U', 'V'},
            {'P', 'Q', 'R', 'X', 'Y', 'Z'},
            {'F', 'G', 'H', 'I', 'J', ' '},
            {'O', 'K', 'L', 'M', 'N', 'Ö'},
            {'?', '!', '-', ':', '.', '_'}
    };
    CircleCharacters[] circleChars = new CircleCharacters[6];
    CircleCharacters singleChars;

    int firstPie;

    public ContentContainer(Context context) {
        super(context);
        this.context = context;
        getDisplaySize();



        coordinates = new Coordinates(SCREENRADIUS, SCREENRADIUS);
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
        for (int i = 0; i < chars.length; i++) {
            CartesianCoordinates center = coordinates.getPositionRawCartesian((int) ((SCREENRADIUS - textPadding) * 0.6), i * 60);
            circleChars[i] = new CircleCharacters(context, chars[i], center, 25, chars[i][i]);
            addView(circleChars[i]);
        }

        // delete Button
        deleteButtonRadius = SCREENRADIUS / 7;
        DeleteButton deleteButton = new DeleteButton(context, deleteButtonRadius);
        Paint circlepaint = new Paint();
        circlepaint.setColor(Color.parseColor(String.valueOf(Colors.HANNELORE)));
        circlepaint.setAntiAlias(true);
        circlepaint.setStyle(Paint.Style.FILL);
        circlepaint.setAlpha(170);
        Circle deleteCircle = new Circle(context, deleteButtonRadius, circlepaint);

        addView(deleteCircle);
        addView(deleteButton);

        // statedisplay

        CharactersetDisplay charDisplay = new CharactersetDisplay(context, SCREENRADIUS - textPadding, textPadding);

        addView(charDisplay);

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

        if (pc.radius > SCREENRADIUS - textPadding ||
            pc.radius < deleteButtonRadius) return pie;

        if (pc.angle > 330 || pc.angle <  30) pie = 0;
        if (pc.angle >  30 && pc.angle <  90) pie = 1;
        if (pc.angle >  90 && pc.angle <  150) pie = 2;
        if (pc.angle > 150 && pc.angle <  210) pie = 3;
        if (pc.angle > 210 && pc.angle <  270) pie = 4;
        if (pc.angle > 270 && pc.angle <  330) pie = 5;

        return pie;
    }

    private boolean deleteButtonTouched(MotionEvent event) {

        PolarCoordinates coord = coordinates.getPositionPolar((int) event.getX(), (int) event.getY());
        if (coord.radius < deleteButtonRadius)
            return true;

        return false;
    }


    private void refreshText() {

        Character[] textAr = text.toArray(new Character[text.size()]);
        circleText.setText(new String(ArrayUtils.toPrimitive(textAr)));

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                firstPie = getPiePart(event);
                if (firstPie != -1) {
                    pieSingle = new Pie(context, textPadding, firstPie);
                    singleChars = new CircleCharacters(context, chars[firstPie],
                            new CartesianCoordinates(SCREENRADIUS, SCREENRADIUS), 80);

                    addView(pieSingle);
                    addView(singleChars);
                }
                if (deleteButtonTouched(event) && !text.isEmpty()) {
                    text.remove(text.size() - 1);
                    refreshText();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (pieSingle != null) {
                }
                break;
            case MotionEvent.ACTION_UP:
                // check if first click was in range of characters
                if (pieSingle != null) {
                    removeView(pieSingle);
                    removeView(singleChars);
                    pieSingle = null;

                    int upPie = getPiePart(event);

                    if (upPie != -1) {
                        char newchar = chars[firstPie][upPie];
                        if (newchar == '_') newchar = ' ';
                        text.add(newchar);
                        refreshText();
                    }
                }
                break;
        }

        return true;
    }
}
