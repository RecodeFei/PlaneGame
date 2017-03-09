package com.example.feikuang.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by feikuang on 1/6/17.
 */
public class Player {
    //主角的血量与血量位图
    private int playerHP = 3;
    private Bitmap bmpPlayerHP;
    //主角的坐标以及位图
    public int x,y;
    private Bitmap bmpPlayer;
    //主角移动速度
    private int speed = 5;
    //主角移动标示
    private boolean isUp,isDown,isLeft,isRight;
    //主角的构造函数
    public Player(Bitmap bmpPlayer,Bitmap bmpPlayerHP) {
        this.bmpPlayer = bmpPlayer;
        this.bmpPlayerHP = bmpPlayerHP;
        x = MySurfaceView.screenW/2 - bmpPlayer.getWidth()/2;
        y = MySurfaceView.screenH - bmpPlayer.getHeight();
    }
    //主角的绘制函数
    public void draw(Canvas canvas, Paint paint) {
        //绘制主角
        canvas.drawBitmap(bmpPlayer,x,y,paint);
        //绘制主角血量
        for (int i = 0; i < playerHP; i++) {
            canvas.drawBitmap(bmpPlayerHP,i*bmpPlayerHP.getWidth(),
                    MySurfaceView.screenH - bmpPlayerHP.getHeight(),paint);
        }
    }
    //实体按钮
    public void onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            isUp = true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            isDown = true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            isLeft = true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            isRight =true;
        }
    }
    //实体建提起
    public void onKeyUp(int keyCode,KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            isUp = false;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            isDown = false;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            isLeft = false;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            isRight = false;
        }
    }
    //触屏
    public boolean onTouchEvent(MotionEvent event){
        x = (int) event.getX();
        y = (int) event.getY();
        return true;
    }
    //主角的逻辑
    public void logic() {
        if (isLeft) {
            x -=speed;
        }
        if (isRight){
            x+=speed;
        }
        if (isUp){
            x-=speed;
        }
        if (isDown){
            x+=speed;
        }
        if (x + bmpPlayer.getWidth() >= MySurfaceView.screenW) {
            x = MySurfaceView.screenW - bmpPlayer.getWidth();
        }else if (x <= 0){
            x = 0;
        }
        //判断y边界
        if (y + bmpPlayer.getHeight() >= MySurfaceView.screenH) {
            y = MySurfaceView.screenH - bmpPlayer.getHeight();
        }else if (y <= 0)
            y =0;
    }
    //设置主角血量
    public void setPlayerHP(int hp) {
        this.playerHP = hp;
    }
    //获取主角血量
    public int getPlayerHP(){
        return playerHP;
    }
}
