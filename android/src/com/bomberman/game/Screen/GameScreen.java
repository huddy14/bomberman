package com.bomberman.game.Screen;

import android.text.method.Touch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.bomberman.game.Model.*;
import com.bomberman.game.View.*;
import com.bomberman.game.Controller.*;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Patryk on 16.05.2016.
 */

public class GameScreen implements Screen {
    private Board board;
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
    private Camera camera;
    private Player player;

    private int width, height;

    @Override
    public void show() {
        board = new Board();
        player = new Player(new Vector2(10,10));
        //boardDraw = new BoardDraw(board);
       // controller = new BombermanController(board);
        textureAtlas = new TextureAtlas("Bomberman/Front/BombermanFront.pack");
        animation = new Animation(1/10f,textureAtlas.getRegions());
        batch = new SpriteBatch();
        touchpad = (new TouchpadView()).getTouchpad();
        bombButton = (new BombButton()).getButton();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()),batch);
        stage.addActor(touchpad);
        stage.addActor(bombButton);
        camera = new OrthographicCamera();


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
        camera.update();
        player.update(touchpad.getKnobPercentX(),touchpad.getKnobPercentY());
        //boardDraw.render();
        batch.begin();
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(bombermanView.getAnimation(player.getDirection()).getKeyFrame(elapsedTime,true),player.getPosition().x,player.getPosition().y);
        batch.end();
        stage.act(elapsedTime);
        stage.draw();
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
