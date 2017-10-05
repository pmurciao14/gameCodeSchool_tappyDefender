package pablomurcia.tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by ifbpmurcia.externos on 5/10/17.
 */

public class PlayerShip {

    private final int INITIAL_SPEED = 1;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    private final int INITIAL_X = 50;
    private final int INITIAL_Y = 50;
    private int maxY;
    private int minY;
    private final int GRAVITY = -12;



    private Bitmap bitmap;
    private int x, y;
    private int speed = 0;
    private boolean boosting;

    public PlayerShip(Context context, int screenX, int screenY){
        x = INITIAL_X;
        y = INITIAL_Y;

        speed = INITIAL_SPEED;
        boosting = false;
        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);

        maxY = screenY-getBitmap().getHeight();
        minY = 0;
    }

    public void update(){
        x++;

        if (boosting) {
            speed += 2;
        } else {
            speed -= 5;
        }

        //Constraint max & min speed
        if (speed > MAX_SPEED){
            speed = MAX_SPEED;
        }

        if (speed<MIN_SPEED){
            speed=MIN_SPEED;
        }

        //Simulate gravity but avoid ship go off screen
        y -= speed + GRAVITY;

        if (y<minY){
            y=minY;
        }

        if (y>maxY){
            y=maxY;
        }
    }

    public void setBoosting(){
        boosting=true;
    }

    public void stopBoosting(){
        boosting=false;
    }

    //Getters
    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getSpeed(){
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
