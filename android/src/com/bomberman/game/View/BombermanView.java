package com.bomberman.game.View;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Model.Bomberman;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huddy on 6/1/16.
 */
//Klasa ktora przygotowuje i rysuje animacje gracza
public class BombermanView extends SpriteBatch {
    private Map<Bomberman.Direction,Animation>animations=new HashMap<>();

    public BombermanView()
    {
        animations.put(Bomberman.Direction.DOWN, new Animation(1/10f,new TextureAtlas(AssetsPaths.BOMBERMAN_FRONT_PACK).getRegions()));
        animations.put(Bomberman.Direction.UP, new Animation(1/10f,new TextureAtlas(AssetsPaths.BOMBERMAN_BACK_PACK).getRegions()));
        animations.put(Bomberman.Direction.LEFT, new Animation(1/10f,new TextureAtlas(AssetsPaths.BOMBERMAN_SIDE_LEFT_PACK).getRegions()));
        animations.put(Bomberman.Direction.RIGHT, new Animation(1/10f,new TextureAtlas(AssetsPaths.BOMBERMAN_SIDE_RIGHT).getRegions()));

    }

    public void drawBomberman(Bomberman.Direction dir, float deltaTime, float x, float y)
    {
        this.begin();
        this.draw(animations.get(dir).getKeyFrame(deltaTime,true),x,y);
        this.end();
    }

}
