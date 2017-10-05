package pablomurcia.tappydefender;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;

import javax.security.auth.login.LoginException;

/**
 * Created by ifbpmurcia.externos on 5/10/17.
 */

public class TDView extends SurfaceView implements Runnable {

    volatile boolean playing;
    Thread gameThread = null;

    public TDView(Context context) {
        super(context);
    }

    @Override
    public void run() {
        while (playing){
            update();
            //draw();
            control();
        }
    }

    private void control() {

    }

    private void update() {

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
