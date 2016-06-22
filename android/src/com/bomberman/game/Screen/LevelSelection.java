package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.bomberman.game.Constants;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.UIFactory;

/**
 * Created by huddy on 6/22/16.
 */
public class LevelSelection extends Stage implements Screen {
    private Texture textureBg;
    private Texture textureExit;
    private Texture textureMap;
    private SpriteBatch batch = new SpriteBatch();
    private Button[] buttons = new Button[12];
//TODO: stworzyc tablice tekstur w constants, gdzie przechowywane będą obrazki dla kazdej mapy i w pozmieniac w buildStage
    public LevelSelection()
    {
        super();
        buildStage();
        Gdx.input.setInputProcessor(this);

    }
    @Override
    public void show() {

    }

    private void buildStage()
    {
        textureMap = new Texture(Constants.LEVEL_SELECTION_MAP1);
        float x = getWidth()/10, y = getHeight()/10;
        for(int i = 0, j =1; i<buttons.length; i++)
        {
            buttons[i] = UIFactory.createButton(textureMap);
            addActor(buttons[i]);
            buttons[i].setBounds(x,y,getWidth()/5,getHeight()/5);
            buttons[i].addListener(UIFactory.createListener(ScreenEnum.GAME,1));
            x+= getWidth()*3/10;
            if(i%3==0 && i!=0)
            {
                x = getWidth()/10;
                y += getHeight()*3/10;
            }
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.act(delta);
        super.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
