package pablomurcia.tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import javax.security.auth.login.LoginException;

/**
 * Created by ifbpmurcia.externos on 5/10/17.
 */

public class TDView extends SurfaceView implements Runnable {

    volatile boolean playing;
    Thread gameThread = null;

    private PlayerShip player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public TDView(Context context) {
        super(context);

        ourHolder = getHolder();
        paint = new Paint();
        player = new PlayerShip(context);
    }

    @Override
    public void run() {
        while (playing){
            update();
            draw();
            control();
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e){
            Log.e("----->", e.getMessage());
        }
    }

    private void update() {
        player.update();
    }

    private void draw(){
        if (ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255,0,0,0));
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            ourHolder.unlockCanvasAndPost(canvas);
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
