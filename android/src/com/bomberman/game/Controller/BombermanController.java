package com.bomberman.game.Controller;


import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.Constants;
import com.bomberman.game.Model.Bomberman;

import java.util.ArrayList;

/**
 * Created by Patryk on 15.05.2016.
 */
public class BombermanController {
    private Bomberman player;
    private TiledMap tiledMap;
    private MapObjects mapObjects;
    private ArrayList<Rectangle> solidElements = new ArrayList<>();

    public BombermanController(Bomberman player, TiledMap tiledMap)
    {
        this.tiledMap = tiledMap;
        this.player = player;
        getCollisionLayers();
    }

    private void getCollisionLayers()
    {
        mapObjects = tiledMap.getLayers().get(Constants.SOLID_OBJECT).getObjects();
        for(int i = 0 ; i < mapObjects.getCount(); i++)
        {
            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
            solidElements.add(obj.getRectangle());
        }
        mapObjects = tiledMap.getLayers().get(Constants.EXPLODING_OBJECT).getObjects();
        for(int i = 0 ; i < mapObjects.getCount(); i++)
        {
            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
            solidElements.add(obj.getRectangle());
        }
    }

    public void update(float x, float y)
    {
        float oldX = player.getPosition().x;
        float oldY = player.getPosition().y;

        player.update(x,y);

        for(int i = 0 ; i < solidElements.size();i++)
        {


            if(Intersector.overlaps(solidElements.get(i),player.getRectangle()))
            {
                player.setStatus(Bomberman.Status.IDLE);
                player.setX(oldX);
                player.setY(oldY);
                player.getPosition();
                break;

            }

        }
    }

    public void updateSolidElements(ArrayList<Rectangle>diff)
    {
        solidElements.removeAll(diff);
    }

    public void deleteExplodableElements(Rectangle obj)
    {
        solidElements.remove(obj);
    }

}
