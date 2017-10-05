package pablomurcia.tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by ifbpmurcia.externos on 5/10/17.
 */

public class PlayerShip {

    private final int INITIAL_SPEED = 1;
    private final int INITIAL_X = 50;
    private final int INITIAL_Y = 50;

    private Bitmap bitmap;
    private int x, y;
    private int speed = 0;

    public PlayerShip(Context context){
        x = INITIAL_X;
        y = INITIAL_Y;
        speed = INITIAL_SPEED;

        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
    }

    public void update(){
        x++;
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
