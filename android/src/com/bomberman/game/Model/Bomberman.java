package com.bomberman.game.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.Interfaces.IMovingModel;

/**
 * Created by Patryk on 15.05.2016.
 */
public class Bomberman implements IMovingModel{

    public static final int SIZE = 50;
    private int availableBombs = 3;
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

    }

    public boolean isBombPlanted()
    {
        return bombPlanted>0;
    }
    public boolean canPlant()
    {
        return bombPlanted< availableBombs;
    }

    public void addBombCount() {
        availableBombs++;
    }

    public void resestBombCount() {
        availableBombs = 1;
    }

    public void bombPlanted()
    {
        bombPlanted++;
    }
    public void bombExploded()
    {
        bombPlanted--;
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


    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    @Override
    public void setX(float x) {
        this.position.x = x;
    }

    @Override
    public void setY(float y) {
        this.position.y=y;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x,position.y,SIZE,SIZE);
    }

    @Override
    public float getVelocity() {
        return this.velocity;
    }

    @Override
    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void update(float x, float y) {
        if (x == 0 && y == 0)status=Status.IDLE;
        else status=Status.MOVE;
        direction = calculateDirection(x,y);
        if(status.equals(Status.MOVE)) {
            move(direction);
//            switch (direction) {
//                case LEFT:
//                    this.position.x -= velocity;
//                    break;
//                case RIGHT:
//                    this.position.x += velocity;
//                    break;
//                case UP:
//                    this.position.y += velocity;
//                    break;
//                case DOWN:
//                    this.position.y -= velocity;
//                    break;
//                default:
//                    break;
//            }
        }
    }

    @Override
    public void move(Direction direction) {
        this.position.x += velocity * direction.getX();
        this.position.y += velocity * direction.getY();
    }
}
