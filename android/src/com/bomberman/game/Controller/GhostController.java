package com.bomberman.game.Controller;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Interfaces.IExplosionListener;
import com.bomberman.game.Interfaces.IMovingModel;
import com.bomberman.game.Model.Bomb;
import com.bomberman.game.Model.Bomberman;
import com.bomberman.game.Model.Ghost;
import com.bomberman.game.Model.Map;
import com.bomberman.game.View.GhostView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by huddy on 6/20/16.
 */
public class GhostController implements IController,IExplosionListener {

    private Map map;
    private Bomberman player;
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private Ghost ghost = new Ghost(new Vector2(64,64*11));
    private GhostView ghostView = new GhostView();
    private Map.CollisionDetector collisionDetector;
    private BombController bombController;
    private int directionIndex;
    private Random randomGenerator = new Random();
    private float elapsedTime = 0;


    public GhostController(Bomberman player, Map map)
    {
        this.map = map;
        this.player = player;
        this.collisionDetector = map.getCollisionDetector();
        //roboczo

    }

    public Ghost getGhost()
    {
        return this.ghost;
    }


    @Override
    public void draw(OrthographicCamera camera)
    {
        if(!ghost.getStatus().equals(IMovingModel.Status.DEAD)) {
            update();
            elapsedTime += Gdx.graphics.getDeltaTime();
            ghostView.setProjectionMatrix(camera.combined);
            ghostView.drawGhost(ghost.getDirection(), elapsedTime, ghost.getPosition().x, ghost.getPosition().y);
        }
    }

    public void update()
    {
        float oldX = ghost.getPosition().x, oldY = ghost.getPosition().y;
        ghost.move(ghost.getDirection());

        if (collisionDetector.playerCollision(ghost.getBounds())) {
            ghost.update(oldX, oldY);
            directionIndex = randomGenerator.nextInt(IMovingModel.Direction.values().length);
            ghost.setDirection(IMovingModel.Direction.values()[directionIndex]);
        }

    }

    @Override
    public void onExplosion(Bomb bomb) {
        if(collisionDetector.movingModelBombCollision(ghost,bomb)) {
            //Log.w("potwor se wybuch",""+player.getStatus());
            ghost.setStatus(IMovingModel.Status.DEAD);
        }
    }
}