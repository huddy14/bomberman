package com.bomberman.game.Model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by huddy on 6/3/16.
 */
public class Bomb {
    private Vector2 position = new Vector2();
    private Circle bounds;
    private final static float CELL_SIZE = 64;
    private final static float WIDHT = 900;
    private final static float HEIGHT = 900;

    public Bomb (Vector2 position)
    {
        //dodajemy polowe szerkosci i wysokosci komorki, zeby bomba sie dobrze rysowala sie w odpowiedniej komorce
        this.position.x = fixPosition(position.x + CELL_SIZE/2);
        this.position.y = fixPosition(position.y + CELL_SIZE/2);
        bounds = new Circle(this.position.x +24f ,this.position.y+24f ,CELL_SIZE + 15f);

    }

    public Circle getBounds()
    {
        return bounds;
    }
//TODO: bomba ma sie wyswietlac rowno miedzy kolumnami
    private float fixPosition(float x)
    {
        float temp = x % CELL_SIZE;
        return x-temp+(CELL_SIZE/8);
        //return x;
    }

    public float getX()
    {
        return position.x;
    }

    public float getY()
    {
        return position.y;
    }

    public Vector2 getPosition ()
    {
        return this.position;
    }

}
