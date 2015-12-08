package com.maya.androidtutorial.smartwatchtest;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Created by patrick on 08.12.15.
 */
public class ContentContainer extends RelativeLayout {


    public static int SCREENRADIUS;
    Context context;
    Circle circleText;
    Pie pieAll;

    public ContentContainer(Context context) {
        super(context);

        this.context = context;

        getDisplaySize();

        // white Circle
        Paint paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);
        circleText = new Circle(context, SCREENRADIUS, paintText);
        addView(circleText);


        // pie parts
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int textPadding = SCREENRADIUS / 6;
        pieAll = new Pie(context, textPadding, Pie.TYPE_ALL);
        addView(pieAll);

    }



    private void getDisplaySize() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        wm.getDefaultDisplay().getMetrics(metrics);

        SCREENRADIUS = metrics.widthPixels / 2;
    }
}
