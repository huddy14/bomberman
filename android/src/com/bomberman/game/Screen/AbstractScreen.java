package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bomberman.game.Interfaces.IController;

/**
 * Created by huddy on 6/22/16.
 */
public abstract class AbstractScreen extends Stage implements Screen{

    protected AbstractScreen()
    {
        super();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);


    }

    public abstract void buildStage();

    public abstract void onBackButtonPressed();


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        // Czyszczenie ekranu
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // wywołanie aktorów
        super.act(delta);
        super.draw();

        onBackButtonPressed();
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        //Gdx.input.setInputProcessor(null);
        super.dispose();

    }
}
