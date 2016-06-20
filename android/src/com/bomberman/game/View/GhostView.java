package com.bomberman.game.View;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IMovingModel;
import com.bomberman.game.Model.Bomberman;
import com.bomberman.game.Model.Ghost;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huddy on 6/20/16.
 */
public class GhostView extends SpriteBatch {
    private Map<Ghost.Direction,Animation> animations=new HashMap<>();

    public GhostView()
    {
        animations.put(Ghost.Direction.DOWN, new Animation(1/10f,new TextureAtlas(Constants.GHOST_FRONT_PACK).getRegions()));
        animations.put(Ghost.Direction.UP, new Animation(1/10f,new TextureAtlas(Constants.GHOST_BACK_PACK).getRegions()));
        animations.put(Ghost.Direction.LEFT, new Animation(1/10f,new TextureAtlas(Constants.GHOST_SIDE_PACK).getRegions()));
        animations.put(Ghost.Direction.RIGHT, new Animation(1/10f,new TextureAtlas(Constants.GHOST_SIDE_PACK).getRegions()));
    }

    public void drawGhost(Ghost.Direction dir, float deltaTime, float x, float y)
    {
        this.begin();
        this.draw(animations.get(dir).getKeyFrame(deltaTime,true),x,y);
        this.end();
    }

}
