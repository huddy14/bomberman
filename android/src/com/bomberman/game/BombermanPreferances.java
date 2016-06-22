package com.bomberman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by huddy on 6/22/16.
 */
public class BombermanPreferances {
    private static BombermanPreferances ourInstance;
    private static Preferences prefs;

    public static BombermanPreferances getInstance() {
        if(ourInstance==null)
            ourInstance = new BombermanPreferances();
        return ourInstance;
    }

    private BombermanPreferances() {
        prefs = Gdx.app.getPreferences("BOMBERMAN");
    }

    public void setBombsCount(int bombs)
    {
        prefs.putInteger("BOMBS_COUNT",bombs);
        prefs.flush();
    }

    public int getBombCount()
    {
        if(!prefs.contains("BOMBS_COUNT"))
            setBombsCount(1);
        return prefs.getInteger("BOMBS_COUNT");

    }

    public void setBombRange(int range)
    {
        prefs.putInteger("BOMB_RANGE",range);
        prefs.flush();

    }

    public int getBombRange()
    {
        if(!prefs.contains("BOMB_RANGE"))
            setBombRange(1);
        return prefs.getInteger("BOMB_RANGE");
    }

    public void setVelocity(float velocity)
    {
        prefs.putFloat("VELOCITY",velocity);
        prefs.flush();

    }

    public float getVelocity()
    {
        if(!prefs.contains("VELOCITY"))
            setVelocity(2f);
        return prefs.getFloat("VELOCITY");
    }

    public void setMaxMapIndex(int index)
    {
        if(index>getMaxMapIndex()) {
            prefs.putInteger("MAP_INDEX", index);
            prefs.flush();
        }

    }

    public int getMaxMapIndex()
    {
        if(!prefs.contains("MAP_INDEX"))
            prefs.putInteger("MAP_INDEX",1);
        prefs.flush();

        return prefs.getInteger("MAP_INDEX");
    }

    public void setLifes(int lifes)
    {
        prefs.putInteger("LIFES",lifes);
        prefs.flush();
    }
    public int getLifes()
    {
        if(!prefs.contains("LIFES")) {
            prefs.putInteger("LIFES", 3);
            prefs.flush();
        }

        return prefs.getInteger("LIFES");
    }
}
