package com.example.myapplication200120;

import android.animation.ArgbEvaluator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Random;

public class MyThread extends Thread{
    private final int REDRAW_TIME=100;//частота обновления экрана
    private final int ANIMATION_TIME=1500;//продолжительность анимации
    private boolean flag;//флажок запущен ли поток
    private long startTime;
    private long prevRedrawTime;
    private Paint paint;
    private ArgbEvaluator argbEvaluator;
    private SurfaceHolder surfaceHolder;//указатель для получения canvas
    MyThread(SurfaceHolder h){
        flag=false;
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        argbEvaluator =new ArgbEvaluator();
        surfaceHolder=h;
    }
    public long getTime(){
        return System.nanoTime()/1000;
    }
    public void outdraw(Canvas canvas){
        long currentTime=getTime() -startTime;
        int width=canvas.getWidth();
        int height=canvas.getHeight();
        canvas.drawColor(Color.BLACK);
        int centerX=width/2;
        int centerY=height/2;
        float maxr=Math.min(width,height)/2;
        float fraction=(float)((currentTime%ANIMATION_TIME)/ANIMATION_TIME);
        Log.d("RRR",Float.toString(fraction));
        //int color=(int)argbEvaluator.evaluate(fraction,Color.RED,Color.BLACK);
        Random r=new Random();
        int color=Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        Log.d("RRR",Float.toString(color));
        canvas.drawCircle(centerX,centerY,maxr*fraction,paint);
    }
    public void setRunning(boolean running) {
        flag=running;
        prevRedrawTime=getTime();
    }

    @Override
    public void run() {
        Canvas canvas;
        startTime=getTime();
        while(flag){
            long currtime=getTime();
            long elapsedTime=currtime-prevRedrawTime;
            if (elapsedTime<REDRAW_TIME){
                continue;
            }
            canvas=surfaceHolder.lockCanvas();
            outdraw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
            prevRedrawTime=getTime();
        }
    }
}
