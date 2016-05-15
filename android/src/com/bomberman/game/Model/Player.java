package com.bomberman.game.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Patryk on 15.05.2016.
 */
public class Player {

    public static final int SPEED = 20;
    public static final int SIZE = 10;
    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    Status status = Status.IDLE;

    public Player(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    //Możliwe stany gracza
    public enum Status {
        IDLE, MOVE, DEAD
    }

    //Kierunek
    public enum Direction {
        LEFT, RIGHT, UP, DOWN, NONE
    }


    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
    }
}
