package com.bomberman.game.Controller;


import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.Constants;
import com.bomberman.game.GlobalMethods;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Interfaces.IExplosionListener;
import com.bomberman.game.Interfaces.IGameStatus;
import com.bomberman.game.Interfaces.IMovingModel;
import com.bomberman.game.Model.Bomb;
import com.bomberman.game.Model.Bomberman;
import com.bomberman.game.Model.Ghost;
import com.bomberman.game.Model.Map;
import com.bomberman.game.View.BombermanView;

import java.util.ArrayList;

/**
 * Created by Patryk on 15.05.2016.
 */
public class BombermanController implements IController,IExplosionListener {
    private Bomberman player;
    private BombermanView playerView;
    private GhostController ghostController;
    private BombController bombController;
    private Map map;
    private float elapsedTime = 0;
    private Map.CollisionDetector collisionDetector;
    private int approx;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private IGameStatus onGameStatusChangeListener;


    public BombermanController(Map map)
    {
        this.map = map;
        this.collisionDetector = map.getCollisionDetector();
        this.player = new Bomberman(new Vector2(64,64*11));;
        this.playerView = new BombermanView();
        this.approx = (int) player.getBounds().height/2;
    }

    public void setControllers(BombController bombController,GhostController ghostController)
    {
        this.bombController = bombController;
        this.ghostController = ghostController;
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

    public void update(float x, float y)
    {
        //TODO:SYF JAK CHUJ TU JEST XD
        if(player.getStatus().equals(IMovingModel.Status.DEAD))
             onGameStatusChangeListener.onGameStatusChange(IGameStatus.GameStatus.LOSE);
        //TODO: zredukowac ify jak sie da a jak nie to nie
        if(!player.getStatus().equals(IMovingModel.Status.DEAD)) {
            float oldX = player.getPosition().x;
            float oldY = player.getPosition().y;

            player.update(x, y);
            //czek if kolizja */
            if (collisionDetector.playerGhostsCollision(player.getSmallBounds(), ghostController.getGhosts())) {
                player.setStatus(IMovingModel.Status.DEAD);
                Log.w("s", "" + player.getStatus());
            }
            else if (collisionDetector.powerCollision(player.getBounds())){
                String type = map.getPowerType();
                switch (type) {
                    case Constants.BOMB_UP:
                        player.addBombCount();
                        break;
                    case Constants.FLAME_UP:
                        //TODO: NIE DZIALA
                        bombController.setRange(bombController.getRange()+1);
                        break;
                    case Constants.SPEED_UP:
                        player.setVelocity(player.getVelocity() + 1f);
                        break;
                }
                map.deletePower();
            }
            //TODO: WIN
            else if (ghostController.getGhosts().size() == 0 && collisionDetector.portalCollison(player.getBounds())) {
                Log.w("XXX", "WIN");
                onGameStatusChangeListener.onGameStatusChange(IGameStatus.GameStatus.WIN);
            }


            if (collisionDetector.playerCollision(player.getBounds())) {
                Rectangle rectangle = map.getRectangle();
                if (rectangle.getX() == 0 || rectangle.getY() == 0) {
                    player.setStatus(Bomberman.Status.IDLE);
                    player.setX(oldX);
                    player.setY(oldY);
                    player.getPosition();
                } else if (rectangle.getX() - player.getPosition().x > approx && Bomberman.Direction.DOWN == player.getDirection()) {
                    //left
                    player.update(-1, 0);
                } else if (rectangle.getX() - player.getPosition().x > approx && Bomberman.Direction.UP == player.getDirection()) {
                    //left
                    player.update(-1, 0);
                } else if (rectangle.getX() - player.getPosition().x < -approx && Bomberman.Direction.DOWN == player.getDirection()) {
                    //right
                    player.update(1, 0);
                } else if (rectangle.getX() - player.getPosition().x < -approx && Bomberman.Direction.UP == player.getDirection()) {
                    //right
                    player.update(1, 0);
                } else if (rectangle.getY() - player.getPosition().y < -approx && Bomberman.Direction.RIGHT == player.getDirection()) {
                    //up
                    player.update(0, 1);
                } else if (rectangle.getY() - player.getPosition().y < -approx && Bomberman.Direction.LEFT == player.getDirection()) {
                    //up
                    player.update(0, 1);
                } else if (rectangle.getY() - player.getPosition().y > approx - 5 && Bomberman.Direction.RIGHT == player.getDirection()) {
                    //down
                    player.update(0, -1);
                } else if (rectangle.getY() - player.getPosition().y > approx - 5 && Bomberman.Direction.LEFT == player.getDirection()) {
                    //down
                    player.update(0, -1);
                } else {
                    player.setStatus(Bomberman.Status.IDLE);
                    player.setX(oldX);
                    player.setY(oldY);
                    player.getPosition();
                }

            }
        }
    }

    @Override
    public void draw(OrthographicCamera camera) {

        if(!player.getStatus().equals(IMovingModel.Status.DEAD))
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
            playerView.setProjectionMatrix(camera.combined);
            playerView.drawBomberman(player.getDirection(), elapsedTime, player.getPosition().x, player.getPosition().y);

            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.WHITE);

            Rectangle rect = player.getSmallBounds();
            shapeRenderer.rect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());

            shapeRenderer.end();
        }
    }

    @Override
    public void onExplosion(Bomb bomb) {
        if(collisionDetector.movingModelBombCollision(player,bomb))
            player.setStatus(IMovingModel.Status.DEAD);

    }

}
