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
 *
 * Zeigt die verfügbaren charsets oben am display an
 */
public class CharactersetDisplay extends View {

    Coordinates coord;
    int radius;
    int width;

    private int currentCharset;

    Path pathLeft, pathRight, pathTextLeft, pathTextRight;

    public CharactersetDisplay(Context context, int radius, int width) {
        super(context);
        this.radius = radius;
        this.width = width;

        coord = new Coordinates(ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS);

        createPathLeft();
        createPathRight();

        currentCharset = ContentContainer.CHARSET_CHARSMALL;

        pathTextLeft = new Path();
        CartesianCoordinates point = coord.getPositionRawCartesian(radius, 110);
        pathTextLeft.moveTo(point.x, point.y);
        pathTextLeft.addArc(width, width, ContentContainer.SCREENRADIUS * 2 - width, ContentContainer.SCREENRADIUS * 2 - width, -110, 20);


        pathTextRight = new Path();
        point = coord.getPositionRawCartesian(radius, 70);
        pathTextRight.moveTo(point.x, point.y);
        pathTextRight.addArc(width, width, ContentContainer.SCREENRADIUS * 2 - width, ContentContainer.SCREENRADIUS * 2 - width, -90, 20);

    }

    /**
     * setzt den akutellen zeichensatz
     * @param charset
     */
    public void setCurrentCharset (int charset) {
        this.currentCharset = charset;
        invalidate();
    }



    private void createPathLeft () {
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
        pathLeft = path;
    }

    private void createPathRight () {

        Path path = new Path();
        CartesianCoordinates point;

        point = coord.getPositionRawCartesian(radius, 90);
        path.moveTo(point.x, point.y);

        path.addArc(width, width, ContentContainer.SCREENRADIUS * 2 - width, ContentContainer.SCREENRADIUS * 2 - width, -90, 20);


        point = coord.getPositionRawCartesian(radius + width, 70);
        path.lineTo(point.x, point.y);

        path.addArc(0, 0, ContentContainer.SCREENRADIUS * 2, ContentContainer.SCREENRADIUS * 2, -70, -20);

        point = coord.getPositionRawCartesian(radius, 90);
        path.lineTo(point.x, point.y);
        pathRight = path;
    }


    @Override
    public void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(15);

        canvas.drawPath(pathLeft, paint);
        canvas.drawPath(pathRight, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(pathLeft, paint);
        canvas.drawPath(pathRight, paint);



        String textLeft = "";
        String textRight = "";
        switch (currentCharset) {

            case ContentContainer.CHARSET_CHARSMALL:
                textLeft = "ABC";
                textRight = "12#";
                break;
            case ContentContainer.CHARSET_CHARBIG:
                textLeft = "abc";
                textRight = "";
                break;
            case ContentContainer.CHARSET_NUMBERS:
                textLeft = "";
                textRight = "abc";
                break;
        }

        canvas.drawTextOnPath(textLeft, pathTextLeft, -0, -5, paint);
        canvas.drawTextOnPath(textRight, pathTextRight, -0, -5, paint);



    }
}
