package com.bomberman.game.Model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.Constants;
import com.bomberman.game.Controller.BombController;
import com.bomberman.game.View.BombView;
import com.bomberman.game.View.BoomView;

import java.util.ArrayList;

/**
 * Created by huddy on 6/3/16.
 */
public class Bomb {
    public static interface BombListener {
        public void onBombExploded(Bomb bomb);
        public void onExplosionFinished(Bomb bomb);
        public ExplosionBounds onBombPlanted(Bomb bomb);
    }

    private Vector2 position = new Vector2();
    private Circle bounds;
    private BombListener mListener;
    private final static float CELL_SIZE = 64;
    private final static float WIDHT = 900;
    private final static float HEIGHT = 900;
    private State state = State.COUNT_DOWN;
    private float remainingSeconds = 3;
    private BoomView explosion = new BoomView();
    private ExplosionBounds explosionBounds;
    private int range = 2;


    public static enum State {COUNT_DOWN, EXPLODED,EXPLOSION_FINISHED}
    //private boolean exploded = false;

    public Bomb(Vector2 position, BombController bombController) {
        //dodajemy polowe szerkosci i wysokosci komorki, zeby bomba sie dobrze rysowala sie w odpowiedniej komorce
        this.position.x = fixPosition(position.x + CELL_SIZE / 2);
        this.position.y = fixPosition(position.y + CELL_SIZE / 2);
        bounds = new Circle(this.position.x + 24f, this.position.y + 24f, 2 * CELL_SIZE + 15f);
        mListener = bombController;
        explosionBounds = mListener.onBombPlanted(this);

    }

    public int getRange()
    {
        return this.range;
    }
    public void setRemaningSeconds(float remainingSeconds)
    {
        this.remainingSeconds=remainingSeconds;
    }
    public float getRemainingSeconds()
    {
        return this.remainingSeconds;
    }

    public void update(float deltaTime) {
        this.remainingSeconds -= deltaTime;
        if (remainingSeconds <= 0 && state != State.EXPLODED && state != State.EXPLOSION_FINISHED) {
            this.state = State.EXPLODED;
            mListener.onBombExploded(this);
        }
        if(remainingSeconds<=-1 && state != State.EXPLOSION_FINISHED) {
            this.state = State.EXPLOSION_FINISHED;
            mListener.onExplosionFinished(this);
        }

    }

    public BoomView getExplosion()
    {
        for(int i = explosionBounds.getX(); i <= explosionBounds.getXmax(); i++)
        {
            explosion.drawBoom(tileToPixel(i),tileToPixel(explosionBounds.getY()));

        }
        for(int i = explosionBounds.getX(); i >= explosionBounds.getXmin(); i--)
        {
            explosion.drawBoom(tileToPixel(i),tileToPixel(explosionBounds.getY()));

        }
        for(int i = explosionBounds.getY(); i <= explosionBounds.getYmax(); i++)
        {
            explosion.drawBoom(tileToPixel(explosionBounds.getX()),tileToPixel(i));

        }
        for(int i = explosionBounds.getY(); i <= explosionBounds.getYmin(); i++)
        {
            explosion.drawBoom(tileToPixel(explosionBounds.getX()),tileToPixel(i));

        }
        return explosion;
    }

    private float tileToPixel(int x)
    {
        return Constants.TILE_SIZE * x;
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

    public ExplosionBounds getExplosionBounds()
    {
        return this.explosionBounds;
    }

    public Vector2 getPosition ()
    {
        return this.position;
    }

}

