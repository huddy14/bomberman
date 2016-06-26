package com.bomberman.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Constants;

/**
 * Created by huddy on 6/4/16.
 */
//klasa przygotowuje i rysuje eksplozję
public class ExplosionView extends SpriteBatch {
    private Animation animation;
    float x,y;

    public ExplosionView()
    {
        animation = new Animation(1/40f,new TextureAtlas(AssetsPaths.BOOM_ANIMATION).getRegions());
    }

    public void drawBoom(float x, float y)
    {
        this.x = x * Constants.TILE_SIZE + Constants.TILE_SIZE/8;
        this.y = y * Constants.TILE_SIZE + Constants.TILE_SIZE/8;
        this.begin();
        this.draw(animation.getKeyFrame(Gdx.graphics.getDeltaTime(), true), this.x, this.y);
        this.end();

    }

}
