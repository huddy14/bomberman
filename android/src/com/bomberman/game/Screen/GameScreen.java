package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.bomberman.game.Model.*;
import com.bomberman.game.View.*;
import com.bomberman.game.Controller.*;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Patryk on 16.05.2016.
 */

public class GameScreen implements Screen {
    private Map map;
    private BoardDraw boardDraw;
    private BombermanController controller;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private Stage stage;
    private float elapsedTime;
    private SpriteBatch batch;
    private BombermanView bombermanView;
    private Touchpad touchpad;
    private Button bombButton;
    private MapCamera camera;
    private Player player;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    boolean collision = false;
    private ArrayList<Rectangle> rectArray = new ArrayList<>();
    MapObjects coll;


    private int width, height;

    @Override
    public void show() {
        map = new Map();
        tiledMap = new TmxMapLoader().load("Maps/map1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        player = new Player(new Vector2(100,200));
        //boardDraw = new BoardDraw(map);
       // controller = new BombermanController(map);
        textureAtlas = new TextureAtlas("Bomberman/Front/BombermanFront.pack");
        animation = new Animation(1/10f,textureAtlas.getRegions());
        batch = new SpriteBatch();
        touchpad = (new TouchpadView()).getTouchpad();
        bombButton = (new BombButton()).getButton();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),batch);
        stage.addActor(touchpad);
        stage.addActor(bombButton);
        camera = new MapCamera(tiledMap.getProperties(),player);
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        float a =Gdx.graphics.getWidth();
        float b = Gdx.graphics.getHeight();

        camera.update();
        coll = tiledMap.getLayers().get(1).getObjects();
        for(int i = 0 ; i < coll.getCount();i++)
        {
            RectangleMapObject obj = (RectangleMapObject)coll.get(i);

            rectArray.add(obj.getRectangle());
        }

        bombermanView = new BombermanView(player);
        Gdx.input.setInputProcessor(stage);

        //Gdx.input.setInputProcessor(this);
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


    //TODO: zaimplementowac metodę obliczającą kąt z pozycji touchpad knopa i na tej podstawie ustalić direction w którym ma się poruszać bomberman


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //camera.position.set(camera.position.x-2,camera.position.y-2,0);

        float oldX = player.getPosition().x;
        float oldY = player.getPosition().y;
        player.setCollision(false);
        player.update(touchpad.getKnobPercentX(),touchpad.getKnobPercentY());
        int objectLayerId = 1;
//        TiledMapTileLayer collisionLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0);
//        MapObjects objects = collisionLayer.getObjects();
        for(int i = 0 ; i < rectArray.size();i++)
        {


            if(Intersector.overlaps(rectArray.get(i),player.getRectangle()))
            {
                player.setCollision(true);
                player.setX(oldX);
                player.setY(oldY);
                player.getPosition();
                break;

            }

        }


        //ustawiamy projectionMatrix zeby wspolrzedne gracza byly dobrze renderowane na naszej mapie
        player.setProjectionMatrix(camera.combined);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        //player.update(touchpad.getKnobPercentX(),touchpad.getKnobPercentY());
        //boardDraw.render();
        player.begin();
        elapsedTime += Gdx.graphics.getDeltaTime();

        player.draw(bombermanView.getAnimation(player.getDirection()).getKeyFrame(elapsedTime,true),player.getPosition().x,player.getPosition().y);
        player.end();
        stage.act(elapsedTime);
        stage.draw();
        camera.update();

    }

//    public boolean keyUp(int keycode) {
//        return true;
//    }
//
//    private void ChangeNavigation(int x, int y){
//        controller.resetWay();
//        if(height-y >  controller.player.getPosition().y * boardDraw.ppuY)
//            controller.upPressed();
//
//        if(height-y <  controller.player.getPosition().y * boardDraw.ppuY)
//            controller.downPressed();
//
//        if ( x< controller.player.getPosition().x * boardDraw.ppuX)
//            controller.leftPressed();
//
//        if (x> (controller.player.getPosition().x +Player.SIZE)* boardDraw.ppuX)
//            controller.rightPressed();
//    }


}
