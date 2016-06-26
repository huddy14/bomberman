package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.BombermanPreferances;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;
import com.bomberman.game.Screen.ScreenManagment.UIFactory;

/**
 * Created by huddy on 6/22/16.
 */
public class LevelSelection extends AbstractScreen {
    private Texture textureBg;
    private Texture textureMap;
    private Texture lock;
    private Image lockImage;
    private Image background;
    private Button[] buttons = new Button[AssetsPaths.MAPS.length];

    public LevelSelection()
    {
        super();
        buildStage();

    }

    @Override
    public void buildStage() {
        lock = new Texture(AssetsPaths.LEVEL_SELECTION_LOCK);
        textureBg = new Texture(AssetsPaths.LEVEL_SELECTION_BACKGROUND);

        background = new Image(textureBg);
        background.setBounds(0,0,getWidth(),getHeight());
        background.setFillParent(true);

        addActor(background);

        float miniMapWidth = getWidth()/5;
        float miniMapHeight = getHeight()/3;
        float lockWidth = miniMapWidth/2;
        float lockHeight = miniMapHeight/2;
        float x = getWidth()/10, y = getHeight()/10;

        BombermanPreferances.getInstance();

        for(int i = 0; i<buttons.length; i++)
        {
            textureMap = new Texture(AssetsPaths.LEVEL_SELECTION_MAP[i]);

            buttons[i] = UIFactory.createButton(textureMap);
            addActor(buttons[i]);
            buttons[i].setBounds( x , y , miniMapWidth , miniMapHeight );
            int b = BombermanPreferances.getInstance().getMaxMapIndex();
            if(i>b) {

                //rysowanie klodek
                lockImage = new Image(lock);
                lockImage.setBounds(x, y, lockHeight, lockHeight);
                addActor(lockImage);
            }
            else
                buttons[i].addListener(UIFactory.createListener(ScreenEnum.GAME, i));


            x+= getWidth()*3/10;
            if((i+1)%3==0 && i!=0)
            {
                x = getWidth()/10;
                y += getHeight()*5/10;
            }
        }
    }

    @Override
    public void onBackButtonPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
