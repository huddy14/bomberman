package com.bomberman.game.Model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IMovingModel;

/**
 * Created by Patryk on 15.05.2016.
 */
public class Bomberman implements IMovingModel{

    public static final int SIZE = 62;
    private int availableBombs = 1;
    private int bombPlanted = 0;
    Vector2 position = new Vector2();
    float velocity = 3f;
    int lifes = 3;
    Status status = Status.IDLE;
    Direction direction = Direction.DOWN;
    private static final double pi = Math.PI;

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
        availableBombs = MathUtils.clamp(availableBombs+1,1,5);
    }

    public void resestBombCount() {
        availableBombs = 1;
    }
    public int getBombCount()
    {
        return this.availableBombs;
    }

    public void setAvailableBombs(int availableBombs)
    {
        this.availableBombs = availableBombs;
    }

    public void bombPlanted()
    {
        bombPlanted++;
    }
    public void bombExploded()
    {
        bombPlanted--;
    }

    public int getLifes()
    {
        return this.lifes;
    }
    public void setLifes(int lifes) {
        this.lifes = MathUtils.clamp(lifes,0,3);
    }

    public void deleteLife()
    {
        this.lifes --;
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

    public Rectangle getSmallBounds()
    {
        return new Rectangle(position.x+ Constants.TILE_SIZE/4,position.y+Constants.TILE_SIZE/4,SIZE/2,SIZE/2);
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
        this.velocity = MathUtils.clamp(velocity,1,3.5f);
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
        }
    }

    @Override
    public void move(Direction direction) {
        velocity = 3f;
        this.position.x += velocity * direction.getX();
        this.position.y += velocity * direction.getY();
    }

    public void moveOb(Direction direction) {
        this.position.x += direction.getX();
        this.position.y += direction.getY();
    }


}
