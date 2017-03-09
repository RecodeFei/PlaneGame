package com.example.feikuang.planegame;

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
 * Created by feikuang on 1/6/17.
 */
public class MySurfaceView extends SurfaceView implements Callback, Runnable{
    //定义游戏状态常量
    public static final int GAME_MENU = 0;//游戏菜单
    public static final int GAMEING = 1;//游戏中
    public static final int GAME_WIN = 2;//游戏胜利
    public static final int GAME_LOST = 3;//游戏失败
    public static final int GAME_PAUSE = -1;//游戏菜单
    private SurfaceHolder sfh;
    public static int screenW, screenH;
    private Paint paint;
    private Thread th;
    private Canvas canvas;
    private boolean flag;
    //声明一个Resources实例便于加载图片
    private Resources res = this.getResources();
    //声明游戏需要用到的图片资源
    private Bitmap bmpBackGround;//游戏背景
    private Bitmap bmpBoom;//爆炸效果
    private Bitmap bmpBoosBoom;//Boos爆炸效果
    private Bitmap bmpButtion;//游戏开始按钮
    private Bitmap bmpButtionPress;//游戏开始按钮被点击
    private Bitmap bmpEnemyDuck;//怪物鸭子
    private Bitmap bmpEnemyFly;//怪物苍蝇
    private Bitmap bmpEnemyBoos;//怪物猪头Boos
    private Bitmap bmpGameWin;//游戏胜利背景
    private Bitmap bmpGameLost;//游戏失败背景
    private Bitmap bmpPlayer;//游戏主角飞机
    private Bitmap bmpPlayerHp;//主角飞机血量
    private Bitmap bmpMenu;//菜单背景
    private Bitmap bmpBullet;//子弹
    private Bitmap bmpEnemyBullet;//敌机子弹
    private Bitmap bmpBoosBullet;//Boos子弹
    //当前游戏状态（默认厨师在游戏菜单界面）
    public static int gameState = GAME_MENU;
    //声明一个菜单对象
    private GameMenu gameMenu;
    //初始化函数
    //声明一个滚动游戏背景对象
    private GameBg backGround;
    //声明主角对象
    private  Player player;


    //初始化函数
    public MySurfaceView(Context context){
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);
    }
    private void initGame() {
        //放置游戏切入后台重新进入游戏时，游戏被重置！
        //当游戏状态处于菜单时，才会重置游戏
        if (gameState == GAME_MENU) {
            bmpBackGround = BitmapFactory.decodeResource(res, R.drawable.background);
            bmpBoom = BitmapFactory.decodeResource(res,R.drawable.boom);
            bmpBoosBoom = BitmapFactory.decodeResource(res,R.drawable.boos_boom);
            bmpButtion = BitmapFactory.decodeResource(res,R.drawable.button);
            bmpButtionPress = BitmapFactory.decodeResource(res,R.drawable.button_press);
            bmpEnemyFly = BitmapFactory.decodeResource(res,R.drawable.enemy_fly);
            bmpEnemyDuck = BitmapFactory.decodeResource(res,R.drawable.enemy_duck);
            bmpEnemyBoos = BitmapFactory.decodeResource(res,R.drawable.enemy_pig);
            bmpGameWin = BitmapFactory.decodeResource(res,R.drawable.gamewin);
            bmpGameLost = BitmapFactory.decodeResource(res,R.drawable.gamelost);
            bmpPlayer = BitmapFactory.decodeResource(res,R.drawable.player);
            bmpPlayerHp = BitmapFactory.decodeResource(res,R.drawable.hp);
            bmpMenu = BitmapFactory.decodeResource(res,R.drawable.menu);
            bmpBullet = BitmapFactory.decodeResource(res,R.drawable.bullet);
            bmpEnemyBullet = BitmapFactory.decodeResource(res,R.drawable.bullet_enemy);
            bmpBoosBullet = BitmapFactory.decodeResource(res,R.drawable.boosbullet);
        }
        //菜单类实例
        gameMenu = new GameMenu(bmpMenu,bmpButtion,bmpButtionPress);
        //背景图实例
        backGround = new GameBg(bmpBackGround);
        //实例化主角
        player = new Player(bmpPlayer,bmpPlayerHp);
    }
    //视图创建函数
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        initGame();
        flag = true;
        th = new Thread(this);
        th.start();
    }
    public void surfaceChanged(SurfaceHolder holder,int format,int width, int height){

    }
    public void surfaceDestroyed(SurfaceHolder holder){

    }
    //绘图函数
    public void myDraw() {
        //绘图函数根据游戏状态不同进行不同绘制
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null){
                canvas.drawColor(Color.WHITE);
                switch (gameState) {
                    case GAME_MENU:
                        //调用menu类，启动
                        gameMenu.draw(canvas,paint);
                        break;
                    case GAMEING:
                        backGround.draw(canvas,paint);
                        //主角绘图函数
                        player.draw(canvas,paint);
                        break;
                    case GAME_PAUSE:
                        break;
                    case GAME_WIN:
                        break;
                    case GAME_LOST:
                        break;
                }
            }
        }catch (Exception e){
        }finally {
            if (canvas != null){
                sfh.unlockCanvasAndPost(canvas);
            }
        }

    }
    //按键监听事件函数根据游戏不同状态进行不同监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (gameState){
            case GAME_MENU:
                break;
            case GAMEING:
                player.onKeyDown(keyCode,event);
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;
        }
        return super.onKeyDown(keyCode,event);
    }
    //实体按键抬起监听函数
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //按键监听事件函数根据游戏状态不同进行不同监听
        switch (gameState) {
            case GAME_MENU:
                break;
            case GAMEING:
                player.onKeyUp(keyCode,event);
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;
        }
        return super.onKeyUp(keyCode,event);
    }
    //触屏事件监听函数
    public boolean onTouchEvent(MotionEvent event) {
        //触屏监听事件函数根据游戏状态不同进行监听
        switch (gameState){
            case GAME_MENU:
                gameMenu.onTouchEvent(event);
                break;
            case GAMEING:
                player.onTouchEvent(event);
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;
        }
        return true;
    }
    //逻辑函数
    private void logic(){
        //逻辑处理根据游戏章台不同进行不同处理
        switch (gameState) {
            case GAME_MENU:
                break;
            case GAMEING:
                backGround.logic();
                //主角逻辑
                player.logic();
                break;
            case GAME_PAUSE:
                break;
            case GAME_WIN:
                break;
            case GAME_LOST:
                break;
        }
    }

    public void run() {
        while (flag) {
            long start = System.currentTimeMillis();
            myDraw();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50){
                    Thread.sleep(50 - (end - start));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
