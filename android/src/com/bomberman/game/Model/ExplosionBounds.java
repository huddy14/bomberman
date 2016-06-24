package com.bomberman.game.Model;

import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Constants;

/**
 * Created by huddy on 6/19/16.
 */
public class ExplosionBounds {
    private int x, y, ymin, ymax, xmin, xmax, bombRange;
    private Rectangle[] toDelete;


    public ExplosionBounds(int x, int y, int ymin, int ymax, int xmin, int xmax, Rectangle[] toDelete) {
        this.x = x;
        this.y = y;

        this.ymin = ymin;
        this.ymax = ymax;
        this.xmin = xmin;
        this.xmax = xmax;

        this.toDelete = toDelete;
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

    public Rectangle getVerticalRectangle()
    {
        return new Rectangle(x* Constants.TILE_SIZE,ymin* Constants.TILE_SIZE, Constants.TILE_SIZE,(ymax-ymin+1) * Constants.TILE_SIZE);
    }

    public Rectangle getHorizontalRectangle()
    {
        return new Rectangle(xmin* Constants.TILE_SIZE,y* Constants.TILE_SIZE, (xmax-xmin+1) * Constants.TILE_SIZE, Constants.TILE_SIZE);

    }
}
