package com.bomberman.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
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
import com.bomberman.game.View.*;
import com.bomberman.game.Controller.*;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Patryk on 16.05.2016.
 */

public class GameScreen extends ChangeListener implements Screen, IGameStatus {
    private Stage stage;
    private float elapsedTime;
//    private BombermanView bombermanView;
//    private BombermanController bombermanController;
//    private GhostController ghostController;
    private Controllers controller;
    private Touchpad touchpad;
    private Button bombButton;
    private MapCamera camera;
//    private Bomberman player;
    private TiledMap tiledMap;
    private Map map;
    private TiledMapRenderer tiledMapRenderer;
    private GameStatus gameStatus = GameStatus.PLAYING;
    private BombGame game;

//    private BombController bombController;


    public GameScreen(BombGame game )
    {
        this.game = game;
    }
    private int width, height;

    @Override
    public void show() {
        init();
    }

    private void init()
    {
        tiledMap = new TmxMapLoader().load(Constants.MAP_1);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        map = new Map(tiledMap);

        //player = new Bomberman(new Vector2(80,700));
//        player = new Bomberman(new Vector2(64,64*11));
//        bombermanView = new BombermanView();
        controller = new Controllers(map);
        //ustawiamy te klase jako sluchacza zmiany stanu gry
        controller.bomberman().setOnGameStatusChangeListener(this);

        camera = new MapCamera(tiledMap.getProperties(),controller.bomberman().getPlayer());
        if(camera.getMapHeight() > Gdx.graphics.getHeight() || camera.getMapWidth() > Gdx.graphics.getWidth())
            camera.setToOrtho(false,camera.getMapWidth(), camera.getMapHeight());
        else
            camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

//        ghostController = new GhostController(player, map);
//
//        ghostController.addGhost(new Ghost(new Vector2(5 * Constants.TILE_SIZE, 5*Constants.TILE_SIZE)));
//        ghostController.addGhost(new Ghost(new Vector2(11 * Constants.TILE_SIZE, 9*Constants.TILE_SIZE)));
//        ghostController.addGhost(new Ghost(new Vector2(11 * Constants.TILE_SIZE, 3*Constants.TILE_SIZE)));
//
//        bombermanController = new BombermanController(player,bombermanView,ghostController,map);
//
//        bombController = new BombController(player,bombermanController,ghostController,map);

        touchpad = (new TouchpadView(10,new Touchpad.TouchpadStyle()));
        bombButton = (new BombButton());
        bombButton.addListener(this);

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),controller.bomberman().getView());
        stage.addActor(touchpad);
        stage.addActor(bombButton);



        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void resize(int width, int height) {
        //boardDraw.setSize(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();

        //wyświetlanie gracza
        controller.update(touchpad.getKnobPercentX(),touchpad.getKnobPercentY());
        //bv.setProjectionMatrix(camera.combined);
        //ustawiamy projectionMatrix zeby wspolrzedne gracza byly dobrze renderowane na naszej mapie
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        controller.draw(camera);
        stage.act(elapsedTime);
        stage.draw();






    }


    @Override
    public void changed(ChangeEvent event, Actor actor) {
        if(controller.bomberman().getPlayer().canPlant()) {
            controller.bomb().addBomb();
        }
    }

    @Override
    public void onGameStatusChange(GameStatus gameStatus) {
        if(gameStatus.equals(GameStatus.LOSE))
        {
            game.setScreen(new SplashScreen(game));
            this.dispose();
        }

    }
}
