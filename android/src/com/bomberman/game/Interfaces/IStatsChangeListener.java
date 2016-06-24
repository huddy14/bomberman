package com.bomberman.game.Interfaces;

/**
 * Created by huddy on 6/24/16.
 */
public interface IStatsChangeListener {
    void onLevelChange(int level);
    void onLifeCountChange(int lifes);
    void onBombCountChange(int bombs);
    void onBombRangeChange(int range);
    void onSpeedChange(float velocity);
}
