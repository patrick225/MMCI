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
    public static final int CHARSET_CHARBIG = 1;
    public static final int CHARSET_CHARSMALL = 2;
    public static final int CHARSET_NUMBERS = 3;

    Context context;

    private ArrayList<Character> text;

    Coordinates coordinates;

    int textPadding;
    int deleteButtonRadius;

    int currentCharset;

    Circle circleTextBackground;
    Pie pieAll;
    Pie pieSingle;
    CircleText circleText;

    char[][] bigChars = {
            {'E', 'Ä', 'A', 'B', 'C', 'D'},
            {'W', 'S', 'T', 'Ü', 'U', 'V'},
            {'P', 'Q', 'R', 'X', 'Y', 'Z'},
            {'F', 'G', 'H', 'I', 'J', ' '},
            {'O', 'K', 'L', 'M', 'N', 'Ö'},
            {'?', '!', '-', ':', '.', '_'}
    };

    char[][] smallChars = {
            {'e', 'ä', 'a', 'b', 'c', 'd'},
            {'w', 's', 't', 'ü', 'u', 'v'},
            {'p', 'q', 'r', 'x', 'y', 'z'},
            {'f', 'g', 'h', 'i', 'j', ' '},
            {'o', 'k', 'l', 'm', 'n', 'ö'},
            {'?', '!', '-', ':', '.', '_'}
    };

    char[][] numbersChars = {
            {'1', '6', '[', ']', '(', ')'},
            {'"', '2', '7', '+', ';', '*'},
            {'#', '&', '3', '8', '%', '$'},
            {'=', '<', '>', '4', '9', '|'},
            {'/', '\\', '€', '^', '5', '0'},
            {'?', '!', '-', ':', '.', '_'}
    };

    char[][] currentChars;

    CircleCharacters[] circleChars = new CircleCharacters[6];
    CircleCharacters singleChars;

    CharactersetDisplay charDisplay;

    int firstPie;

    public ContentContainer(Context context) {
        super(context);
        this.context = context;
        getDisplaySize();

        currentChars = getCharset(CHARSET_CHARSMALL);
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
        for (int i = 0; i < currentChars.length; i++) {
            CartesianCoordinates center = coordinates.getPositionRawCartesian((int) ((SCREENRADIUS - textPadding) * 0.6), i * 60);
            circleChars[i] = new CircleCharacters(context, currentChars[i], center, 25, currentChars[i][i]);
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

        charDisplay = new CharactersetDisplay(context, SCREENRADIUS - textPadding, textPadding);

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


    int id1 = -1;
    int id2 = -1;
    int angle = 0;
    private void handleMultitouchEvent (MotionEvent event) {

        MotionEvent.PointerCoords p1 = new MotionEvent.PointerCoords();
        MotionEvent.PointerCoords p2 = new MotionEvent.PointerCoords();

        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            if (id1 == -1) {
                id1 = event.getPointerId(event.getActionIndex());
            } else if (id2 == -1) {
                id2 = event.getPointerId(event.getActionIndex());
            }
            if (event.getPointerCount() == 2 && id2 != -1) {
                event.getPointerCoords(id1, p1);
                event.getPointerCoords(id2, p2);
                angle = Coordinates.getAngle(p1, p2);
                return;
            }
        }
        if (event.getPointerCount() == 2) {
            event.getPointerCoords(id1, p1);
            event.getPointerCoords(id2, p2);

            int rotate = Coordinates.getAngle(p1, p2);

            circleText.rotate(rotate-angle);
            angle = rotate;

        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            id1 = event.getPointerId(event.getActionIndex());
        }

        if (event.getPointerCount() == 2) {
            handleMultitouchEvent(event);
            return true;
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                firstPie = getPiePart(event);
                if (firstPie != -1) {
                    pieSingle = new Pie(context, textPadding, firstPie);
                    singleChars = new CircleCharacters(context, currentChars[firstPie],
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
                        char newchar = currentChars[firstPie][upPie];
                        if (newchar == '_') newchar = ' ';
                        text.add(newchar);
                        refreshText();
                    }
                }
                break;
        }

        return true;
    }

    public String getResultString () {
        Character[] textAr = text.toArray(new Character[text.size()]);
        String text = (new String(ArrayUtils.toPrimitive(textAr)));
        Log.i("text", text);
        return text;
    }

    public void switchCharset(int kick) {

        switch (kick) {
            case Gyroscope.KICK_FRONT:
                    if (currentCharset == CHARSET_CHARBIG) {
                        currentCharset = CHARSET_CHARSMALL;
                    } else if (currentCharset == CHARSET_NUMBERS) {

                    } else {
                        currentCharset = CHARSET_CHARBIG;
                    }

                break;
            case Gyroscope.KICK_REAR:
                if (currentCharset == CHARSET_NUMBERS) {
                    currentCharset = CHARSET_CHARSMALL;
                } else if (currentCharset == CHARSET_CHARBIG) {

                } else {
                    currentCharset = CHARSET_NUMBERS;
                }
                break;
        }
        charDisplay.setCurrentCharset(currentCharset);
        updateCharset();

    }

    private void updateCharset() {

        currentChars = getCharset(currentCharset);

        // character labels
        for (int i = 0; i < currentChars.length; i++) {
            removeView(circleChars[i]);
            CartesianCoordinates center = coordinates.getPositionRawCartesian((int) ((SCREENRADIUS - textPadding) * 0.6), i * 60);
            circleChars[i] = new CircleCharacters(context, currentChars[i], center, 25, currentChars[i][i]);
            addView(circleChars[i]);
        }

    }

    private char[][] getCharset(int charset) {
        switch (charset) {
            case CHARSET_CHARSMALL:
                return smallChars;
            case CHARSET_CHARBIG:
                return bigChars;
            case CHARSET_NUMBERS:
                return numbersChars;

        }

        return null;
    }
}
