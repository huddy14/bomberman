package com.bomberman.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.Constants;

/**
 * Created by huddy on 6/3/16.
 */
public class BombView extends SpriteBatch {

    private Animation animation;


    public BombView()
    {
        animation = new Animation(1/10f,new TextureAtlas(Constants.BOMB_ANIMATION).getRegions());
    }


    //rysowanie bomby przez okreslony czas
    public void drawBomb(Vector2 position)
    {
        this.begin();
        this.draw(animation.getKeyFrame(Gdx.graphics.getDeltaTime(),true),position.x,position.y);
        this.end();
    }



}