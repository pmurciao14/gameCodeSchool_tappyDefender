package pablomurcia.tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by ifbpmurcia.externos on 6/10/17.
 */

public class EnemyShip {

    private Bitmap bitmap;
    private int x, y, maxY, minY, maxX, minX, speed;

    public EnemyShip(Context context, int screenX, int screenY){
        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.enemyShip);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(6)+10;

        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();
    }
}
