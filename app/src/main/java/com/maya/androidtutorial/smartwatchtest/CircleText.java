package com.maya.androidtutorial.smartwatchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

/**
 * Created by patrick on 09.12.15.
 *
 * erzeugt die am kreis gebeugte textausgabe
 */
public class CircleText extends View {


    Path path;
    Paint paint;

    float rotation;

    String text = "";

    public CircleText (Context context) {

        super(context);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.RIGHT);
        rotation = 0;

        path = new Path();
        path.addCircle(ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS, Path.Direction.CCW);

    }

    /**
     * setzt den text
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    /**
     * dreht um eingegeben winkel
     * @param angle
     */
    public void rotate (int angle) {
        rotation = rotation +  (float) (angle / 1.5);
        invalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {

        canvas.rotate(rotation, ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS);
        canvas.drawTextOnPath(text, path, -250, -7, paint);
    }
}
