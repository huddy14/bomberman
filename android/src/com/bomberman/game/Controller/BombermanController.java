package com.bomberman.game.Controller;


import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.Constants;
import com.bomberman.game.GlobalMethods;
import com.bomberman.game.Model.Bomberman;

import java.util.ArrayList;

/**
 * Created by Patryk on 15.05.2016.
 */
public class BombermanController {
    private Bomberman player;
    private TiledMap tiledMap;
    private MapObjects mapObjects;
    private ArrayList<Rectangle> collisionElements = new ArrayList<>();
    //private ArrayList<Rectangle> explodingElements = new ArrayList<>();


    public BombermanController(Bomberman player, TiledMap tiledMap)
    {
        this.tiledMap = tiledMap;
        this.player = player;
        getCollisionLayers();
    }

    private void getCollisionLayers()
    {
        collisionElements = GlobalMethods.getElements(tiledMap,Constants.SOLID_OBJECT);
//        mapObjects = tiledMap.getLayers().get(Constants.SOLID_OBJECT).getObjects();
//        for(int i = 0 ; i < mapObjects.getCount(); i++)
//        {
//            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
//            solidElements.add(obj.getRectangle());
//        }
        collisionElements.addAll(GlobalMethods.getElements(tiledMap,Constants.EXPLODING_OBJECT));
//        mapObjects = tiledMap.getLayers().get(Constants.EXPLODING_OBJECT).getObjects();
//        for(int i = 0 ; i < mapObjects.getCount(); i++)
//        {
//            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
//            solidElements.add(obj.getRectangle());
//        }
    }

    public void update(float x, float y)
    {
        float oldX = player.getPosition().x;
        float oldY = player.getPosition().y;

        player.update(x,y);

        for(int i = 0 ; i < collisionElements.size();i++)
        {


            if(Intersector.overlaps(collisionElements.get(i),player.getRectangle()))
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
        collisionElements.removeAll(diff);
    }

    public void updateCollisionElements(Rectangle obj)
    {
        collisionElements.remove(obj);
    }

}
