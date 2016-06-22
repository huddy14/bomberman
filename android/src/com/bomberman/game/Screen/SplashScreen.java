package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomberman.game.BombGame;
import com.bomberman.game.Constants;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by huddy on 6/21/16.
 */
//TODO:splash jakby sie animowal bylby sztos, a jak nie to niech sie sam wylaczy po 3 sekundach jak nikt nie kliknie nic
public class SplashScreen extends AbstractScreen {
    private Texture texture = new Texture(Constants.SPLASH_SCREEN);
    private Image background;

    public SplashScreen() {
        super();
        buildStage();
    }

    @Override
    public void buildStage() {
        background = new Image(texture);
        background.setBounds(0, 0, getWidth(), getHeight());
        background.setFillParent(true);
        addActor(background);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        onScreenTouch();
    }

    private void onScreenTouch() {
        if (Gdx.input.justTouched()) {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
    }
}