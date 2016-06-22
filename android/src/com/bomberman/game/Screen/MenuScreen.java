package com.bomberman.game.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.bomberman.game.Constants;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;
import com.bomberman.game.Screen.ScreenManagment.UIFactory;

/**
 * Created by huddy on 6/22/16.
 */
public class MenuScreen extends AbstractScreen {

    private Texture textureBg;
    private Texture textureExit;
    private Texture texturePlay;
    private SpriteBatch batch = new SpriteBatch();

    public MenuScreen()
    {
        super();
        textureBg = new Texture(Constants.BACKGROUND_MENU);
        textureExit = new Texture(Constants.STOP_BUTTON_MENU);
        texturePlay = new Texture(Constants.PLAY_BUTTON_MENU);

        buildStage();
    }

    @Override
    public void buildStage() {
       Image bg = new Image(textureBg);
        bg.setBounds(0,0,getWidth(),getHeight());
        bg.setFillParent(true);
       addActor(bg);
        float x = getWidth() ;
        float y = getHeight();
        Button btnPlay = UIFactory.createButton(texturePlay);
        //btnPlay.setPosition(getWidth()/2f,getHeight() - getHeight()/4, Align.center);
        btnPlay.setBounds(x/4,y*5/8,x/2,y/4);
        addActor(btnPlay);

        Button btnExit = UIFactory.createButton(textureExit);
        btnExit.setBounds(x/4,y/8,x/2,y/4);
        //btnExit.setPosition(getWidth()/2f,getHeight()/4,Align.center);
        addActor(btnExit);

        btnExit.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.exit();
                        return false;
                    }
                });

        btnPlay.addListener(UIFactory.createListener(ScreenEnum.LEVEL_SELECTION));

        //Log.w("H: ",""+bg.getHeight()+" W: "+bg.getWidth());
    }

    @Override
    public void onBackButtonPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }
    }


}
