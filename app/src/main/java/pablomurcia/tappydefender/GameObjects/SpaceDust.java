package pablomurcia.tappydefender.GameObjects;

import java.util.Random;

/**
 * Created by ifbpmurcia.externos on 6/10/17.
 */

public class SpaceDust {

    private int x, y, speed, maxX, maxY;
    private int minX = 0;
    private int minY = 0;

    public SpaceDust(int screenX, int screenY){
        maxX = screenX;
        maxY = screenY;

        Random generator = new Random();
        speed = generator.nextInt(10);

        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }

    public void update(int playerSpeed) {
        //Move left & speed up when the player does
        x -= playerSpeed;
        x -= speed;

        //Respawn
        if (x<0){
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(15);
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
