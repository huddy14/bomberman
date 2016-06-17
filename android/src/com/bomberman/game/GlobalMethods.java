package com.bomberman.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by huddy on 6/17/16.
 */
public class GlobalMethods {

    public static final ArrayList<Rectangle> getElements(TiledMap map, String layer_name)
    {
        ArrayList<Rectangle> elements = new ArrayList<>();
        MapObjects mapObjects = map.getLayers().get(layer_name).getObjects();
        for(int i=0 ; i < mapObjects.getCount(); i++)
        {
            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
            elements.add(obj.getRectangle());
        }
        return elements;
    }
}
