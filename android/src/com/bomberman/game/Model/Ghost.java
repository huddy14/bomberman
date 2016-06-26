package com.bomberman.game.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IMovingModel;

/**
 * Created by huddy on 6/20/16.
 * Logic model for ghost
 */
public class Ghost implements IMovingModel{
    public static final int SIZE = 64;
    Vector2 position = new Vector2();
    float velocity = 0.3f;
    IMovingModel.Status status = IMovingModel.Status.MOVE;
    IMovingModel.Direction direction = IMovingModel.Direction.DOWN;

    public Ghost(Vector2 position)
    {
        this.position=position;
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
        this.position.y = y;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, SIZE,SIZE);
    }

    @Override
    public Rectangle getSmallBounds() {
        return new Rectangle(position.x+ Constants.TILE_SIZE/4,position.y+ Constants.TILE_SIZE/4,SIZE/1.5f,SIZE/1.5f);
    }

    @Override
    public float getVelocity() {
        return velocity;
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
        this.position.x = x;
        this.position.y = y;
    }
    @Override
    public void move(Direction direction) {
        this.position.x += velocity * direction.getX();
        this.position.y += velocity * direction.getY();
    }
}
