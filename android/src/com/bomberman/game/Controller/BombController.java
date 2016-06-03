package com.bomberman.game.Controller;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.Model.Bomb;

import java.util.ArrayList;

/**
 * Created by huddy on 6/3/16.
 */
public class BombController {
    private ArrayList<Bomb> bombList = new ArrayList<>();
    private BombermanController bombermanController;
    private TiledMap map;
    private MapObjects mapObjects;
    private ArrayList<Rectangle> explodableElements = new ArrayList<>();
    private boolean bombPlanted = false;
    int iter = 0;

    public BombController(BombermanController bombermanController, TiledMap map)
    {
        this.map = map;
        this.bombermanController = bombermanController;
        getExplodableElements();
    }

    private void getExplodableElements()
    {
        mapObjects = map.getLayers().get(3).getObjects();
        for(int i=0 ; i < mapObjects.getCount(); i++)
        {
            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
            explodableElements.add(obj.getRectangle());
        }
    }
    //TODO:metoda ktora usuwa grafike z mapy setCell = null
    //TODO:usuwanie elementów dopiero w momencie gdy bomba wybuchnie nie wcześniej
    private void update()
    {
        //TODO:zaimplementowac metode zeby wykrywala tylko te kolizja pionowo i poziomo nie po skosie
        for(int i=0;i<explodableElements.size();i++)
        {
            if(Intersector.overlaps(bombList.get(iter).getBounds(),explodableElements.get(i)))
            {
                bombermanController.deleteExplodableElements(explodableElements.get(i));
                explodableElements.remove(i);

            }
        }
        iter++;
    }

    public void addBomb(Bomb bomb)
    {
        bombList.add(bomb);
        update();
    }
}
