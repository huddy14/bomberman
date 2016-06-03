package com.bomberman.game.View;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bomberman.game.Constants;
import com.bomberman.game.Screen.MapCamera;

/**
 * Created by huddy on 6/3/16.
 */
public class BombView extends SpriteBatch {

    private Animation animation;
    private float elapsedTime = 0;

    public BombView()
    {
        animation = new Animation(1/10f,new TextureAtlas(Constants.BOMB_ANIMATION).getRegions());
    }



    public boolean drawBomb(float deltaTime, float x, float y)
    {
        elapsedTime += deltaTime;
        if(elapsedTime < 3f)
        {
            this.begin();
            this.draw(animation.getKeyFrame(elapsedTime,true),x,y);
            this.end();
            return true;
        }
        else
        {
            elapsedTime = 0;
            return false;
        }
    }

}
