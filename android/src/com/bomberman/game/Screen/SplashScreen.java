package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BooleanArray;
import com.badlogic.gdx.utils.Timer;
import com.bomberman.game.BombGame;
import com.bomberman.game.Constants;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by huddy on 6/21/16.
 */
public class SplashScreen implements Screen {
    private SpriteBatch batch = new SpriteBatch();
    private Texture texture = new Texture(Constants.SPLASH_SCREEN);
    private boolean timerIsOn = false;
    private GameScreen gameScreen = new GameScreen();
    private BombGame game;

    public SplashScreen(BombGame game)
    {
        this.game = game;
        //Gdx.input.setInputProcessor(this);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        onScreenTouch();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

    }

    private void onScreenTouch()
    {
        if(Gdx.input.justTouched())
        {
            game.setScreen(gameScreen);
        }
    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
