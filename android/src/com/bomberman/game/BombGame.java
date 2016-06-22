package com.bomberman.game;

import com.badlogic.gdx.Game;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;
import com.bomberman.game.Screen.SplashScreen;

/**
 * Created by Patryk on 15.05.2016.
 */
public class BombGame extends Game {

    public SplashScreen game;

    //Utworzenie obiektu ekranu
    @Override
    public void create() {
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenEnum.SPLASH);
    }

    @Override
    public void resize(int width, int height) {

    }
/*
    @Override
    public void render() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
    */
}
