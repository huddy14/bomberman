package com.bomberman.game.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.bomberman.game.Constants;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.UIFactory;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by huddy on 6/22/16.
 */
public class MenuScreen extends Stage implements Screen {

    private Texture textureBg;
    private Texture textureExit;
    private Texture texturePlay;


    public void buildStage()
    {
        Image bg = new Image(textureBg);
        addActor(bg);

        ImageButton btnPlay = UIFactory.createButton(texturePlay);
        btnPlay.setPosition(getWidth()/2,120f, Align.center);
        addActor(btnPlay);

        ImageButton btnExit = UIFactory.createButton(textureExit);
        btnExit.setPosition(getWidth()/2,60f,Align.center);

        btnExit.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.exit();
                        return false;
                    }
                });

        btnPlay.addListener(UIFactory.createListener(ScreenEnum.GAME,1));

    }

    public MenuScreen()
    {
        super();
        textureBg = new Texture(Constants.BACKGROUND_MENU);
        textureExit = new Texture(Constants.STOP_BUTTON_MENU);
        texturePlay = new Texture(Constants.PLAY_BUTTON_MENU);
        buildStage();
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Calling to Stage methods
        super.act(delta);
        super.draw();
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
