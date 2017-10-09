package pablomurcia.tappydefender.GameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import pablomurcia.tappydefender.R;

/**
 * Created by ifbpmurcia.externos on 6/10/17.
 */

public class EnemyShip {

    private Bitmap bitmap;
    private int x, y, maxY, minY, maxX, minX, speed;
    private Rect hitBox;

    public EnemyShip(Context context, int screenX, int screenY){
        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_ship);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(6)+10;

        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();

        hitBox = new Rect(x,y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed){
        //Move left & speed up when the player does
        x-=playerSpeed;
        x-=speed;

        //Respawn
        if (x<minX-bitmap.getWidth()){
            Random generator = new Random();
            speed = generator.nextInt(10)+10;
            x = maxX;
            y = generator.nextInt(maxY)-bitmap.getHeight();
        }

        //Refresh hitBox for collisions
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public Rect getHitBox(){
        return hitBox;
    }
}
