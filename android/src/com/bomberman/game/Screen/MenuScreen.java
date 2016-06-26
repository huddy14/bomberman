package com.bomberman.game.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Audio.AudioManager;
import com.bomberman.game.BombermanPreferances;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.UIFactory;

/**
 * Created by huddy on 6/22/16.
 */
public class MenuScreen extends AbstractScreen {

    private Texture textureBg;
    private Texture textureExit;
    private Texture texturePlay;
    private Texture textureScore;
    private Image imageAudio;

    public MenuScreen()
    {
        super();
        textureBg = new Texture(AssetsPaths.BACKGROUND_MENU);
        textureExit = new Texture(AssetsPaths.STOP_BUTTON_MENU);
        texturePlay = new Texture(AssetsPaths.PLAY_BUTTON_MENU);
        textureScore = new Texture(AssetsPaths.SCORES_BUTTON_MENU);

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

        Image btnBg = new Image(new Texture(AssetsPaths.BUTTON_BACKGROUND_MENU));
        btnBg.setBounds(x/4,0,x/2,y);
        addActor(btnBg);

        Button btnPlay = UIFactory.createButton(texturePlay);
        btnPlay.setBounds(x/4,3*y/4,x/2,y/4);
        addActor(btnPlay);

        Button btnExit = UIFactory.createButton(textureExit);
        btnExit.setBounds(x/4,0,x/2,y/4);
        addActor(btnExit);

        Button btnHighscore = UIFactory.createButton(textureScore);
        btnHighscore.setBounds(x/4,y*3/8,x/2,y/4);
        addActor(btnHighscore);


        final TextureRegion audioON = new TextureRegion(new Texture("Audio/buttonON.png"));
        final TextureRegion audioOFF = new TextureRegion(new Texture("Audio/buttonOFF.png"));

        final AudioManager audioManager =  AudioManager.getInstance();
        imageAudio = new Image();
        imageAudio.setDrawable(new TextureRegionDrawable(audioManager.isSoundEnabled() ? audioON : audioOFF ));
        imageAudio.setBounds(getWidth()-getHeight()/5,0,getHeight()/5,getHeight()/5);

        addActor(imageAudio);

        imageAudio.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        AudioManager.getInstance().setSoundEnabled();
                        imageAudio.setDrawable(new TextureRegionDrawable(audioManager.isSoundEnabled() ? audioON : audioOFF ));
                        return false;
                    }
                });

        btnExit.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.exit();
                        return false;
                    }
                });

        btnPlay.addListener(UIFactory.createListener(ScreenEnum.LEVEL_SELECTION));

        btnHighscore.addListener(UIFactory.createListener(ScreenEnum.HIGHSCORE));
    }


    @Override
    public void onBackButtonPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }
    }


}
