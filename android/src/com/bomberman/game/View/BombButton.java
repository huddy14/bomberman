package com.bomberman.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by huddy on 6/2/16.
 */
public class BombButton  {
    private Skin buttonSkin;
    private Button button;
    private ButtonStyle buttonStyle;
    private TextureAtlas texture;

    public BombButton()
    {
        buttonSkin= new Skin();
        texture = new TextureAtlas(Gdx.files.internal("Bomb/BombAnimation.pack"));
        buttonStyle = new ButtonStyle();
        buttonSkin.addRegions(texture);
        buttonStyle.down = buttonSkin.getDrawable("Bomb_f01");
        buttonStyle.checked = buttonSkin.getDrawable("Bomb_f02");
        buttonStyle.up = buttonSkin.getDrawable("Bomb_f03");
        button = new Button(buttonStyle);
        button.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/10f),
                0 + Gdx.graphics.getHeight()/10f,
                Gdx.graphics.getWidth()/15f,
                Gdx.graphics.getWidth()/15f
        );
    }



    public Button getButton()
    {
        return button;
    }
}
