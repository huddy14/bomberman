package com.bomberman.game.Controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.Constants;
import com.bomberman.game.GlobalMethods;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Model.Bomb;
import com.bomberman.game.Model.Bomberman;
import com.bomberman.game.Model.Map;
import com.bomberman.game.View.BombermanView;

import java.util.ArrayList;

/**
 * Created by Patryk on 15.05.2016.
 */
public class BombermanController implements IController {
    private Bomberman player;
    private BombermanView playerView;
    private Map map;
    private ArrayList<Rectangle> collisionElements = new ArrayList<>();
    private float elapsedTime = 0;
    private Map.CollisionDetector collisionDetector;


    public BombermanController(Bomberman player,BombermanView playerView, Map map)
    {
        this.map = map;
        this.collisionDetector = map.getCollisionDetector();
        this.player = player;
        this.playerView = playerView;
        //collisionElements = map.getCollisionElements();
        collisionElements.addAll(map.getSolidElements());
        collisionElements.addAll(map.getExplodableElements());
    }

//    private void getCollisionLayers()
//    {
//        collisionElements.clone(map.getExplodableElements());
//        collisionElements.addAll(map.getSolidElements());
//
//    }

    public void update(float x, float y)
    {
        float oldX = player.getPosition().x;
        float oldY = player.getPosition().y;

        player.update(x,y);
        //czek if kolizja */
        if(collisionDetector.playerCollision(player.getRectangle()))
        {
            player.setStatus(Bomberman.Status.IDLE);
            player.setX(oldX);
            player.setY(oldY);
            player.getPosition();
        }

    }

    @Override
    public void draw(OrthographicCamera camera) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        playerView.setProjectionMatrix(camera.combined);
        playerView.drawBomberman(player.getDirection(),elapsedTime,player.getPosition().x,player.getPosition().y);
    }
}
