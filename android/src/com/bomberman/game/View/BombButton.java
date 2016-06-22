package com.bomberman.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.bomberman.game.Constants;

/**
 * Created by huddy on 6/2/16.
 */
//TODO: powiekszyc przyicsk kuns3k
public class BombButton  extends Button{
    private Skin buttonSkin;
    private ButtonStyle buttonStyle;
    private TextureAtlas texture;

    public BombButton()
    {
        super();
        buttonSkin= new Skin();
        texture = new TextureAtlas(Gdx.files.internal(Constants.BOMB_ANIMATION));
        buttonStyle = new ButtonStyle();
        buttonSkin.addRegions(texture);
        buttonStyle.down = buttonSkin.getDrawable(Constants.BOMB_1);
        buttonStyle.checked = buttonSkin.getDrawable(Constants.BOMB_2);
        buttonStyle.up = buttonSkin.getDrawable(Constants.BOMB_3);
        this.setStyle(buttonStyle);
        this.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/10f),
                0 + Gdx.graphics.getHeight()/10f,
                Gdx.graphics.getWidth()/15f,
                Gdx.graphics.getWidth()/15f
        );
    }
}
