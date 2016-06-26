package com.bomberman.game.Screen.ScreenManagment;


import android.widget.Toast;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;

/**
 * Created by huddy on 6/22/16.
 * Help for creating buttons and its listeners, which call different screens
 */
public class UIFactory {

    public static Button createButton(Texture texture)
    {
        return new Button(
                new TextureRegionDrawable(
                        new TextureRegion(texture)
                )
        );
    }

    public static InputListener createListener(final ScreenEnum destScreen, final Object... params) {
        return
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x,
                                             float y, int pointer, int button) {
                        ScreenManager.getInstance().showScreen(destScreen, params);
                        return false;
                    }
                };
    }
}
