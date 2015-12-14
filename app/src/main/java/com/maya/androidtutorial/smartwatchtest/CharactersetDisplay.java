package com.maya.androidtutorial.smartwatchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

/**
 * Created by patrick on 14.12.15.
 */
public class CharactersetDisplay extends View {

    Coordinates coord;
    int radius;
    int width;

    public CharactersetDisplay(Context context, int radius, int width) {
        super(context);
        this.radius = radius;
        this.width = width;

        coord = new Coordinates(ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS);

    }


    @Override
    public void onDraw(Canvas canvas) {


        Path path = new Path();
        CartesianCoordinates point;

        point = coord.getPositionRawCartesian(radius, 90);
        path.moveTo(point.x, point.y);

        path.addArc(width, width, ContentContainer.SCREENRADIUS * 2 - width, ContentContainer.SCREENRADIUS * 2 - width, -90, -20);


        point = coord.getPositionRawCartesian(radius + width, 110);
        path.lineTo(point.x, point.y);

        path.addArc(0, 0, ContentContainer.SCREENRADIUS * 2, ContentContainer.SCREENRADIUS * 2, -110, +20);

        point = coord.getPositionRawCartesian(radius, 90);
        path.lineTo(point.x, point.y);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        canvas.drawPath(path, paint);

//        paint.setColor(Color.WHITE);
//        canvas.drawTextOnPath("ABC", path, 0, 0, paint);
    }
}
