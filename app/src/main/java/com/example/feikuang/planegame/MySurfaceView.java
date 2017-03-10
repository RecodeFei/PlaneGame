package com.example.feikuang.planegame;


import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 *
 * @author feikuang
 *
 */
public class MySurfaceView extends SurfaceView implements Callback, Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private boolean flag;
    private Canvas canvas;
    public static int screenW, screenH;
    //¶šÒåÓÎÏ·×ŽÌ¬³£Á¿
    public static final int GAME_MENU = 0;//ÓÎÏ·²Ëµ¥
    public static final int GAMEING = 1;//ÓÎÏ·ÖÐ
    public static final int GAME_WIN = 2;//ÓÎÏ·Ê€Àû
    public static final int GAME_LOST = 3;//ÓÎÏ·Ê§°Ü
    public static final int GAME_PAUSE = -1;//ÓÎÏ·²Ëµ¥
    //µ±Ç°ÓÎÏ·×ŽÌ¬(Ä¬ÈÏ³õÊŒÔÚÓÎÏ·²Ëµ¥œçÃæ)
    public static int gameState = GAME_MENU;
    //ÉùÃ÷Ò»žöResourcesÊµÀý±ãÓÚŒÓÔØÍŒÆ¬
    private Resources res = this.getResources();
    //ÉùÃ÷ÓÎÏ·ÐèÒªÓÃµœµÄÍŒÆ¬×ÊÔŽ(ÍŒÆ¬ÉùÃ÷)
    private Bitmap bmpBackGround;//ÓÎÏ·±³Ÿ°
    private Bitmap bmpBoom;//±¬ÕšÐ§¹û
    private Bitmap bmpBoosBoom;//Boos±¬ÕšÐ§¹û
    private Bitmap bmpButton;//ÓÎÏ·¿ªÊŒ°ŽÅ¥
    private Bitmap bmpButtonPress;//ÓÎÏ·¿ªÊŒ°ŽÅ¥±»µã»÷
    private Bitmap bmpEnemyDuck;//¹ÖÎïÑŒ×Ó
    private Bitmap bmpEnemyFly;//¹ÖÎï²ÔÓ¬
    private Bitmap bmpEnemyBoos;//¹ÖÎïÖíÍ·Boos
    private Bitmap bmpGameWin;//ÓÎÏ·Ê€Àû±³Ÿ°
    private Bitmap bmpGameLost;//ÓÎÏ·Ê§°Ü±³Ÿ°
    private Bitmap bmpPlayer;//ÓÎÏ·Ö÷œÇ·É»ú
    private Bitmap bmpPlayerHp;//Ö÷œÇ·É»úÑªÁ¿
    private Bitmap bmpMenu;//²Ëµ¥±³Ÿ°
    public static Bitmap bmpBullet;//×Óµ¯
    public static Bitmap bmpEnemyBullet;//µÐ»ú×Óµ¯
    public static Bitmap bmpBossBullet;//Boss×Óµ¯
    //ÉùÃ÷Ò»žö²Ëµ¥¶ÔÏó
    private GameMenu gameMenu;
    //ÉùÃ÷Ò»žö¹ö¶¯ÓÎÏ·±³Ÿ°¶ÔÏó
    private GameBg backGround;
    //ÉùÃ÷Ö÷œÇ¶ÔÏó
    private Player player;
    //ÉùÃ÷Ò»žöµÐ»úÈÝÆ÷
    private Vector<Enemy> vcEnemy;
    //Ã¿ŽÎÉú³ÉµÐ»úµÄÊ±Œä(ºÁÃë)
    private int createEnemyTime = 50;
    private int count;//ŒÆÊýÆ÷
    //µÐÈËÊý×é£º1ºÍ2±íÊŸµÐ»úµÄÖÖÀà£¬-1±íÊŸBoss
    //¶þÎ¬Êý×éµÄÃ¿Ò»Î¬¶ŒÊÇÒ»×é¹ÖÎï
    private int enemyArray[][] = { { 1, 2 }, { 1, 1 }, { 1, 3, 1, 2 }, { 1, 2 }, { 2, 3 }, { 3, 1, 3 }, { 2, 2 }, { 1, 2 }, { 2, 2 }, { 1, 3, 1, 1 }, { 2, 1 },
            { 1, 3 }, { 2, 1 }, { -1 } };
    //µ±Ç°È¡³öÒ»Î¬Êý×éµÄÏÂ±ê
    private int enemyArrayIndex;
    //ÊÇ·ñ³öÏÖBoss±êÊ¶Î»
    private boolean isBoss;
    //Ëæ»ú¿â£¬ÎªŽŽœšµÄµÐ»úž³ÓèËæŒŽ×ø±ê
    private Random random;
    //µÐ»ú×Óµ¯ÈÝÆ÷
    private Vector<Bullet> vcBullet;
    //ÌíŒÓ×Óµ¯µÄŒÆÊýÆ÷
    private int countEnemyBullet;
    //Ö÷œÇ×Óµ¯ÈÝÆ÷
    private Vector<Bullet> vcBulletPlayer;
    //ÌíŒÓ×Óµ¯µÄŒÆÊýÆ÷
    private int countPlayerBullet;
    //±¬ÕšÐ§¹ûÈÝÆ÷
    private Vector<Boom> vcBoom;
    //ÉùÃ÷Boss
    private Boss boss;
    //BossµÄ×Óµ¯ÈÝÆ÷
    public static Vector<Bullet> vcBulletBoss;

    /**
     * SurfaceView³õÊŒ»¯º¯Êý
     */
    public MySurfaceView(Context context) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        //ÉèÖÃ±³Ÿ°³£ÁÁ
        this.setKeepScreenOn(true);
    }

    /**
     * SurfaceViewÊÓÍŒŽŽœš£¬ÏìÓŠŽËº¯Êý
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        initGame();
        flag = true;
        //ÊµÀýÏß³Ì
        th = new Thread(this);
        //Æô¶¯Ïß³Ì
        th.start();
    }

    /*
     * ×Ô¶šÒåµÄÓÎÏ·³õÊŒ»¯º¯Êý
     */
    private void initGame() {
        //·ÅÖÃÓÎÏ·ÇÐÈëºóÌšÖØÐÂœøÈëÓÎÏ·Ê±£¬ÓÎÏ·±»ÖØÖÃ!
        //µ±ÓÎÏ·×ŽÌ¬ŽŠÓÚ²Ëµ¥Ê±£¬²Å»áÖØÖÃÓÎÏ·
        if (gameState == GAME_MENU) {
            //ŒÓÔØÓÎÏ·×ÊÔŽ
            bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.background);
            bmpBoom = BitmapFactory.decodeResource(res, R.drawable.boom);
            bmpBoosBoom = BitmapFactory.decodeResource(res, R.drawable.boos_boom);
            bmpButton = BitmapFactory.decodeResource(res, R.drawable.button);
            bmpButtonPress = BitmapFactory.decodeResource(res, R.drawable.button_press);
            bmpEnemyDuck = BitmapFactory.decodeResource(res, R.drawable.enemy_duck);
            bmpEnemyFly = BitmapFactory.decodeResource(res, R.drawable.enemy_fly);
            bmpEnemyBoos = BitmapFactory.decodeResource(res, R.drawable.enemy_pig);
            bmpGameWin = BitmapFactory.decodeResource(res, R.drawable.gamewin);
            bmpGameLost = BitmapFactory.decodeResource(res, R.drawable.gamelost);
            bmpPlayer = BitmapFactory.decodeResource(res, R.drawable.player);
            bmpPlayerHp = BitmapFactory.decodeResource(res, R.drawable.hp);
            bmpMenu = BitmapFactory.decodeResource(res, R.drawable.menu);
            bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
            bmpEnemyBullet = BitmapFactory.decodeResource(res, R.drawable.bullet_enemy);
            bmpBossBullet = BitmapFactory.decodeResource(res, R.drawable.boosbullet);
            //±¬ÕšÐ§¹ûÈÝÆ÷ÊµÀý
            vcBoom = new Vector<Boom>();
            //µÐ»ú×Óµ¯ÈÝÆ÷ÊµÀý
            vcBullet = new Vector<Bullet>();
            //Ö÷œÇ×Óµ¯ÈÝÆ÷ÊµÀý
            vcBulletPlayer = new Vector<Bullet>();
            //²Ëµ¥ÀàÊµÀý
            gameMenu = new GameMenu(bmpMenu, bmpButton, bmpButtonPress);
            //ÊµÀýÓÎÏ·±³Ÿ°
            backGround = new GameBg(bmpBackGround);
            //ÊµÀýÖ÷œÇ
            player = new Player(bmpPlayer, bmpPlayerHp);
            //ÊµÀýµÐ»úÈÝÆ÷
            vcEnemy = new Vector<Enemy>();
            //ÊµÀýËæ»ú¿â
            random = new Random();
            //---BossÏà¹Ø
            //ÊµÀýboss¶ÔÏó
            boss = new Boss(bmpEnemyBoos);
            //ÊµÀýBoss×Óµ¯ÈÝÆ÷
            vcBulletBoss = new Vector<Bullet>();
        }
    }

    /**
     * ÓÎÏ·»æÍŒ
     */
    public void myDraw() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);
                //»æÍŒº¯ÊýžùŸÝÓÎÏ·×ŽÌ¬²»Í¬œøÐÐ²»Í¬»æÖÆ
                switch (gameState) {
                    case GAME_MENU:
                        //²Ëµ¥µÄ»æÍŒº¯Êý
                        gameMenu.draw(canvas, paint);
                        break;
                    case GAMEING:
                        //ÓÎÏ·±³Ÿ°
                        backGround.draw(canvas, paint);
                        //Ö÷œÇ»æÍŒº¯Êý
                        player.draw(canvas, paint);
                        if (isBoss == false) {
                            //µÐ»ú»æÖÆ
                            for (int i = 0; i < vcEnemy.size(); i++) {
                                vcEnemy.elementAt(i).draw(canvas, paint);
                            }
                            //µÐ»ú×Óµ¯»æÖÆ
                            for (int i = 0; i < vcBullet.size(); i++) {
                                vcBullet.elementAt(i).draw(canvas, paint);
                            }
                        } else {
                            //BoosµÄ»æÖÆ
                            boss.draw(canvas, paint);
                            //Boss×Óµ¯ÂßŒ­
                            for (int i = 0; i < vcBulletBoss.size(); i++) {
                                vcBulletBoss.elementAt(i).draw(canvas, paint);
                            }
                        }
                        //ŽŠÀíÖ÷œÇ×Óµ¯»æÖÆ
                        for (int i = 0; i < vcBulletPlayer.size(); i++) {
                            vcBulletPlayer.elementAt(i).draw(canvas, paint);
                        }
                        //±¬ÕšÐ§¹û»æÖÆ
                        for (int i = 0; i < vcBoom.size(); i++) {
                            vcBoom.elementAt(i).draw(canvas, paint);
                        }
                        break;
                    case GAME_PAUSE:
                        break;
                    case GAME_WIN:
                        canvas.drawBitmap(bmpGameWin, 0, 0, paint);
                        break;
                    case GAME_LOST:
                        canvas.drawBitmap(bmpGameLost, 0, 0, paint);
                        break;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Ž¥ÆÁÊÂŒþŒàÌý
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Ž¥ÆÁŒàÌýÊÂŒþº¯ÊýžùŸÝÓÎÏ·×ŽÌ¬²»Í¬œøÐÐ²»Í¬ŒàÌý
        switch (gameState) {
            case GAME_MENU:
                //²Ëµ¥µÄŽ¥ÆÁÊÂŒþŽŠÀí
                gameMenu.onTouchEvent(event);
                break;
            case GAMEING:
                player.onTouchEvent(event);
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:

                break;
        }
        return true;
    }

    /**
     * °ŽŒü°ŽÏÂÊÂŒþŒàÌý
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //ŽŠÀíback·µ»Ø°ŽŒü
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //ÓÎÏ·Ê€Àû¡¢Ê§°Ü¡¢œøÐÐÊ±¶ŒÄ¬ÈÏ·µ»Ø²Ëµ¥
            if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
                gameState = GAME_MENU;
                //Boss×ŽÌ¬ÉèÖÃÎªÃ»³öÏÖ
                isBoss = false;
                //ÖØÖÃÓÎÏ·
                initGame();
                //ÖØÖÃ¹ÖÎï³ö³¡
                enemyArrayIndex = 0;
            } else if (gameState == GAME_MENU) {
                //µ±Ç°ÓÎÏ·×ŽÌ¬ÔÚ²Ëµ¥œçÃæ£¬Ä¬ÈÏ·µ»Ø°ŽŒüÍË³öÓÎÏ·
                MainActivity.instance.finish();
                System.exit(0);
            }
            //±íÊŸŽË°ŽŒüÒÑŽŠÀí£¬²»ÔÙœ»žøÏµÍ³ŽŠÀí£¬
            //ŽÓ¶ø±ÜÃâÓÎÏ·±»ÇÐÈëºóÌš
            return true;
        }
        //°ŽŒüŒàÌýÊÂŒþº¯ÊýžùŸÝÓÎÏ·×ŽÌ¬²»Í¬œøÐÐ²»Í¬ŒàÌý
        switch (gameState) {
            case GAME_MENU:
                break;
            case GAMEING:
                //Ö÷œÇµÄ°ŽŒü°ŽÏÂÊÂŒþ
                player.onKeyDown(keyCode, event);
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * °ŽŒüÌ§ÆðÊÂŒþŒàÌý
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //ŽŠÀíback·µ»Ø°ŽŒü
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //ÓÎÏ·Ê€Àû¡¢Ê§°Ü¡¢œøÐÐÊ±¶ŒÄ¬ÈÏ·µ»Ø²Ëµ¥
            if (gameState == GAMEING || gameState == GAME_WIN || gameState == GAME_LOST) {
                gameState = GAME_MENU;
            }
            //±íÊŸŽË°ŽŒüÒÑŽŠÀí£¬²»ÔÙœ»žøÏµÍ³ŽŠÀí£¬
            //ŽÓ¶ø±ÜÃâÓÎÏ·±»ÇÐÈëºóÌš
            return true;
        }
        //°ŽŒüŒàÌýÊÂŒþº¯ÊýžùŸÝÓÎÏ·×ŽÌ¬²»Í¬œøÐÐ²»Í¬ŒàÌý
        switch (gameState) {
            case GAME_MENU:
                break;
            case GAMEING:
                //°ŽŒüÌ§ÆðÊÂŒþ
                player.onKeyUp(keyCode, event);
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ÓÎÏ·ÂßŒ­
     */
    private void logic() {
        //ÂßŒ­ŽŠÀížùŸÝÓÎÏ·×ŽÌ¬²»Í¬œøÐÐ²»Í¬ŽŠÀí
        switch (gameState) {
            case GAME_MENU:
                break;
            case GAMEING:
                //±³Ÿ°ÂßŒ­
                backGround.logic();
                //Ö÷œÇÂßŒ­
                player.logic();
                //µÐ»úÂßŒ­
                if (isBoss == false) {
                    //µÐ»úÂßŒ­
                    for (int i = 0; i < vcEnemy.size(); i++) {
                        Enemy en = vcEnemy.elementAt(i);
                        //ÒòÎªÈÝÆ÷²»¶ÏÌíŒÓµÐ»ú £¬ÄÇÃŽ¶ÔµÐ»úisDeadÅÐ¶š£¬
                        //Èç¹ûÒÑËÀÍöÄÇÃŽŸÍŽÓÈÝÆ÷ÖÐÉŸ³ý,¶ÔÈÝÆ÷ÆðµœÁËÓÅ»¯×÷ÓÃ£»
                        if (en.isDead) {
                            vcEnemy.removeElementAt(i);
                        } else {
                            en.logic();
                        }
                    }
                    //Éú³ÉµÐ»ú
                    count++;
                    if (count % createEnemyTime == 0) {
                        for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
                            //²ÔÓ¬
                            if (enemyArray[enemyArrayIndex][i] == 1) {
                                int x = random.nextInt(screenW - 100) + 50;
                                vcEnemy.addElement(new Enemy(bmpEnemyFly, 1, x, -50));
                                //ÑŒ×Ó×ó
                            } else if (enemyArray[enemyArrayIndex][i] == 2) {
                                int y = random.nextInt(20);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 2, -50, y));
                                //ÑŒ×ÓÓÒ
                            } else if (enemyArray[enemyArrayIndex][i] == 3) {
                                int y = random.nextInt(20);
                                vcEnemy.addElement(new Enemy(bmpEnemyDuck, 3, screenW + 50, y));
                            }
                        }
                        //ÕâÀïÅÐ¶ÏÏÂÒ»×éÊÇ·ñÎª×îºóÒ»×é(Boss)
                        if (enemyArrayIndex == enemyArray.length - 1) {
                            isBoss = true;
                        } else {
                            enemyArrayIndex++;
                        }
                    }
                    //ŽŠÀíµÐ»úÓëÖ÷œÇµÄÅö×²
                    for (int i = 0; i < vcEnemy.size(); i++) {
                        if (player.isCollsionWith(vcEnemy.elementAt(i))) {
                            //·¢ÉúÅö×²£¬Ö÷œÇÑªÁ¿-1
                            player.setPlayerHp(player.getPlayerHp() - 1);
                            //µ±Ö÷œÇÑªÁ¿Ð¡ÓÚ0£¬ÅÐ¶šÓÎÏ·Ê§°Ü
                            if (player.getPlayerHp() <= -1) {
                                gameState = GAME_LOST;
                            }
                        }
                    }
                    //Ã¿2ÃëÌíŒÓÒ»žöµÐ»ú×Óµ¯
                    countEnemyBullet++;
                    if (countEnemyBullet % 40 == 0) {
                        for (int i = 0; i < vcEnemy.size(); i++) {
                            Enemy en = vcEnemy.elementAt(i);
                            //²»Í¬ÀàÐÍµÐ»ú²»Í¬µÄ×Óµ¯ÔËÐÐ¹ìŒ£
                            int bulletType = 0;
                            switch (en.type) {
                                //²ÔÓ¬
                                case Enemy.TYPE_FLY:
                                    bulletType = Bullet.BULLET_FLY;
                                    break;
                                //ÑŒ×Ó
                                case Enemy.TYPE_DUCKL:
                                case Enemy.TYPE_DUCKR:
                                    bulletType = Bullet.BULLET_DUCK;
                                    break;
                            }
                            vcBullet.add(new Bullet(bmpEnemyBullet, en.x + 10, en.y + 20, bulletType));
                        }
                    }
                    //ŽŠÀíµÐ»ú×Óµ¯ÂßŒ­
                    for (int i = 0; i < vcBullet.size(); i++) {
                        Bullet b = vcBullet.elementAt(i);
                        if (b.isDead) {
                            vcBullet.removeElement(b);
                        } else {
                            b.logic();
                        }
                    }
                    //ŽŠÀíµÐ»ú×Óµ¯ÓëÖ÷œÇÅö×²
                    for (int i = 0; i < vcBullet.size(); i++) {
                        if (player.isCollsionWith(vcBullet.elementAt(i))) {
                            //·¢ÉúÅö×²£¬Ö÷œÇÑªÁ¿-1
                            player.setPlayerHp(player.getPlayerHp() - 1);
                            //µ±Ö÷œÇÑªÁ¿Ð¡ÓÚ0£¬ÅÐ¶šÓÎÏ·Ê§°Ü
                            if (player.getPlayerHp() <= -1) {
                                gameState = GAME_LOST;
                            }
                        }
                    }
                    //ŽŠÀíÖ÷œÇ×Óµ¯ÓëµÐ»úÅö×²
                    for (int i = 0; i < vcBulletPlayer.size(); i++) {
                        //È¡³öÖ÷œÇ×Óµ¯ÈÝÆ÷µÄÃ¿žöÔªËØ
                        Bullet blPlayer = vcBulletPlayer.elementAt(i);
                        for (int j = 0; j < vcEnemy.size(); j++) {
                            //ÌíŒÓ±¬ÕšÐ§¹û
                            //È¡³öµÐ»úÈÝÆ÷µÄÃ¿žöÔªÓëÖ÷œÇ×Óµ¯±éÀúÅÐ¶Ï
                            if (vcEnemy.elementAt(j).isCollsionWith(blPlayer)) {
                                vcBoom.add(new Boom(bmpBoom, vcEnemy.elementAt(j).x, vcEnemy.elementAt(j).y, 7));
                            }
                        }
                    }
                } else {//BossÏà¹ØÂßŒ­
                    //Ã¿0.5ÃëÌíŒÓÒ»žöÖ÷œÇ×Óµ¯
                    boss.logic();
                    if (countPlayerBullet % 10 == 0) {
                        //BossµÄÃ»·¢·èÖ®Ç°µÄÆÕÍš×Óµ¯
                        vcBulletBoss.add(new Bullet(bmpBossBullet, boss.x + 35, boss.y + 40, Bullet.BULLET_FLY));
                    }
                    //Boss×Óµ¯ÂßŒ­
                    for (int i = 0; i < vcBulletBoss.size(); i++) {
                        Bullet b = vcBulletBoss.elementAt(i);
                        if (b.isDead) {
                            vcBulletBoss.removeElement(b);
                        } else {
                            b.logic();
                        }
                    }
                    //Boss×Óµ¯ÓëÖ÷œÇµÄÅö×²
                    for (int i = 0; i < vcBulletBoss.size(); i++) {
                        if (player.isCollsionWith(vcBulletBoss.elementAt(i))) {
                            //·¢ÉúÅö×²£¬Ö÷œÇÑªÁ¿-1
                            player.setPlayerHp(player.getPlayerHp() - 1);
                            //µ±Ö÷œÇÑªÁ¿Ð¡ÓÚ0£¬ÅÐ¶šÓÎÏ·Ê§°Ü
                            if (player.getPlayerHp() <= -1) {
                                gameState = GAME_LOST;
                            }
                        }
                    }
                    //Boss±»Ö÷œÇ×Óµ¯»÷ÖÐ£¬²úÉú±¬ÕšÐ§¹û
                    for (int i = 0; i < vcBulletPlayer.size(); i++) {
                        Bullet b = vcBulletPlayer.elementAt(i);
                        if (boss.isCollsionWith(b)) {
                            if (boss.hp <= 0) {
                                //ÓÎÏ·Ê€Àû
                                gameState = GAME_WIN;
                            } else {
                                //Œ°Ê±ÉŸ³ý±ŸŽÎÅö×²µÄ×Óµ¯£¬·ÀÖ¹ÖØžŽÅÐ¶šŽË×Óµ¯ÓëBossÅö×²¡¢
                                b.isDead = true;
                                //BossÑªÁ¿Œõ1
                                boss.setHp(boss.hp - 1);
                                //ÔÚBossÉÏÌíŒÓÈýžöBoss±¬ÕšÐ§¹û
                                vcBoom.add(new Boom(bmpBoosBoom, boss.x + 25, boss.y + 30, 5));
                                vcBoom.add(new Boom(bmpBoosBoom, boss.x + 35, boss.y + 40, 5));
                                vcBoom.add(new Boom(bmpBoosBoom, boss.x + 45, boss.y + 50, 5));
                            }
                        }
                    }
                }
                //Ã¿1ÃëÌíŒÓÒ»žöÖ÷œÇ×Óµ¯
                countPlayerBullet++;
                if (countPlayerBullet % 20 == 0) {
                    vcBulletPlayer.add(new Bullet(bmpBullet, player.x + 15, player.y - 20, Bullet.BULLET_PLAYER));
                }
                //ŽŠÀíÖ÷œÇ×Óµ¯ÂßŒ­
                for (int i = 0; i < vcBulletPlayer.size(); i++) {
                    Bullet b = vcBulletPlayer.elementAt(i);
                    if (b.isDead) {
                        vcBulletPlayer.removeElement(b);
                    } else {
                        b.logic();
                    }
                }
                //±¬ÕšÐ§¹ûÂßŒ­
                for (int i = 0; i < vcBoom.size(); i++) {
                    Boom boom = vcBoom.elementAt(i);
                    if (boom.playEnd) {
                        //²¥·ÅÍê±ÏµÄŽÓÈÝÆ÷ÖÐÉŸ³ý
                        vcBoom.removeElementAt(i);
                    } else {
                        vcBoom.elementAt(i).logic();
                    }
                }
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;

        }
    }

    @Override
    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * SurfaceViewÊÓÍŒ×ŽÌ¬·¢ÉúžÄ±ä£¬ÏìÓŠŽËº¯Êý
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * SurfaceViewÊÓÍŒÏûÍöÊ±£¬ÏìÓŠŽËº¯Êý
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
    }
}