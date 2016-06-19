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
    float x,y;

    public BoomView ()
    {
        animation = new Animation(1/40f,new TextureAtlas(Constants.BOOM_ANIMATION).getRegions());
    }

    public void drawBoom(float x, float y)
    {
        this.x = x * 64f + 16f;
        this.y = y * 64f + 16f;
        this.begin();
        this.draw(animation.getKeyFrame(Gdx.graphics.getDeltaTime(), true), this.x, this.y);
        this.end();

    }

}
