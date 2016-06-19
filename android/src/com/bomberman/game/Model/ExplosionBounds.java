package com.bomberman.game.Model;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by huddy on 6/19/16.
 */
public class ExplosionBounds {
    private int x, y, ymin, ymax, xmin, xmax;
    private Rectangle[] toDelete;


    public ExplosionBounds(int x, int y, int ymin, int ymax, int xmin, int xmax, Rectangle[] toDelete) {
        this.x = x;
        this.y = y;
        this.ymin = ymin;
        this.ymax = ymax;
        this.toDelete = toDelete;
        this.xmin = xmin;
        this.xmax = xmax;
    }

    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public int getYmin()
    {
        return this.ymin;
    }
    public int getXmax()
    {
        return this.xmax;
    }
    public int getYmax()
    {
        return this.ymax;
    }
    public int getXmin()
    {
        return this.xmin;
    }

    public Rectangle[] getToDelete()
    {
        return this.toDelete;
    }
}
