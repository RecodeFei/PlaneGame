package com.example.feikuang.planegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author feikuang
 *
 */
public class Bullet {
    //×Óµ¯ÍŒÆ¬×ÊÔŽ
    public Bitmap bmpBullet;
    //×Óµ¯µÄ×ø±ê
    public int bulletX, bulletY;
    //×Óµ¯µÄËÙ¶È
    public int speed;
    //×Óµ¯µÄÖÖÀàÒÔŒ°³£Á¿
    public int bulletType;
    //Ö÷œÇµÄ
    public static final int BULLET_PLAYER = -1;
    //ÑŒ×ÓµÄ
    public static final int BULLET_DUCK = 1;
    //²ÔÓ¬µÄ
    public static final int BULLET_FLY = 2;
    //BossµÄ
    public static final int BULLET_BOSS = 3;
    //×Óµ¯ÊÇ·ñ³¬ÆÁ£¬ ÓÅ»¯ŽŠÀí
    public boolean isDead;

    //Boss·è¿ñ×ŽÌ¬ÏÂ×Óµ¯Ïà¹Ø³ÉÔ±±äÁ¿
    private int dir;//µ±Ç°Boss×Óµ¯·œÏò
    //8·œÏò³£Á¿
    public static final int DIR_UP = -1;
    public static final int DIR_DOWN = 2;
    public static final int DIR_LEFT = 3;
    public static final int DIR_RIGHT = 4;
    public static final int DIR_UP_LEFT = 5;
    public static final int DIR_UP_RIGHT = 6;
    public static final int DIR_DOWN_LEFT = 7;
    public static final int DIR_DOWN_RIGHT = 8;

    //×Óµ¯µ±Ç°·œÏò
    public Bullet(Bitmap bmpBullet, int bulletX, int bulletY, int bulletType) {
        this.bmpBullet = bmpBullet;
        this.bulletX = bulletX;
        this.bulletY = bulletY;
        this.bulletType = bulletType;
        //²»Í¬µÄ×Óµ¯ÀàÐÍËÙ¶È²»Ò»
        switch (bulletType) {
            case BULLET_PLAYER:
                speed = 4;
                break;
            case BULLET_DUCK:
                speed = 3;
                break;
            case BULLET_FLY:
                speed = 4;
                break;
            case BULLET_BOSS:
                speed = 5;
                break;
        }
    }

    /**
     * ×šÓÃÓÚŽŠÀíBoss·è¿ñ×ŽÌ¬ÏÂŽŽœšµÄ×Óµ¯
     * @param bmpBullet
     * @param bulletX
     * @param bulletY
     * @param bulletType
     * @param Dir
     */
    public Bullet(Bitmap bmpBullet, int bulletX, int bulletY, int bulletType, int dir) {
        this.bmpBullet = bmpBullet;
        this.bulletX = bulletX;
        this.bulletY = bulletY;
        this.bulletType = bulletType;
        speed = 5;
        this.dir = dir;
    }

    //×Óµ¯µÄ»æÖÆ
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bmpBullet, bulletX, bulletY, paint);
    }

    //×Óµ¯µÄÂßŒ­
    public void logic() {
        //²»Í¬µÄ×Óµ¯ÀàÐÍÂßŒ­²»Ò»
        //Ö÷œÇµÄ×Óµ¯Ž¹Ö±ÏòÉÏÔË¶¯
        switch (bulletType) {
            case BULLET_PLAYER:
                bulletY -= speed;
                if (bulletY < -50) {
                    isDead = true;
                }
                break;
            //ÑŒ×ÓºÍ²ÔÓ¬µÄ×Óµ¯¶ŒÊÇŽ¹Ö±ÏÂÂäÔË¶¯
            case BULLET_DUCK:
            case BULLET_FLY:
                bulletY += speed;
                if (bulletY > MySurfaceView.screenH) {
                    isDead = true;
                }
                break;
            //Boss·è¿ñ×ŽÌ¬ÏÂµÄ8·œÏò×Óµ¯ÂßŒ­
            case BULLET_BOSS:
                //Boss·è¿ñ×ŽÌ¬ÏÂµÄ×Óµ¯ÂßŒ­ŽýÊµÏÖ
                switch (dir) {
                    //·œÏòÉÏµÄ×Óµ¯
                    case DIR_UP:
                        bulletY -= speed;
                        break;
                    //·œÏòÏÂµÄ×Óµ¯
                    case DIR_DOWN:
                        bulletY += speed;
                        break;
                    //·œÏò×óµÄ×Óµ¯
                    case DIR_LEFT:
                        bulletX -= speed;
                        break;
                    //·œÏòÓÒµÄ×Óµ¯
                    case DIR_RIGHT:
                        bulletX += speed;
                        break;
                    //·œÏò×óÉÏµÄ×Óµ¯
                    case DIR_UP_LEFT:
                        bulletY -= speed;
                        bulletX -= speed;
                        break;
                    //·œÏòÓÒÉÏµÄ×Óµ¯
                    case DIR_UP_RIGHT:
                        bulletX += speed;
                        bulletY -= speed;
                        break;
                    //·œÏò×óÏÂµÄ×Óµ¯
                    case DIR_DOWN_LEFT:
                        bulletX -= speed;
                        bulletY += speed;
                        break;
                    //·œÏòÓÒÏÂµÄ×Óµ¯
                    case DIR_DOWN_RIGHT:
                        bulletY += speed;
                        bulletX += speed;
                        break;
                }
                //±ßœçŽŠÀí
                if (bulletY > MySurfaceView.screenH || bulletY <= -40 || bulletX > MySurfaceView.screenW || bulletX <= -40) {
                    isDead = true;
                }
                break;
        }
    }
}