package com.bomberman.game.Interfaces;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by huddy on 6/20/16.
 */
public interface IMovingModel {

    enum Status {
        IDLE, MOVE, DEAD
    }

    //Kierunek
    enum Direction {
        LEFT(-1,0), RIGHT(1,0), UP(0,1), DOWN(0,-1);

        private final int dx,dy;

        Direction(int dx, int dy)
        {
            this.dy =dy;
            this.dx = dx;
        }
        public int getX(){return this.dx;}
        public int getY(){return this.dy;}
    }

    Direction getDirection();
    void setDirection(Direction direction);
    Vector2 getPosition();
    void setX(float x);
    void setY(float y);
    Rectangle getBounds();
    float getVelocity();
    void setVelocity(float velocity);

    Status getStatus();
    void setStatus(Status status);
    void update(float x, float y);
    void move(Direction direction);
}
