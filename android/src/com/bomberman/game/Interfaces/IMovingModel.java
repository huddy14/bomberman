package com.bomberman.game.Interfaces;

import android.util.Pair;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import net.dermetfan.gdx.physics.box2d.PositionController;

import java.util.HashMap;
import java.util.Map;

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
        private static final Map<Pair<Integer,Integer>,Direction> directions = new HashMap<>();


        static{
            for(Integer i = 0 ; i < Direction.values().length; i++) {
                Direction d = Direction.values()[i];
                directions.put(new Pair<>(d.getX(),d.getY()),d );
            }
        }

        Direction(int dx, int dy)
        {
            this.dy =dy;
            this.dx = dx;
        }
        public Direction getCollisionDirection(int mult)
        {
            Direction dir =directions.get(new Pair<>(Math.abs(dy)*mult,Math.abs(dx)*mult));
            return dir;
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
    Rectangle getSmallBounds();
    float getVelocity();
    void setVelocity(float velocity);

    Status getStatus();
    void setStatus(Status status);
    void update(float x, float y);
    void move(Direction direction);
}
