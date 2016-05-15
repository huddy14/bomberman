package com.bomberman.game.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Patryk on 15.05.2016.
 */
public class Wall {
    static final int SIZE = 10;

    Vector2 position = new Vector2();
    Rectangle bounds = new Rectangle();

    public Wall(Vector2 pos) {
        this.position = pos;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }
}
