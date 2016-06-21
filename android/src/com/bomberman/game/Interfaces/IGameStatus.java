package com.bomberman.game.Interfaces;

/**
 * Created by huddy on 6/21/16.
 */
public interface IGameStatus {
    enum GameStatus {
        WIN,LOSE,PLAYING
    }
    void onGameStatusChange(GameStatus gameStatus);

}
