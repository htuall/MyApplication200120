package com.example.myapplication200120;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    MyThread mythread;
    public MySurface(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mythread=new MyThread(getHolder());
        mythread.setRunning(true);
        mythread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        mythread.setRunning(false);
        while(retry){
            try {
                mythread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            retry=false;
        }
    }
}
