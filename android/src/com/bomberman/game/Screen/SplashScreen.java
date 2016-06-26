package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.FontGenerator;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;

/**
 * Created by huddy on 6/21/16.
 */
public class SplashScreen extends AbstractScreen {
    private Texture texture = new Texture(AssetsPaths.SPLASH_SCREEN);
    private Image background;
    private boolean appLoaded = false;
    private boolean loadingApp = false;
    private SequenceAction loadingAction;



    public SplashScreen(Runnable doOnBackground) {
        super();
        buildStage();

        loadingAction = Actions.sequence(
                Actions.run(doOnBackground),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        appLoaded = true;
                    }
                }));
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
        if(!loadingApp)
        {
            addAction(loadingAction);
            loadingApp = true;
        }
        if(appLoaded)
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
    }

    @Override
    public void onBackButtonPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }
    }
}