package com.bomberman.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Constants;

/**
 * Created by huddy on 6/1/16.
 */
//przygotowanie wygladu dżojstika
public class TouchpadView extends Touchpad
{
    private Skin touchpadSkin;
    private Drawable touchBackground,touchKnob;

    public TouchpadView(float deadzoneRadius, TouchpadStyle style) {
        super(deadzoneRadius, style);
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture(AssetsPaths.TOUCHPAD_BACKGROUND));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture(AssetsPaths.TOUCHPAD_KNOB));
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        style.background = touchBackground;
        style.knob = touchKnob;
        //Create new TouchPad with the created style
        this.setBounds(touchBackground.getRightWidth()/2 + Constants.TILE_SIZE / 4,touchBackground.getRightWidth()/2 + Constants.TILE_SIZE/4,
                Gdx.graphics.getWidth()/5f,
                Gdx.graphics.getWidth()/5f);
    }



}
