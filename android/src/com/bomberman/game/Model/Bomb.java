package com.bomberman.game.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by huddy on 6/3/16.
 */
public class Bomb {
    private Vector2 position = new Vector2();
    private Rectangle bounds;
    private final static float WIDHT = 64;
    private final static float HEIGHT = 64;

    public Bomb (Vector2 position)
    {
        this.position.x = fixPosition(position.x);
        this.position.y = fixPosition(position.y);
        bounds = new Rectangle(position.x,position.y,WIDHT,HEIGHT);
    }

    public Rectangle getBounds()
    {
        return bounds;
    }
//TODO: bomba ma sie wyswietlac rowno miedzy kolumnami
    private float fixPosition(float x)
    {
//        float temp = x % 64;
//        return x-temp+32;
        return x;
    }

    public float getX()
    {
        return position.x;
    }

    public float getY()
    {
        return position.y;
    }

}
