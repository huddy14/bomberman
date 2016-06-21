package com.bomberman.game;

import com.badlogic.gdx.Game;
import com.bomberman.game.Screen.GameScreen;
import com.bomberman.game.Screen.SplashScreen;

/**
 * Created by Patryk on 15.05.2016.
 */
public class BombGame extends Game {

    public SplashScreen game;

    //Utworzenie obiektu ekranu
    @Override
    public void create() {
        game = new SplashScreen(this);
        setScreen(game);
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
