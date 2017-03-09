package com.example.feikuang.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.ParcelUuid;

/**
 * Created by feikuang on 1/6/17.
 */
public class Enemy {
    //敌机的种类标示
    public int type;
    //苍蝇
    public static final int TYPE_FLY=1;
    //鸭子(从左到右移动）
    public static final int TYPE_DUCKL=2;
    //鸭子（从右到左移动）
    public static final int TYPE_DUCKR=3;
    //敌机图片资源
    public Bitmap bmpEnemy;
    //敌机左边
    public int x,y;
    //敌机每帧宽高
    private int frameW,frameeH;
    //敌机当前帧下表
    private int frameIndex;
    //敌机的移动速度
    private int speed;
    //判断敌机是否已经出屏
    public boolean isDead;
    //敌机的构造函数
    public Enemy(Bitmap bmpEnemy,int enemyType,int x, int y){
        this.bmpEnemy = bmpEnemy;
        frameW = bmpEnemy.getHeight()/10;
        frameeH = bmpEnemy.getHeight();
        this.type = enemyType;
        this.x = x;
        this.y = y;
        //不同种类的敌机速度不同
        switch (type) {
            //苍蝇
            case TYPE_FLY:
                speed = 25;
                break;
            case TYPE_DUCKL:
                speed = 3;
                break;
            case TYPE_DUCKR:
                speed = 3;
                break;

        }
    }
    //敌机绘图函数
    public void draw(Canvas canvas, Paint paint){
        canvas.save();
        canvas.clipRect(x,y,x+frameW,y+frameeH);
        canvas.drawBitmap(bmpEnemy,x -frameIndex * frameW,y,paint);
        canvas.restore();
    }
    //敌机逻辑ＡＩ
    public void logic() {
        //不断循环播放帧形成动画
        frameIndex++;
        if (frameIndex > = 10){
            frameIndex = 0;
        }
        //不同种类的敌机拥有不同的ai逻辑
        switch (type){
            case TYPE_FLY:
                if (isDead == false) {
                    //减速出现，加速返回
                    speed -= 1;
                    y += speed;
                }
        }
    }
}
