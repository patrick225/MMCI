package com.maya.androidtutorial.smartwatchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

/**
 * Created by patrick on 08.12.15.
 */
public class Pie extends View {

    public final static int TYPE_ALL = 1;
    public final static int TYPE_ZOOM = 2;


    Paint paint;
    Path path[] = new Path[6];


    public Pie(Context context, int textPadding, int type) {
        super(context);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);

        for(int i = 0; i < path.length; i++){
            path[i] = new Path();
            path[i].moveTo(ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS);
            path[i].lineTo(ContentContainer.SCREENRADIUS, 0 + textPadding);
            path[i].addArc(textPadding, textPadding, ContentContainer.SCREENRADIUS * 2 - textPadding,
                    ContentContainer.SCREENRADIUS * 2 - textPadding, -90 + i * 60, 60);
            path[i].lineTo(ContentContainer.SCREENRADIUS, ContentContainer.SCREENRADIUS);
            path[i].close();
        }






    }


    @Override
    protected void onDraw(Canvas canvas) {

        Colors colors[] = Colors.values();
        for (int i = 0; i < path.length; i++) {
            paint.setColor(Color.parseColor(String.valueOf(colors[i])));
            canvas.drawPath(path[i], paint);
        }

    }
}
