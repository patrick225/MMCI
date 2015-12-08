package com.maya.androidtutorial.smartwatchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

/**
 * Created by privat on 05.12.2015.
 */
public class Circle extends View {

    private int radius;
    Paint paint;

    Path path;

    public Circle (Context context, int radius, Paint fill) {
        super(context);

        this.paint = fill;
        this.radius = radius;

        path = new Path();
        path.addCircle(ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS, radius, Path.Direction.CCW);

    }



    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPath(path, paint);
    }
}
