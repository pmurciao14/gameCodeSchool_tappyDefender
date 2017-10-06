package pablomurcia.tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import javax.security.auth.login.LoginException;

/**
 * Created by ifbpmurcia.externos on 5/10/17.
 */

public class TDView extends SurfaceView implements Runnable {

    volatile boolean playing;
    Thread gameThread = null;

    //Game Objects
    private PlayerShip player;
    private EnemyShip enemy1, enemy2, enemy3;

    //Drawing Objects
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public TDView(Context context, int x, int y) {
        super(context);

        //Init holder and drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        //Init ships
        player = new PlayerShip(context, x, y);
        enemy1 = new EnemyShip(context, x, y);
        enemy2 = new EnemyShip(context, x, y);
        enemy3 = new EnemyShip(context, x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while (playing){
            update();
            draw();
            control();
        }
    }

    private void update() {
        //Update player
        player.update();

        //Update enemies
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());
    }

    private void draw(){
        if (ourHolder.getSurface().isValid()){
            //Lock canvas & reserve memory
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255,0,0,0));

            //Draw ships
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            canvas.drawBitmap(enemy1.getBitmap(), enemy1.getX(), enemy1.getY(), paint);
            canvas.drawBitmap(enemy2.getBitmap(), enemy2.getX(), enemy2.getY(), paint);
            canvas.drawBitmap(enemy3.getBitmap(), enemy3.getX(), enemy3.getY(), paint);

            //Unlock canvas and update
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e){
            Log.e("----->", e.getMessage());
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e){
            Log.e("----->", e.getMessage());
        }
    }
}
