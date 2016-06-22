package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.bomberman.game.BombGame;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IGameStatus;
import com.bomberman.game.Model.*;
import com.bomberman.game.Screen.CameraManagment.MapCamera;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;
import com.bomberman.game.View.*;
import com.bomberman.game.Controller.*;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Patryk on 16.05.2016.
 */

public class GameScreen extends AbstractScreen implements Screen, IGameStatus {
    private Controllers controller;
    private Touchpad touchpad;
    private Button bombButton;
    private MapCamera camera;
    private TiledMap tiledMap;
    private Map map;
    private TiledMapRenderer tiledMapRenderer;


//TODO: implementacja pozycji gracza i ghostow przy wczytywaniu mapy
    public GameScreen(int level )
    {
        super();
        buildStage();
    }

    @Override
    public void buildStage() {
        touchpad = (new TouchpadView(10,new Touchpad.TouchpadStyle()));
        bombButton = (new BombButton());
        bombButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(controller.bomberman().getPlayer().canPlant()) {
                    controller.bomb().addBomb();
                }
            }
        });

        this.addActor(touchpad);
        this.addActor(bombButton);

    }

    @Override
    public void show() {
        init();
    }

    private void init()
    {
        tiledMap = new TmxMapLoader().load(Constants.MAP_1);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        map = new Map(tiledMap);

        controller = Controllers.getInstance();
        controller.initializeControllers(map);

        //ustawiamy te klase jako sluchacza zmiany stanu gry

        controller.bomberman().setOnGameStatusChangeListener(this);

        camera = new MapCamera(tiledMap.getProperties(),controller.bomberman().getPlayer());
        if(camera.getMapWidth() < Gdx.graphics.getWidth())
            camera.setToOrtho(false,camera.getMapWidth() ,Gdx.graphics.getHeight());
        else
            camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    }



    @Override
    public void render(float delta) {
        super.render(delta);

        camera.update();

        controller.update(touchpad.getKnobPercentX(),touchpad.getKnobPercentY());

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        controller.draw(camera);
        this.act(delta);
        this.draw();
    }


    @Override
    public void onGameStatusChange(GameStatus gameStatus) {
        if(gameStatus.equals(GameStatus.LOSE))
        {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
        if(gameStatus.equals(GameStatus.WIN))
            ScreenManager.getInstance().showScreen(ScreenEnum.LEVEL_SELECTION);

    }
}
