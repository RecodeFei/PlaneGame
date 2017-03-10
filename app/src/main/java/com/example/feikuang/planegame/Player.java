package com.example.feikuang.planegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * @author feikuang
 *
 */
public class Player {
    //Ö÷œÇµÄÑªÁ¿ÓëÑªÁ¿Î»ÍŒ
    //Ä¬ÈÏ3Ñª
    private int playerHp = 3;
    private Bitmap bmpPlayerHp;
    //Ö÷œÇµÄ×ø±êÒÔŒ°Î»ÍŒ
    public int x, y;
    private Bitmap bmpPlayer;
    //Ö÷œÇÒÆ¶¯ËÙ¶È
    private int speed = 5;
    //Ö÷œÇÒÆ¶¯±êÊ¶£š»ùŽ¡ÕÂœÚÒÑœ²œâ£¬Äã¶®µÃ£©
    private boolean isUp, isDown, isLeft, isRight;
    //Åö×²ºóŽŠÓÚÎÞµÐÊ±Œä
    //ŒÆÊ±Æ÷
    private int noCollisionCount = 0;
    //ÒòÎªÎÞµÐÊ±Œä
    private int noCollisionTime = 60;
    //ÊÇ·ñÅö×²µÄ±êÊ¶Î»
    private boolean isCollision;

    //Ö÷œÇµÄ¹¹Ôìº¯Êý
    public Player(Bitmap bmpPlayer, Bitmap bmpPlayerHp) {
        this.bmpPlayer = bmpPlayer;
        this.bmpPlayerHp = bmpPlayerHp;
        x = MySurfaceView.screenW / 2 - bmpPlayer.getWidth() / 2;
        y = MySurfaceView.screenH - bmpPlayer.getHeight();
    }

    //Ö÷œÇµÄ»æÍŒº¯Êý
    public void draw(Canvas canvas, Paint paint) {
        //»æÖÆÖ÷œÇ
        //µ±ŽŠÓÚÎÞµÐÊ±ŒäÊ±£¬ÈÃÖ÷œÇÉÁËž
        if (isCollision) {
            //Ã¿2ŽÎÓÎÏ·Ñ­»·£¬»æÖÆÒ»ŽÎÖ÷œÇ
            if (noCollisionCount % 2 == 0) {
                canvas.drawBitmap(bmpPlayer, x, y, paint);
            }
        } else {
            canvas.drawBitmap(bmpPlayer, x, y, paint);
        }
        //»æÖÆÖ÷œÇÑªÁ¿
        for (int i = 0; i < playerHp; i++) {
            canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(), MySurfaceView.screenH - bmpPlayerHp.getHeight(), paint);
        }
    }

    //ÊµÌå°ŽŒü
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
            isRight = true;
        }
    }

    //ÊµÌå°ŽŒüÌ§Æð
    public void onKeyUp(int keyCode, KeyEvent event) {
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

    public boolean onTouchEvent(MotionEvent event){
               x = (int) event.getX();
               y = (int) event.getY();
              return true;
      }

    //Ö÷œÇµÄÂßŒ­
    public void logic() {
        //ŽŠÀíÖ÷œÇÒÆ¶¯
        if (isLeft) {
            x -= speed;
        }
        if (isRight) {
            x += speed;
        }
        if (isUp) {
            y -= speed;
        }
        if (isDown) {
            y += speed;
        }
        //ÅÐ¶ÏÆÁÄ»X±ßœç
        if (x + bmpPlayer.getWidth() >= MySurfaceView.screenW) {
            x = MySurfaceView.screenW - bmpPlayer.getWidth();
        } else if (x <= 0) {
            x = 0;
        }
        //ÅÐ¶ÏÆÁÄ»Y±ßœç
        if (y + bmpPlayer.getHeight() >= MySurfaceView.screenH) {
            y = MySurfaceView.screenH - bmpPlayer.getHeight();
        } else if (y <= 0) {
            y = 0;
        }
        //ŽŠÀíÎÞµÐ×ŽÌ¬
        if (isCollision) {
            //ŒÆÊ±Æ÷¿ªÊŒŒÆÊ±
            noCollisionCount++;
            if (noCollisionCount >= noCollisionTime) {
                //ÎÞµÐÊ±Œä¹ýºó£¬œÓŽ¥ÎÞµÐ×ŽÌ¬Œ°³õÊŒ»¯ŒÆÊýÆ÷
                isCollision = false;
                noCollisionCount = 0;
            }
        }
    }

    //ÉèÖÃÖ÷œÇÑªÁ¿
    public void setPlayerHp(int hp) {
        this.playerHp = hp;
    }

    //»ñÈ¡Ö÷œÇÑªÁ¿
    public int getPlayerHp() {
        return playerHp;
    }

    //ÅÐ¶ÏÅö×²(Ö÷œÇÓëµÐ»ú)
    public boolean isCollsionWith(Enemy en) {
        //ÊÇ·ñŽŠÓÚÎÞµÐÊ±Œä
        if (isCollision == false) {
            int x2 = en.x;
            int y2 = en.y;
            int w2 = en.frameW;
            int h2 = en.frameH;
            if (x >= x2 && x >= x2 + w2) {
                return false;
            } else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
                return false;
            } else if (y >= y2 && y >= y2 + h2) {
                return false;
            } else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
                return false;
            }
            //Åö×²ŒŽœøÈëÎÞµÐ×ŽÌ¬
            isCollision = true;
            return true;
            //ŽŠÓÚÎÞµÐ×ŽÌ¬£¬ÎÞÊÓÅö×²
        } else {
            return false;
        }
    }
    //ÅÐ¶ÏÅö×²(Ö÷œÇÓëµÐ»ú×Óµ¯)
    public boolean isCollsionWith(Bullet bullet) {
        //ÊÇ·ñŽŠÓÚÎÞµÐÊ±Œä
        if (isCollision == false) {
            int x2 = bullet.bulletX;
            int y2 = bullet.bulletY;
            int w2 = bullet.bmpBullet.getWidth();
            int h2 = bullet.bmpBullet.getHeight();
            if (x >= x2 && x >= x2 + w2) {
                return false;
            } else if (x <= x2 && x + bmpPlayer.getWidth() <= x2) {
                return false;
            } else if (y >= y2 && y >= y2 + h2) {
                return false;
            } else if (y <= y2 && y + bmpPlayer.getHeight() <= y2) {
                return false;
            }
            //Åö×²ŒŽœøÈëÎÞµÐ×ŽÌ¬
            isCollision = true;
            return true;
            //ŽŠÓÚÎÞµÐ×ŽÌ¬£¬ÎÞÊÓÅö×²
        } else {
            return false;
        }
    }
}