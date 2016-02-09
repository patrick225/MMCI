package com.maya.androidtutorial.smartwatchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by patrick on 10.12.15.
 *
 * zeichnet den löschenbutton auf der oberfläche
 */
public class DeleteButton extends View {

    Paint paint;
    Path path;

    Paint deletepaintX;

    Coordinates coordinates;

    int radius;

    String text = "";

    Context context;

    public DeleteButton(Context context, int radius) {
        super(context);
        this.context = context;

        coordinates = new Coordinates(ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS);

        this.radius = radius;


        path = new Path();
        CartesianCoordinates temp = coordinates.getPositionRawCartesian((int) (radius * 0.9), 180);
        path.moveTo(temp.x, temp.y);
        temp = coordinates.getPositionRawCartesian((int) (radius * 0.7), 150);
        path.lineTo(temp.x, temp.y);
        temp = coordinates.getPositionRawCartesian((int) (radius * 0.7), 30);
        path.lineTo(temp.x, temp.y);
        temp = coordinates.getPositionRawCartesian((int) (radius * 0.7), 330);
        path.lineTo(temp.x, temp.y);
        temp = coordinates.getPositionRawCartesian((int) (radius * 0.7), 210);
        path.lineTo(temp.x, temp.y);
        temp = coordinates.getPositionRawCartesian((int) (radius * 0.9), 180);
        path.lineTo(temp.x, temp.y);
        path.close();


        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);


        deletepaintX = new Paint();
        deletepaintX.setTextSize(13);
        deletepaintX.setAntiAlias(true);
        deletepaintX.setColor(Color.WHITE);
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawPath(path, paint);
        canvas.drawText("X", ContentContainer.SCREENRADIUS - 5, ContentContainer.SCREENRADIUS + 4, deletepaintX);
    }
}
