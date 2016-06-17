package com.bomberman.game.Model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.Controller.BombController;
import com.bomberman.game.View.BombView;

/**
 * Created by huddy on 6/3/16.
 */
public class Bomb {
    public static interface BombListener{
        public void onBombExploded(Bomb bomb);
        public void onBombPlanted(Bomb bomb);
    }

    private Vector2 position = new Vector2();
    private Circle bounds;
    private BombListener mListener;
    private final static float CELL_SIZE = 64;
    private final static float WIDHT = 900;
    private final static float HEIGHT = 900;
    private State state = State.COUNT_DOWN;
    private float remainingSeconds = 3;

    public static enum State { COUNT_DOWN, EXPLODED }
    //private boolean exploded = false;

    public Bomb (Vector2 position, BombController bombController)
    {
        //dodajemy polowe szerkosci i wysokosci komorki, zeby bomba sie dobrze rysowala sie w odpowiedniej komorce
        this.position.x = fixPosition(position.x + CELL_SIZE/2);
        this.position.y = fixPosition(position.y + CELL_SIZE/2);
        bounds = new Circle(this.position.x +24f ,this.position.y+24f ,CELL_SIZE + 15f);
        mListener = bombController;

    }

    public void update(float deltaTime)
    {
        this.remainingSeconds -= deltaTime;
        if(remainingSeconds<=0)
        {
            this.state = State.EXPLODED;
            mListener.onBombExploded(this);
        }
    }

    public State getState()
    {
        return state;
    }

    public Circle getBounds()
    {
        return bounds;
    }

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
