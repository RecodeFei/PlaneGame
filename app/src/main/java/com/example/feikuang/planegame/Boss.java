/**
 *
 */
package com.example.feikuang.planegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Boss
 * @author feikuang
 *
 */
public class Boss {
    //BossµÄÑªÁ¿
    public int hp = 50;
    //BossµÄÍŒÆ¬×ÊÔŽ
    private Bitmap bmpBoss;
    //Boss×ø±ê
    public int x, y;
    //BossÃ¿Ö¡µÄ¿ížß
    public int frameW, frameH;
    //Bossµ±Ç°Ö¡ÏÂ±ê
    private int frameIndex;
    //BossÔË¶¯µÄËÙ¶È
    private int speed = 5;
    //BossµÄÔË¶¯¹ìŒ£
    //Ò»¶šÊ±Œä»áÏò×ÅÆÁÄ»ÏÂ·œÔË¶¯£¬²¢ÇÒ·¢ÉäŽó·¶Î§×Óµ¯£¬£šÊÇ·ñ¿ñÌ¬£©
    //Õý³£×ŽÌ¬ÏÂ £¬×Óµ¯Ž¹Ö±³¯ÏÂÔË¶¯
    private boolean isCrazy;
    //œøÈë·è¿ñ×ŽÌ¬µÄ×ŽÌ¬Ê±ŒäŒäžô
    private int crazyTime = 200;
    //ŒÆÊýÆ÷
    private int count;

    //BossµÄ¹¹Ôìº¯Êý
    public Boss(Bitmap bmpBoss) {
        this.bmpBoss = bmpBoss;
        frameW = bmpBoss.getWidth() / 10;
        frameH = bmpBoss.getHeight();
        //BossµÄX×ø±êŸÓÖÐ
        x = MySurfaceView.screenW / 2 - frameW / 2;
        y = 0;
    }

    //BossµÄ»æÖÆ
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.clipRect(x, y, x + frameW, y + frameH);
        canvas.drawBitmap(bmpBoss, x - frameIndex * frameW, y, paint);
        canvas.restore();
    }

    //BossµÄÂßŒ­
    public void logic() {
        //²»¶ÏÑ­»·²¥·ÅÖ¡ÐÎ³É¶¯»­
        frameIndex++;
        if (frameIndex >= 10) {
            frameIndex = 0;
        }
        //Ã»ÓÐ·è¿ñµÄ×ŽÌ¬
        if (isCrazy == false) {
            x += speed;
            if (x + frameW >= MySurfaceView.screenW) {
                speed = -speed;
            } else if (x <= 0) {
                speed = -speed;
            }
            count++;
            if (count % crazyTime == 0) {
                isCrazy = true;
                speed = 24;
            }
            //·è¿ñµÄ×ŽÌ¬
        } else {
            speed -= 1;
            //µ±Boss·µ»ØÊ±ŽŽœšŽóÁ¿×Óµ¯
            if (speed == 0) {
                //ÌíŒÓ8·œÏò×Óµ¯
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_LEFT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_RIGHT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP_LEFT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_UP_RIGHT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_LEFT));
                MySurfaceView.vcBulletBoss.add(new Bullet(MySurfaceView.bmpBossBullet, x+40, y+10, Bullet.BULLET_BOSS, Bullet.DIR_DOWN_RIGHT));
            }
            y += speed;
            if (y <= 0) {
                //»ÖžŽÕý³£×ŽÌ¬
                isCrazy = false;
                speed = 5;
            }
        }
    }

    //ÅÐ¶ÏÅö×²(Boss±»Ö÷œÇ×Óµ¯»÷ÖÐ)
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
        return true;
    }

    //ÉèÖÃBossÑªÁ¿
    public void setHp(int hp) {
        this.hp = hp;
    }
}