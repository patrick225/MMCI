package com.maya.androidtutorial.smartwatchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by patrick on 09.12.15.
 */
public class CircleText extends View {


    Path path;
    Paint paint;

    String text = "";

    public CircleText (Context context) {

        super(context);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.RIGHT);

        path = new Path();
        path.addCircle(ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS, Path.Direction.CCW);

    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawTextOnPath(text, path, -250, -7, paint);

    }
}
