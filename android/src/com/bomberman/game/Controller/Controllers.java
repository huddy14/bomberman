package com.bomberman.game.Controller;

import android.util.Log;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.BombermanPreferances;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Interfaces.IStatsChangeListener;
import com.bomberman.game.Model.Bomberman;
import com.bomberman.game.Model.Ghost;
import com.bomberman.game.Model.Map;

/**
 * Created by huddy on 6/21/16.
 */
public class Controllers implements IController{
    private static Controllers instance;
    private BombController bombController;
    private BombermanController bombermanController;
    private GhostController ghostController;

    private Controllers() { super(); }

    public static Controllers getInstance()
    {
        if(instance==null)
            instance = new Controllers();
        return instance;
    }

    /**
     * Initializing all controllers for specified map
     * @param map loaded map
     */
    //TODO: dla kazdej mapy beda inne wsporzedne playera i ghostow, tez stworzyc tablice gdzie bedziemy to trzymac
    public void initializeControllers(Map map) {
        //inicjalizujemy kontrollery

        bombController = new BombController(map);
        bombermanController = new BombermanController(map);
        ghostController = new GhostController(map);

        //dodajemy do kontrollerów obiekty umożliwiająca komunikacje między nimi

        bombController.setPlayer(bombermanController.getPlayer());
        bombController.addOnExplosionListener(bombermanController);
        bombController.addOnExplosionListener(ghostController);

        bombermanController.setControllers(bombController, ghostController);

        ghostController.setPlayer(bombermanController.getPlayer());

        //ładujemy zapisane informacje o graczu ilosc bomb, zasieg etc..
        loadPreferances();
    }

    public void initializePosition(int level){
        switch (level){
            case 0:
                ghostController.addGhost(new Ghost(new Vector2(5 * Constants.TILE_SIZE, 5* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(11 * Constants.TILE_SIZE, 9* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(11 * Constants.TILE_SIZE, 3* Constants.TILE_SIZE)));
                break;
            case 1:
                ghostController.addGhost(new Ghost(new Vector2(3 * Constants.TILE_SIZE, 9* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(9 * Constants.TILE_SIZE, 4* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(9 * Constants.TILE_SIZE, 7* Constants.TILE_SIZE)));
                break;
            case 2:
                ghostController.addGhost(new Ghost(new Vector2(5 * Constants.TILE_SIZE, 1* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(5 * Constants.TILE_SIZE, 3* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(11 * Constants.TILE_SIZE, 5* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(9 * Constants.TILE_SIZE, 5* Constants.TILE_SIZE)));
                break;
            case 3:
                ghostController.addGhost(new Ghost(new Vector2(5 * Constants.TILE_SIZE, 13* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(5 * Constants.TILE_SIZE, 3* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(14 * Constants.TILE_SIZE, 3* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(14 * Constants.TILE_SIZE, 11* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(6 * Constants.TILE_SIZE, 13* Constants.TILE_SIZE)));
                break;
            case 4:
                ghostController.addGhost(new Ghost(new Vector2(1 * Constants.TILE_SIZE, 1* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(3 * Constants.TILE_SIZE, 5* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(5 * Constants.TILE_SIZE, 3* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(12 * Constants.TILE_SIZE, 11* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(14 * Constants.TILE_SIZE, 11* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(12 * Constants.TILE_SIZE, 7* Constants.TILE_SIZE)));
                break;
            case 5:
                ghostController.addGhost(new Ghost(new Vector2(3 * Constants.TILE_SIZE, 3* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(7 * Constants.TILE_SIZE, 5* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(16 * Constants.TILE_SIZE, 3* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(12 * Constants.TILE_SIZE, 3* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(14 * Constants.TILE_SIZE, 11* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(4 * Constants.TILE_SIZE, 15* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(8 * Constants.TILE_SIZE, 11* Constants.TILE_SIZE)));
                ghostController.addGhost(new Ghost(new Vector2(10 * Constants.TILE_SIZE, 15* Constants.TILE_SIZE)));
                break;
        }
    }
    public BombController bomb() {
        return this.bombController;
    }

    public BombermanController bomberman() {
        return this.bombermanController;
    }

    public GhostController ghost()
    {
        return this.ghostController;
    }

    public void update(float x, float y)
    {
        bombermanController.update(x,y);
    }

    @Override
    public void draw(OrthographicCamera camera) {
        bombController.draw(camera);
        ghostController.draw(camera);
        bombermanController.draw(camera);

    }

    public void setStatsListeners(IStatsChangeListener listener)
    {
        bombermanController.setStatsListener(listener);
        bombController.setStatsListener(listener);
        ghostController.setStatsListener(listener);
    }


    public void loadPreferances()
    {
        BombermanPreferances bp = BombermanPreferances.getInstance();
        bombermanController.getPlayer().setVelocity(bp.getVelocity());
        bombController.setRange(bp.getBombRange());
        bombermanController.getPlayer().setBombsCount(bp.getBombCount());
        bombermanController.getPlayer().setLifes(bp.getLifes());
        bombermanController.getPlayer().setScore(bp.getScore());
    }

    public void savePreferances(int level)
    {
        Bomberman player = bombermanController.getPlayer();
        BombermanPreferances bp = BombermanPreferances.getInstance();
        bp.setScore(player.getScore());
        bp.setBombRange(bombController.getRange());
        bp.setBombsCount(player.getBombCount());
        bp.setVelocity(player.getVelocity());
        bp.setLifes(player.getLifes());
        bp.setMaxMapIndex(level);
    }

    /**
     * Reseting player stats upon death
     */
    public void resetStats() {
        Bomberman player = bombermanController.getPlayer();
        player.setBombsCount(1);
        player.setLifes(3);
        player.setVelocity(2f);
        player.setScore(0);
        bombController.setRange(1);
    }
}
