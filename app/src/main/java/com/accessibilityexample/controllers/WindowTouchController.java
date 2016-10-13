package com.accessibilityexample.controllers;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by sotsys-014 on 5/10/16.
 */

public class WindowTouchController implements View.OnTouchListener {
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;
    private float beforeX,beforeY;
    private WindowManager windowManager;
    private View mainContainer;
    private WindowManager.LayoutParams params;

    public WindowTouchController(WindowManager.LayoutParams params, View mainContainer, WindowManager windowManager) {
        this.params = params;
        this.mainContainer = mainContainer;
        this.windowManager = windowManager;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = params.x;
                initialY = params.y;
                beforeX = event.getRawX();
                beforeY = event.getRawY();
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                Log.d("log","beforeX:"+beforeX+",event.getRawX():"+event.getRawX());
                Log.d("log","beforeY:"+beforeY+",event.getRawX():"+event.getRawY());
                if(Math.abs(beforeX-event.getRawX())<50 && Math.abs(beforeY-event.getRawY())<50){
                    mainContainer.performClick();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                params.y = initialY + (int) (event.getRawY() - initialTouchY);
                windowManager.updateViewLayout(mainContainer, params);
                return true;
        }
        return false;
    }
}
