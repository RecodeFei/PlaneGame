package com.example.feikuang.planegame;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * @author feikuang
 *
 */
public class GameMenu {
    //²Ëµ¥±³Ÿ°ÍŒ
    private Bitmap bmpMenu;
    //°ŽÅ¥ÍŒÆ¬×ÊÔŽ(°ŽÏÂºÍÎŽ°ŽÏÂÍŒ)
    private Bitmap bmpButton, bmpButtonPress;
    //°ŽÅ¥µÄ×ø±ê
    private int btnX, btnY;
    //°ŽÅ¥ÊÇ·ñ°ŽÏÂ±êÊ¶Î»
    private Boolean isPress;
    //²Ëµ¥³õÊŒ»¯
    public GameMenu(Bitmap bmpMenu, Bitmap bmpButton, Bitmap bmpButtonPress) {
        this.bmpMenu = bmpMenu;
        this.bmpButton = bmpButton;
        this.bmpButtonPress = bmpButtonPress;
        //XŸÓÖÐ£¬YœôœÓÆÁÄ»µ×²¿
        btnX = MySurfaceView.screenW / 2 - bmpButton.getWidth() / 2;
        btnY = MySurfaceView.screenH - bmpButton.getHeight();
        isPress = false;
    }
    //²Ëµ¥»æÍŒº¯Êý
    public void draw(Canvas canvas, Paint paint) {
        //»æÖÆ²Ëµ¥±³Ÿ°ÍŒ
        canvas.drawBitmap(bmpMenu, 0, 0, paint);
        //»æÖÆÎŽ°ŽÏÂ°ŽÅ¥ÍŒ
        if (isPress) {//žùŸÝÊÇ·ñ°ŽÏÂ»æÖÆ²»Í¬×ŽÌ¬µÄ°ŽÅ¥ÍŒ
            canvas.drawBitmap(bmpButtonPress, btnX, btnY, paint);
        } else {
            canvas.drawBitmap(bmpButton, btnX, btnY, paint);
        }
    }
    //²Ëµ¥Ž¥ÆÁÊÂŒþº¯Êý£¬Ö÷ÒªÓÃÓÚŽŠÀí°ŽÅ¥ÊÂŒþ
    public void onTouchEvent(MotionEvent event) {
        //»ñÈ¡ÓÃ»§µ±Ç°Ž¥ÆÁÎ»ÖÃ
        int pointX = (int) event.getX();
        int pointY = (int) event.getY();
        //µ±ÓÃ»§ÊÇ°ŽÏÂ¶¯×÷»òÒÆ¶¯¶¯×÷
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            //ÅÐ¶šÓÃ»§ÊÇ·ñµã»÷ÁË°ŽÅ¥
            if (pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
                if (pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
                    isPress = true;
                } else {
                    isPress = false;
                }
            } else {
                isPress = false;
            }
            //µ±ÓÃ»§ÊÇÌ§Æð¶¯×÷
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //Ì§ÆðÅÐ¶ÏÊÇ·ñµã»÷°ŽÅ¥£¬·ÀÖ¹ÓÃ»§ÒÆ¶¯µœ±ðŽŠ
            if (pointX > btnX && pointX < btnX + bmpButton.getWidth()) {
                if (pointY > btnY && pointY < btnY + bmpButton.getHeight()) {
                    //»¹Ô­Button×ŽÌ¬ÎªÎŽ°ŽÏÂ×ŽÌ¬
                    isPress = false;
                    //žÄ±äµ±Ç°ÓÎÏ·×ŽÌ¬Îª¿ªÊŒÓÎÏ·
                    MySurfaceView.gameState = MySurfaceView.GAMEING;
                }
            }
        }
    }
}