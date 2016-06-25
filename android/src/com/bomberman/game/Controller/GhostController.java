package com.bomberman.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Interfaces.IExplosionListener;
import com.bomberman.game.Interfaces.IMovingModel;
import com.bomberman.game.Interfaces.IStatsChangeListener;
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

    private Bomberman player;
    private ArrayList<Ghost> ghosts = new ArrayList<>();
    private ArrayList<Ghost> ghostsToDelete = new ArrayList<>();

    private GhostView ghostView = new GhostView();
    private Map.CollisionDetector collisionDetector;
    private int directionIndex;
    private Random randomGenerator = new Random();
    private float elapsedTime = 0;
    private IStatsChangeListener statsListener;


    public GhostController(Map map)
    {
        this.collisionDetector = map.getCollisionDetector();
    }

    public void setPlayer(Bomberman player)
    {
        this.player = player;
    }

    public ArrayList<Ghost> getGhosts()
    {
        return this.ghosts;
    }

    public void addGhost(Ghost ghost)
    {
        this.ghosts.add(ghost);
    }

    @Override
    public void draw(OrthographicCamera camera)
    {
        if(ghostsToDelete.size()>0)
        {
            statsListener.onScoreChange(ghostsToDelete.size() * 50);
            ghosts.removeAll(ghostsToDelete);
            ghostsToDelete.clear();
        }
        for (Ghost ghost : ghosts) {
            if (!ghost.getStatus().equals(IMovingModel.Status.DEAD)) {
                update(ghost);
                elapsedTime += Gdx.graphics.getDeltaTime();
                ghostView.setProjectionMatrix(camera.combined);
                ghostView.drawGhost(ghost.getDirection(), elapsedTime, ghost.getPosition().x, ghost.getPosition().y);
            }
        }
    }
    public void setStatsListener(IStatsChangeListener statsListener)
    {
        this.statsListener = statsListener;
    }
    public void update(Ghost ghost)
    {
        float oldX = ghost.getPosition().x, oldY = ghost.getPosition().y;
        ghost.move(ghost.getDirection());
        //sprawdzamy czy nastepuje kolizja, jesli tak przypisujemy potworkowi stare wspolrzedne i losowo zmieniamy kierunek
        if (collisionDetector.terrainCollision(ghost.getBounds())) {
            ghost.update(oldX, oldY);
            directionIndex = randomGenerator.nextInt(IMovingModel.Direction.values().length);
            ghost.setDirection(IMovingModel.Direction.values()[directionIndex]);
        }
    }

    @Override
    public void onExplosion(Bomb bomb) {
        //sprawdzenie czy eksplozja zabija ktoregos z potworkow
        for (Ghost ghost : ghosts) {
            if (collisionDetector.movingModelExplosionBoundsCollision(ghost, bomb)) {
                //jesli tak dodajemy go do tablicy potworkow do usuniecia i przy nastepnym wywolaniu draw usuwamy je z tablicy potworkow
                ghost.setStatus(IMovingModel.Status.DEAD);
                ghostsToDelete.add(ghost);
            }
        }
    }
}
