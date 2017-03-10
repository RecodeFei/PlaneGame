package com.example.feikuang.planegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author feikuang
 *
 */
public class Boom {
    //±¬ÕšÐ§¹û×ÊÔŽÍŒ
    private Bitmap bmpBoom;
    //±¬ÕšÐ§¹ûµÄÎ»ÖÃ×ø±ê
    private int boomX, boomY;
    //±¬Õš¶¯»­²¥·Åµ±Ç°µÄÖ¡ÏÂ±ê
    private int cureentFrameIndex;
    //±¬ÕšÐ§¹ûµÄ×ÜÖ¡Êý
    private int totleFrame;
    //Ã¿Ö¡µÄ¿ížß
    private int frameW, frameH;
    //ÊÇ·ñ²¥·ÅÍê±Ï£¬ÓÅ»¯ŽŠÀí
    public boolean playEnd;

    //±¬ÕšÐ§¹ûµÄ¹¹Ôìº¯Êý
    public Boom(Bitmap bmpBoom, int x, int y, int totleFrame) {
        this.bmpBoom = bmpBoom;
        this.boomX = x;
        this.boomY = y;
        this.totleFrame = totleFrame;
        frameW = bmpBoom.getWidth() / totleFrame;
        frameH = bmpBoom.getHeight();
    }

    //±¬ÕšÐ§¹û»æÖÆ
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.clipRect(boomX, boomY, boomX + frameW, boomY + frameH);
        canvas.drawBitmap(bmpBoom, boomX - cureentFrameIndex * frameW, boomY, paint);
        canvas.restore();
    }

    //±¬ÕšÐ§¹ûµÄÂßŒ­
    public void logic() {
        if (cureentFrameIndex < totleFrame) {
            cureentFrameIndex++;
        } else {
            playEnd = true;
        }
    }
}