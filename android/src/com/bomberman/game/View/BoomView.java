package com.bomberman.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bomberman.game.Constants;

/**
 * Created by huddy on 6/4/16.
 */
public class BoomView extends SpriteBatch {
    private Animation animation;
    private float elapsedTime = 0;
    private final static float drawingTime = 1f;

    public BoomView ()
    {
        animation = new Animation(1/10f,new TextureAtlas(Constants.BOOM_ANIMATION).getRegions());
    }

    public void drawBoom(float x,float y)
    {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(elapsedTime < drawingTime) {
            this.begin();
            this.draw(animation.getKeyFrame(Gdx.graphics.getDeltaTime(), true), x, y);
            this.end();
        }
        else {
            //this.dispose();

        }
    }

}
