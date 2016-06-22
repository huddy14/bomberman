package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomberman.game.BombermanPreferances;
import com.bomberman.game.Constants;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;
import com.bomberman.game.Screen.ScreenManagment.UIFactory;

import java.util.ArrayList;

/**
 * Created by huddy on 6/22/16.
 */
public class LevelSelection extends AbstractScreen {
    private Texture textureBg;
    private Texture textureExit;
    private Texture textureMap;
    private Texture lock;
    private Image lockImage;
    private Button[] buttons = new Button[12];
//libTODO: stworzyc tablice tekstur w constants, gdzie przechowywane będą obrazki dla kazdej mapy i w pozmieniac w buildStage
    public LevelSelection()
    {
        super();
        buildStage();

    }

    @Override
    public void buildStage() {
        textureMap = new Texture(Constants.LEVEL_SELECTION_MAP1);
        lock = new Texture(Constants.LEVEL_SELECTION_LOCK);

        float miniMapWidth = getWidth()/5;
        float miniMapHeight = getHeight()/5;
        float lockWidth = miniMapWidth/2;
        float lockHeight = miniMapHeight/2;
        //TODO: warunek jesli mapa odblokowana to tworzymy listener i nie rysujemy klodki
        //TODO: musi wczytac z preferencji ile map jest odblokowanych
        float x = getWidth()/10, y = getHeight()/10;
        BombermanPreferances.getInstance();
        for(int i = 0; i<buttons.length; i++)
        {
            buttons[i] = UIFactory.createButton(textureMap);
            addActor(buttons[i]);
            buttons[i].setBounds( x , y , miniMapWidth , miniMapHeight );
            int b = BombermanPreferances.getInstance().getMaxMapIndex();
            if(i>b-1) {

                //rysowanie klodek
                lockImage = new Image(lock);
                lockImage.setBounds(x, y, lockHeight, lockHeight);
                addActor(lockImage);
                //locks.add(lockImage);
            }
            else
                buttons[i].addListener(UIFactory.createListener(ScreenEnum.GAME, i+1));


            x+= getWidth()*3/10;
            if(i%3==0 && i!=0)
            {
                x = getWidth()/10;
                y += getHeight()*3/10;
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
