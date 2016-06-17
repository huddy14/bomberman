package com.bomberman.game.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Patryk on 15.05.2016.
 */
public class Bomberman {

    public static final int SIZE = 10;
    private int availibleBombs = 3;
    private int bombPlanted = 0;
    Vector2 position = new Vector2();
    float velocity = 3f;
    Rectangle bounds = new Rectangle();
    Status status = Status.IDLE;
    Direction direction = Direction.DOWN;
    private static final double pi = Math.PI;
    boolean collision;

    public Bomberman(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public boolean isBombPlanted()
    {
        return bombPlanted>0;
    }
    public boolean canPlant()
    {
        return bombPlanted<availibleBombs;
    }

    public void addBombCount() {
        availibleBombs++;
    }

    public void resestBombCount() {
        availibleBombs = 1;
    }

    public void bombPlanted()
    {
        bombPlanted++;
    }
    public void bombExploded()
    {
        bombPlanted--;
    }

    //Możliwe stany gracza
    public enum Status {
        IDLE, MOVE, DEAD
    }

    //Kierunek
    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }


    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setX(float x)
    {
        position.x = x;
    }

    public void setY(float y)
    {
        position.y =y;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity)
    {
        this.velocity=velocity;
    }

    public void update(double x, double y) {

        if (x == 0 && y == 0)status=Status.IDLE;
        else status=Status.MOVE;
        direction = calculateDirection(x,y);
        if(status.equals(Status.MOVE)) {
            switch (direction) {
                case LEFT:
                    position.x -= velocity;
                    break;
                case RIGHT:
                    position.x += velocity;
                    break;
                case UP:
                    position.y += velocity;
                    break;
                case DOWN:
                    position.y -= velocity;
                    break;
                default:
                    break;
            }
        }

    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    private Direction calculateDirection(double x, double y)
    {

        double angle = Math.atan2(x,y);

        if(x == 0 && y ==0)return Direction.DOWN;
        else if(angle >=-(pi/4) && angle < pi/4)return Direction.UP;
        else if(angle < -(pi/4d) && angle >= -((3d/4d)*pi))return Direction.LEFT;
        else if(angle > -((3d/4d)*pi) && angle <= (3d/4d)*pi)return Direction.RIGHT;
        //else if(angle >= pi/4d && angle < ((3d/4d)*pi))return Direction.DOWN;
        else return Direction.DOWN;


    }

    public Rectangle getRectangle()
    {
        return new Rectangle(position.x,position.y,50,50);
    }


}
