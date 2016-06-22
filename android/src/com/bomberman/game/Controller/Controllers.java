package com.bomberman.game.Controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Model.Ghost;
import com.bomberman.game.Model.Map;

/**
 * Created by huddy on 6/21/16.
 */
public class Controllers implements IController{
    private BombController bombController;
    private BombermanController bombermanController;
    private GhostController ghostController;

    public Controllers(Map map) {
        initializeControllers(map);
    }

    private void initializeControllers(Map map) {
        //inicjalizujemy kontrollery

        bombController = new BombController(map);
        bombermanController = new BombermanController(map);
        ghostController = new GhostController(map);

        //dodajemy do kontrollerów obiekty umożliwiająca komunikacje między nimi

        bombController.setPlayer(bombermanController.getPlayer());
        bombController.addOnExplosionListener(bombermanController);
        bombController.addOnExplosionListener(ghostController);

        bombermanController.setControllers(bombController,ghostController);

        ghostController.setPlayer(bombermanController.getPlayer());
        ghostController.addGhost(new Ghost(new Vector2(5 * Constants.TILE_SIZE, 5*Constants.TILE_SIZE)));
        ghostController.addGhost(new Ghost(new Vector2(11 * Constants.TILE_SIZE, 9*Constants.TILE_SIZE)));
        ghostController.addGhost(new Ghost(new Vector2(11 * Constants.TILE_SIZE, 3*Constants.TILE_SIZE)));
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
}