package com.bomberman.game.Controller;


import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Audio.AudioManager;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Interfaces.IExplosionListener;
import com.bomberman.game.Interfaces.IGameStatus;
import com.bomberman.game.Interfaces.IMovingModel;
import com.bomberman.game.Interfaces.IStatsChangeListener;
import com.bomberman.game.Model.Bomb;
import com.bomberman.game.Model.Bomberman;
import com.bomberman.game.Model.Map;
import com.bomberman.game.View.BombermanView;

/**
 * Created by Patryk on 15.05.2016.
 * Class created to control Bomberman aka player
 */
public class BombermanController implements IController,IExplosionListener {
    private Bomberman player;
    private BombermanView playerView;
    private GhostController ghostController;
    private BombController bombController;
    private Map map;
    private float elapsedTime = 0;
    private Map.CollisionDetector collisionDetector;
    private float approx;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private IGameStatus onGameStatusChangeListener;
    private IStatsChangeListener statsListener;
    private int HEIGHT,WIDTH;


    public BombermanController(Map map)
    {
        this.map = map;
        this.collisionDetector = map.getCollisionDetector();
        this.player = new Bomberman(new Vector2(64,64*11));;
        this.HEIGHT = map.MAP_HEIGHT();
        this.WIDTH = map.MAP_WIDTH();
        this.player.setMapBounds(WIDTH,HEIGHT);
        this.playerView = new BombermanView();
        this.approx = (int) Constants.TILE_SIZE/1.5f;
    }

    public void setControllers(BombController bombController,GhostController ghostController)
    {
        this.bombController = bombController;
        this.ghostController = ghostController;
    }



    public void setStatsListener(IStatsChangeListener statsListener)
    {
        this.statsListener = statsListener;
    }

    public void setOnGameStatusChangeListener(IGameStatus listener)
    {
        this.onGameStatusChangeListener = listener;
    }

    public Bomberman getPlayer()
    {
        return this.player;
    }

    public BombermanView getView()
    {
        return this.playerView;
    }

    /**
     * Here we are checking if player didn't die, didn't lose, didn't win if not we are updating it's position
     * @param x player x position
     * @param y player y position
     */
    public void update(float x, float y)
    {
        //Sprawdzenie czy player nie zginal

        if(player.getStatus().equals(IMovingModel.Status.DEAD)) {
            player.deleteLife();
            statsListener.onLifeCountChange(player.getLifes());
            AudioManager.getInstance().onPlayerDeath();

            //jelis zginal ale mial jeszcze kilka zyc to ressetujemy jego pozycje i zmieniamy status

            if (player.getLifes() > 0) {
                player.setX(Constants.TILE_SIZE);
                player.setY(Constants.TILE_SIZE * (HEIGHT - 2));
                player.setStatus(IMovingModel.Status.MOVE);
            } else {

                //w przeciwnym wypadku gra jest przegrana

                onGameStatusChangeListener.onGameStatusChange(IGameStatus.GameStatus.LOSE);
            }
        }

        //jesli gracz zyje wykonujemy update jego pozycji z uwzglednieniem kolizji

        else
        {
            float oldX = player.getPosition().x;
            float oldY = player.getPosition().y;

            player.update(x, y);
            if (ghostController.getGhosts().size() == 0 && collisionDetector.portalCollison(player.getBounds())) {
                onGameStatusChangeListener.onGameStatusChange(IGameStatus.GameStatus.WIN);
                AudioManager.getInstance().onPlayerVicotry();
            }
            else if (collisionDetector.playerGhostsCollision(player.getSmallBounds(), ghostController.getGhosts())) {
                player.setStatus(IMovingModel.Status.DEAD);
                Log.w("s", "" + player.getStatus());
            }
            else if (collisionDetector.powerCollision(player.getBounds()))
                setPowerUp();


            if (collisionDetector.terrainCollision(player.getBounds()))
                smoothPlayerMovement(oldX, oldY);

        }
    }

    /**
     * Adding powerups to Bomberman instance
     */
    private void setPowerUp()
    {
        String type = map.getPowerType();
        switch (type) {
            case AssetsPaths.BOMB_UP:
                player.addBombCount();
                statsListener.onBombCountChange(player.getBombCount());
                break;
            case AssetsPaths.FLAME_UP:
                bombController.setRange(bombController.getRange()+1);
                statsListener.onBombRangeChange(bombController.getRange());
                break;
            case AssetsPaths.SPEED_UP:
                player.setVelocity(player.getVelocity() + 0.5f);
                statsListener.onSpeedChange(player.getVelocity());
                break;
        }
        map.deletePower();
        AudioManager.getInstance().onBonusTake();
        statsListener.onScoreChange(20);

    }

    /**
     * Easing player movement since its only allowed to move in grid
     * @param oldX player old x position
     * @param oldY player old y position
     */
    private void smoothPlayerMovement(float oldX, float oldY)
    {
        Rectangle rectangle = map.getRectangle();
        if (rectangle.getX() == 0 || rectangle.getY() == 0) {
            player.setStatus(Bomberman.Status.IDLE);
            player.setX(oldX);
            player.setY(oldY);
            player.getPosition();
        } else if (rectangle.getX() - player.getPosition().x > approx && Bomberman.Direction.DOWN == player.getDirection()) {
            player.moveOb(oldX,oldY,IMovingModel.Direction.LEFT);
        } else if (rectangle.getX() - player.getPosition().x > approx && Bomberman.Direction.UP == player.getDirection()) {
            player.moveOb(oldX,oldY,IMovingModel.Direction.LEFT);
        } else if (rectangle.getX() - player.getPosition().x < -approx && Bomberman.Direction.DOWN == player.getDirection()) {
            player.moveOb(oldX,oldY,IMovingModel.Direction.RIGHT);
        } else if (rectangle.getX() - player.getPosition().x < -approx && Bomberman.Direction.UP == player.getDirection()) {
            player.moveOb(oldX,oldY,IMovingModel.Direction.RIGHT);
        } else if (rectangle.getY() - player.getPosition().y < -approx && Bomberman.Direction.RIGHT == player.getDirection()) {
            player.moveOb(oldX,oldY,IMovingModel.Direction.UP);
        } else if (rectangle.getY() - player.getPosition().y < -approx && Bomberman.Direction.LEFT == player.getDirection()) {
            player.moveOb(oldX,oldY,IMovingModel.Direction.UP);
        } else if (rectangle.getY() - player.getPosition().y > approx - 5 && Bomberman.Direction.RIGHT == player.getDirection()) {
            player.moveOb(oldX,oldY,IMovingModel.Direction.DOWN);
        } else if (rectangle.getY() - player.getPosition().y > approx - 5 && Bomberman.Direction.LEFT == player.getDirection()) {
            player.moveOb(oldX,oldY,IMovingModel.Direction.DOWN);
        } else {
            player.setStatus(Bomberman.Status.IDLE);
            player.setX(oldX);
            player.setY(oldY);
            player.getPosition();
        }


    }

    @Override
    public void draw(OrthographicCamera camera) {

        if(!player.getStatus().equals(IMovingModel.Status.DEAD))
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
            playerView.setProjectionMatrix(camera.combined);
            playerView.drawBomberman(player.getDirection(), elapsedTime, player.getPosition().x, player.getPosition().y);

//            shapeRenderer.setProjectionMatrix(camera.combined);
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//            shapeRenderer.setColor(Color.WHITE);
//
//            Circle rect = player.getCollisionCircle();
//            shapeRenderer.circle(rect.x,rect.y,rect.radius);
//
//            shapeRenderer.end();
        }
    }

    @Override
    public void onExplosion(Bomb bomb) {
        AudioManager.getInstance().onBombEplosion();

        //sprawdzenie czy wybuch bomby nie zabil gracza

        if(collisionDetector.movingModelExplosionBoundsCollision(player,bomb))
            player.setStatus(IMovingModel.Status.DEAD);

    }

}