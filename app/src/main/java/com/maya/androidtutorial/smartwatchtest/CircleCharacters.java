package com.maya.androidtutorial.smartwatchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

/**
 * Created by patrick on 10.12.15.
 *
 * erzeugt die im kreis angeordneten buchstaben der in sechstel unterteilten oberfläche
 */
public class CircleCharacters extends View {

    char[] chars;
    int radius;
    CartesianCoordinates center;
    Paint paint;
    Coordinates coords;

    char mark;

    public CircleCharacters(Context context, char[] characters, CartesianCoordinates center, int radius) {
        super(context);


        this.center = center;
        this.radius = radius;
        chars = characters;

        coords = new Coordinates(center.x, center.y);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);

    }

    public CircleCharacters(Context context, char[] characters, CartesianCoordinates center, int radius, char mark) {
        super(context);
        this.mark = mark;

        this.center = center;
        this.radius = radius;
        chars = characters;

        coords = new Coordinates(center.x, center.y);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
    }


    @Override
    public void onDraw(Canvas canvas) {

        for (int i = 0; i < chars.length; i++) {

            if (chars[i] == mark) {
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            } else {
                paint.setTypeface(null);
            }

            CartesianCoordinates cc = coords.getPositionRawCartesian(radius, i * 60);
            canvas.drawText(String.valueOf(chars[i]), cc.x - 5, cc.y + 5, paint);
        }
    }
}
