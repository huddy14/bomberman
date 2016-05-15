package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.bomberman.game.Model.*;
import com.bomberman.game.View.*;
import com.bomberman.game.Controller.*;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Patryk on 16.05.2016.
 */

public class GameScreen implements Screen, InputProcessor {
    private Board board;
    private BoardDraw boardDraw;
    private BoardController	controller;

    private int width, height;

    @Override
    public void show() {
        board = new Board();
        boardDraw = new BoardDraw(board);
        controller = new BoardController(board);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        ChangeNavigation(x,y);
        return false;
    }


    public boolean touchMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public void resize(int width, int height) {
        boardDraw.setSize(width, height);
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
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        controller.update(delta);
        boardDraw.render();
    }
    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    private void ChangeNavigation(int x, int y){
        controller.resetWay();
        if(height-y >  controller.player.getPosition().y * boardDraw.ppuY)
            controller.upPressed();

        if(height-y <  controller.player.getPosition().y * boardDraw.ppuY)
            controller.downPressed();

        if ( x< controller.player.getPosition().x * boardDraw.ppuX)
            controller.leftPressed();

        if (x> (controller.player.getPosition().x +Player.SIZE)* boardDraw.ppuX)
            controller.rightPressed();
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (!Gdx.app.getType().equals(ApplicationType.Android))
            return false;
        ChangeNavigation(x,y);
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (!Gdx.app.getType().equals(ApplicationType.Android))
            return false;
        controller.resetWay();
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
