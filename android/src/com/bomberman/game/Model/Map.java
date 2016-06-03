package com.bomberman.game.Model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Patryk on 15.05.2016.
 */
public class Map {
    public Bomberman player;
    public int width;
    public int height;
    Array<Wall> walls = new Array<>();

    public Array<Wall> getWalls() {
        return walls;
    }

    public Bomberman getPlayer() {
        return player;
    }

    public Map() {
        width = 80;
        height = 50;
        createWorld();
    }

    public void createWorld() {
        // X/Y Szer/Wys
        player = new Bomberman(new Vector2(35,25));

        walls.add(new Wall(new Vector2(0, 0)));
        walls.add(new Wall(new Vector2(10, 10)));
        walls.add(new Wall(new Vector2(20, 0)));
        walls.add(new Wall(new Vector2(30, 10)));
        walls.add(new Wall(new Vector2(40, 0)));
        walls.add(new Wall(new Vector2(50, 10)));
        walls.add(new Wall(new Vector2(60, 0)));
        walls.add(new Wall(new Vector2(70, 10)));
    }
}
