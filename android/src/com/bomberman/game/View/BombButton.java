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
//TODO: to mozna w ogole wyjebac przycisk stworzyc w gamescreenie
public class BombButton  extends Button{

    public BombButton()
    {
        super(new TextureRegionDrawable(new TextureRegion(new Texture(AssetsPaths.BOMB_BUTTON))));
////        buttonSkin= new Skin();
////        //texture = new TextureAtlas(Gdx.files.internal(AssetsPaths.BOMB_ANIMATION));
////        buttonStyle = new ButtonStyle();
////        //buttonSkin.addRegions(texture);
////        buttonSkin.
////        buttonStyle.down = buttonSkin.getDrawable(AssetsPaths.BOMB_1);
////        buttonStyle.checked = buttonSkin.getDrawable(AssetsPaths.BOMB_2);
////        buttonStyle.up = buttonSkin.getDrawable(AssetsPaths.BOMB_3);
//        this.setD
//        this.setStyle(buttonStyle);
        this.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/8f),
                0 + Gdx.graphics.getHeight()/8f,
                Gdx.graphics.getWidth()/10f,
                Gdx.graphics.getWidth()/10f
        );
    }
}
