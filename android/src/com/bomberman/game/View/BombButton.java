package com.bomberman.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bomberman.game.AssetsPaths;

/**
 * Created by huddy on 6/2/16.
 */
//Tworzymy widok przycisku do stawiania bomb
public class BombButton  extends Button{

    public BombButton()
    {
        super(new TextureRegionDrawable(new TextureRegion(new Texture(AssetsPaths.BOMB_BUTTON))));
        this.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/8f),
                0 + Gdx.graphics.getHeight()/8f,
                Gdx.graphics.getWidth()/10f,
                Gdx.graphics.getWidth()/10f
        );
    }
}
