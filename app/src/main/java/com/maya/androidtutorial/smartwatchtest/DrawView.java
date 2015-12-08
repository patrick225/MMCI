package com.maya.androidtutorial.smartwatchtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by privat on 05.12.2015.
 */
public class DrawView extends View {

    private int screenSize;
    private Path[] homeScreen;
    private Paint paintCircleSegment;
    private Paint paintDelete;
    private Paint paintTextOutput;
    private Path pathTextoutput;
    private Path pathTextoutputIn;
    private Bitmap bmpDelete;
    private int textOutputPadding;

    public DrawView(Context context){
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        checkScreenSize();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(screenSize == 0){
            checkScreenSize();
        }
        textOutputPadding = screenSize/6;
        initCircleSegmentPaint();
        initTextOutput();

        canvas.drawPath(pathTextoutput, paintTextOutput);

        paintTextOutput.setColor(Color.BLACK);
        canvas.drawTextOnPath("Hallo Welt test test", pathTextoutputIn, 0, -7, paintTextOutput);

        for (int i = 0; i<6; i++){
            // hintergrundfarbe fuer die einzelnen buchstaben segmente
            switch(i){
                case 0:{paintCircleSegment.setColor(Color.parseColor(String.valueOf(Colors.SIGFRIED)));}break;
                case 1:{paintCircleSegment.setColor(Color.parseColor(String.valueOf(Colors.EMIL)));}break;
                case 2:{paintCircleSegment.setColor(Color.parseColor(String.valueOf(Colors.SPACE)));}break;
                case 3:{paintCircleSegment.setColor(Color.parseColor(String.valueOf(Colors.NORBERT)));}break;
                case 4:{paintCircleSegment.setColor(Color.parseColor(String.valueOf(Colors.INES)));}break;
                case 5:{paintCircleSegment.setColor(Color.parseColor(String.valueOf(Colors.ROBERT)));}break;
                default: {}
            }
            canvas.drawPath(homeScreen[i], paintCircleSegment);
            paintCircleSegment.setColor(Color.BLACK);
            //canvas.drawText("E", 200, 200, paintCircleSegment);
            //canvas.drawTextOnPath("E", homeScreen[i], 10, 10, paintCircleSegment);
        }


        initDeletePaint();
        canvas.drawCircle(screenSize, screenSize, textOutputPadding*3/2, paintDelete);
        //paintDelete.setAlpha(250);
        //canvas.drawBitmap(bmpDelete, screenSize, screenSize, paintDelete);
    }

    private void initCircleSegmentPaint() {
        homeScreen = new Path[6];
        paintCircleSegment = new Paint();
        paintCircleSegment.setStyle(Paint.Style.FILL);
        paintCircleSegment.setTextSize(100);
        paintCircleSegment.setTextAlign(Paint.Align.CENTER);

        for(int i = 0; i<6; i++){
            Path path = new Path();
            path.moveTo(screenSize, screenSize);
            path.lineTo(screenSize, 0+textOutputPadding);
            path.addArc(textOutputPadding, textOutputPadding, screenSize * 2-textOutputPadding, screenSize * 2-textOutputPadding, -90 + i * 60, 60);
            path.lineTo(screenSize, screenSize);
            path.close();
            homeScreen[i] = path;
        }
    }

    private void initDeletePaint(){
        bmpDelete = BitmapFactory.decodeResource(getResources(), R.drawable.delete);

        paintDelete = new Paint();
        paintDelete.setColor(Color.WHITE);
        paintDelete.setAntiAlias(true);
        paintDelete.setStyle(Paint.Style.FILL);
        paintDelete.setAlpha(190);
    }

    private void initTextOutput(){
        paintTextOutput = new Paint();
        paintTextOutput.setColor(Color.WHITE);
        paintTextOutput.setAntiAlias(true);
        paintTextOutput.setStyle(Paint.Style.FILL);
        paintTextOutput.setTextSize(20);
        paintTextOutput.setTextAlign(Paint.Align.RIGHT);

        pathTextoutput = new Path();
        pathTextoutput.addArc(0, 0, screenSize * 2, screenSize * 2, 90, 360);
        pathTextoutputIn = new Path();
        pathTextoutputIn.addArc(textOutputPadding, textOutputPadding, screenSize * 2-textOutputPadding, screenSize * 2-textOutputPadding, -90, 360);
    }


    private void checkScreenSize() {
        screenSize = this.getWidth();
        screenSize = screenSize / 2;
    }
}