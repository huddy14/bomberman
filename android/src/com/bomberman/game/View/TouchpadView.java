package com.bomberman.game.View;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.*;

/**
 * Created by huddy on 6/1/16.
 */
public class TouchpadView
{
    private Touchpad touchpad;
    private TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground,touchKnob;

    public TouchpadView() {
        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("Touchpad/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("Touchpad/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10,touchpadStyle);

        //setBounds(x,y,width,height)
        touchpad.setBounds(50, 50, 200, 200);
    }
    public Touchpad getTouchpad()
    {
        return touchpad;
    }

}
