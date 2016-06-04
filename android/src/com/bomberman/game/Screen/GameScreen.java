package com.bomberman.game.Screen;

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
import com.bomberman.game.Constants;
import com.bomberman.game.Model.*;
import com.bomberman.game.View.*;
import com.bomberman.game.Controller.*;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Patryk on 16.05.2016.
 */

public class GameScreen implements Screen {
    private Stage stage;
    private float elapsedTime;
    private BombermanView bombermanView;
    private BombermanController bombermanController;
    private Touchpad touchpad;
    private Button bombButton;
    private MapCamera camera;
    private Bomberman player;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private BombController bombController;
    float time =0;
    float x,y;
    Bomb bomb;
    //BombView bv = new BombView();
    boolean bombPlanted = false;
    private ShapeRenderer shapeRenderer;


    private int width, height;

    @Override
    public void show() {
        init();
    }

    private void init()
    {
        //map = new Map();
        tiledMap = new TmxMapLoader().load(Constants.MAP_1);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //player = new Bomberman(new Vector2(80,700));
        player = new Bomberman(new Vector2(64,64*10));
        bombermanView = new BombermanView();

        camera = new MapCamera(tiledMap.getProperties(),player);
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        bombermanController = new BombermanController(player,tiledMap);

        bombController = new BombController(player,bombermanController,tiledMap,camera);

        touchpad = (new TouchpadView(10,new Touchpad.TouchpadStyle()));
        bombButton = (new BombButton());
        bombButton.addListener(bombController);

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),bombermanView);
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


        bombermanController.update(touchpad.getKnobPercentX(),touchpad.getKnobPercentY());

        //bv.setProjectionMatrix(camera.combined);
        //ustawiamy projectionMatrix zeby wspolrzedne gracza byly dobrze renderowane na naszej mapie
        bombermanView.setProjectionMatrix(camera.combined);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        bombermanView.begin();
        elapsedTime += Gdx.graphics.getDeltaTime();

        bombermanView.drawBomberman(player.getDirection(),elapsedTime,player.getPosition().x,player.getPosition().y);
        bombermanView.end();
        //bv.setProjectionMatrix(camera.combined);
        bombController.drawBomb();
        stage.act(elapsedTime);
        stage.draw();






    }


}
