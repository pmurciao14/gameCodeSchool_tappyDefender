package pablomurcia.tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import pablomurcia.tappydefender.GameObjects.EnemyShip;
import pablomurcia.tappydefender.GameObjects.PlayerShip;
import pablomurcia.tappydefender.GameObjects.SpaceDust;

/**
 * Created by ifbpmurcia.externos on 5/10/17.
 */

    //TODO: Enemy ships reduce max Y
    //TODO: Landscape in both positions

public class TDView extends SurfaceView implements Runnable {

    volatile boolean playing;
    Thread gameThread = null;

    //Game Constants
    final int numberOfDustPoints = 150;

    //Game Objects
    private PlayerShip player;
    private EnemyShip enemy1, enemy2, enemy3;
    private ArrayList<SpaceDust> dustList = new ArrayList<SpaceDust>();

    //Drawing Objects
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public TDView(Context context, int screenX, int screenY) {
        super(context);

        //Init holder and drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        //Init ships
        player = new PlayerShip(context, screenX, screenY);
        enemy1 = new EnemyShip(context, screenX, screenY);
        enemy2 = new EnemyShip(context, screenX, screenY);
        enemy3 = new EnemyShip(context, screenX, screenY);

        //Init SpaceDust
        for (int i = 0; i < numberOfDustPoints; i++){
            SpaceDust spec = new SpaceDust(screenX, screenY);
            dustList.add(spec);
        }
    }

    private void update() {
        //Collision detection on new positions before draw
        if (Rect.intersects(player.getHitBox(), enemy1.getHitBox())){
            enemy1.setX(-100);
        }

        if (Rect.intersects(player.getHitBox(), enemy2.getHitBox())){
            enemy2.setX(-100);
        }

        if (Rect.intersects(player.getHitBox(), enemy3.getHitBox())){
            enemy3.setX(-100);
        }

        //Update player
        player.update();

        //Update enemies
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());

        //Update space dust
        for (SpaceDust sd : dustList){
            sd.update(player.getSpeed());
        }
    }

    private void draw(){
        if (ourHolder.getSurface().isValid()){
            //Lock canvas & paint it black
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255,0,0,0));

            //Draw space dust
            paint.setColor(Color.WHITE);
            for (SpaceDust sd:dustList){
                canvas.drawText("*", sd.getX(), sd.getY(), paint);
            }

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
