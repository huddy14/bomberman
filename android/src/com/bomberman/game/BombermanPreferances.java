package com.bomberman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by huddy on 6/22/16.
 */
public class BombermanPreferances {

    //Klasa do zapisywania oraz wczytywania statystyk, ustawien gracza z lokalnego pliku preferances
    private static BombermanPreferances ourInstance;
    private static Preferences prefs;

    private final String BOMBERMAN = "bomberman", BOMBS_COUNT = "bombscount", BOMB_RANGE = "bombrange",
            VELOCITY = "velocity", LIFES = "lifes", LEVEL = "level", SCORE = "score", HIGHSCORE = "highscore",SOUND_ENABLED = "sound";

    public static BombermanPreferances getInstance() {
        if(ourInstance==null)
            ourInstance = new BombermanPreferances();
        return ourInstance;
    }

    private BombermanPreferances() {
        prefs = Gdx.app.getPreferences(BOMBERMAN);
    }

    public void setBombsCount(int bombs)
    {
        prefs.putInteger(BOMBS_COUNT,bombs);
        prefs.flush();
    }

    public int getBombCount()
    {
        if(!prefs.contains(BOMBS_COUNT))
            setBombsCount(1);
        return prefs.getInteger(BOMBS_COUNT);

    }

    public void setBombRange(int range)
    {
        prefs.putInteger(BOMB_RANGE,range);
        prefs.flush();

    }

    public int getBombRange()
    {
        if(!prefs.contains(BOMB_RANGE))
            setBombRange(1);
        return prefs.getInteger(BOMB_RANGE);
    }

    public void setVelocity(float velocity)
    {
        prefs.putFloat(VELOCITY,velocity);
        prefs.flush();

    }

    public float getVelocity()
    {
        if(!prefs.contains(VELOCITY))
            setVelocity(2f);
        return prefs.getFloat(VELOCITY);
    }

    public void setMaxMapIndex(int index)
    {
        if(index>getMaxMapIndex()) {
            prefs.putInteger(LEVEL, index);
            prefs.flush();
        }

    }

    public int getScore()
    {
        if(!prefs.contains(SCORE))
            setScore(0);
        return prefs.getInteger(SCORE);

    }

    public void setScore(int score)
    {
        prefs.putInteger(SCORE,score);
        prefs.flush();
    }

    public int getHighScore(int index)
    {
        if(!prefs.contains(HIGHSCORE+index)) {
            prefs.putInteger(HIGHSCORE + index, 0);
            prefs.flush();
        }
        return prefs.getInteger(HIGHSCORE+index);
    }

    public void setHighScore(int score)
    {
        Integer[] scores = new Integer[4];
        scores[3] = score;
        for(int i = 0 ; i < 3 ; i++)
            scores[i]=getHighScore(i);
        Arrays.sort(scores,Collections.<Integer>reverseOrder());
        for(int i = 0 ; i <3 ; i++)
            prefs.putInteger(HIGHSCORE+i,scores[i]);
        prefs.flush();
    }

    public int getMaxMapIndex()
    {
        if(!prefs.contains(LEVEL))
            prefs.putInteger(LEVEL,0);
        prefs.flush();

        return prefs.getInteger(LEVEL);
    }

    public void setLifes(int lifes)
    {
        prefs.putInteger(LIFES,lifes);
        prefs.flush();
    }
    public int getLifes()
    {
        if(!prefs.contains(LIFES)) {
            prefs.putInteger(LIFES, 3);
            prefs.flush();
        }

        return prefs.getInteger(LIFES);
    }

    public boolean getSound()
    {
        if(!prefs.contains(SOUND_ENABLED)) {
            setSound(true);
        }
        return prefs.getBoolean(SOUND_ENABLED);
    }

    public void setSound(boolean isEnabled)
    {
        prefs.putBoolean(SOUND_ENABLED,isEnabled);
        prefs.flush();
    }

}
