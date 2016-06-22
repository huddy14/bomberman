package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bomberman.game.BombGame;
import com.bomberman.game.Constants;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by huddy on 6/21/16.
 */
//TODO:splash jakby sie animowal bylby sztos, a jak nie to niech sie sam wylaczy po 3 sekundach jak nikt nie kliknie nic
public class SplashScreen implements Screen {
    private SpriteBatch batch = new SpriteBatch();
    private Texture texture = new Texture(Constants.SPLASH_SCREEN);
    private boolean timerIsOn = false;
    private GameScreen gameScreen;
    private BombGame game;

    public SplashScreen()
    {
        this.game = game;
        //gameScreen = new GameScreen(game);
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
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
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
