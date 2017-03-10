package com.example.feikuang.planegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author feikuang
 *
 */
public class Enemy {
    //µÐ»úµÄÖÖÀà±êÊ¶
    public int type;
    //²ÔÓ¬
    public static final int TYPE_FLY = 1;
    //ÑŒ×Ó(ŽÓ×óÍùÓÒÔË¶¯)
    public static final int TYPE_DUCKL = 2;
    //ÑŒ×Ó(ŽÓÓÒÍù×óÔË¶¯)
    public static final int TYPE_DUCKR = 3;
    //µÐ»úÍŒÆ¬×ÊÔŽ
    public Bitmap bmpEnemy;
    //µÐ»ú×ø±ê
    public int x, y;
    //µÐ»úÃ¿Ö¡µÄ¿ížß
    public int frameW, frameH;
    //µÐ»úµ±Ç°Ö¡ÏÂ±ê
    private int frameIndex;
    //µÐ»úµÄÒÆ¶¯ËÙ¶È
    private int speed;;
    //ÅÐ¶ÏµÐ»úÊÇ·ñÒÑŸ­³öÆÁ
    public boolean isDead;

    //µÐ»úµÄ¹¹Ôìº¯Êý
    public Enemy(Bitmap bmpEnemy, int enemyType, int x, int y) {
        this.bmpEnemy = bmpEnemy;
        frameW = bmpEnemy.getWidth() / 10;
        frameH = bmpEnemy.getHeight();
        this.type = enemyType;
        this.x = x;
        this.y = y;
        //²»Í¬ÖÖÀàµÄµÐ»úÑªÁ¿²»Í¬
        switch (type) {
            //²ÔÓ¬
            case TYPE_FLY:
                speed = 25;
                break;
            //ÑŒ×Ó
            case TYPE_DUCKL:
                speed = 3;
                break;
            case TYPE_DUCKR:
                speed = 3;
                break;
        }
    }

    //µÐ»ú»æÍŒº¯Êý
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.clipRect(x, y, x + frameW, y + frameH);
        canvas.drawBitmap(bmpEnemy, x - frameIndex * frameW, y, paint);
        canvas.restore();
    }

    //µÐ»úÂßŒ­AI
    public void logic() {
        //²»¶ÏÑ­»·²¥·ÅÖ¡ÐÎ³É¶¯»­
        frameIndex++;
        if (frameIndex >= 10) {
            frameIndex = 0;
        }
        //²»Í¬ÖÖÀàµÄµÐ»úÓµÓÐ²»Í¬µÄAIÂßŒ­
        switch (type) {
            case TYPE_FLY:
                if (isDead == false) {
                    //ŒõËÙ³öÏÖ£¬ŒÓËÙ·µ»Ø
                    speed -= 1;
                    y += speed;
                    if (y <= -200) {
                        isDead = true;
                    }
                }
                break;
            case TYPE_DUCKL:
                if (isDead == false) {
                    //Ð±ÓÒÏÂœÇÔË¶¯
                    x += speed / 2;
                    y += speed;
                    if (x > MySurfaceView.screenW) {
                        isDead = true;
                    }
                }
                break;
            case TYPE_DUCKR:
                if (isDead == false) {
                    //Ð±×óÏÂœÇÔË¶¯
                    x -= speed / 2;
                    y += speed;
                    if (x < -50) {
                        isDead = true;
                    }
                }
                break;
        }
    }

    //ÅÐ¶ÏÅö×²(µÐ»úÓëÖ÷œÇ×Óµ¯Åö×²)
    public boolean isCollsionWith(Bullet bullet) {
        int x2 = bullet.bulletX;
        int y2 = bullet.bulletY;
        int w2 = bullet.bmpBullet.getWidth();
        int h2 = bullet.bmpBullet.getHeight();
        if (x >= x2 && x >= x2 + w2) {
            return false;
        } else if (x <= x2 && x + frameW <= x2) {
            return false;
        } else if (y >= y2 && y >= y2 + h2) {
            return false;
        } else if (y <= y2 && y + frameH <= y2) {
            return false;
        }
        //·¢ÉúÅö×²£¬ÈÃÆäËÀÍö
        isDead = true;
        return true;
    }
}