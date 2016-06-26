package com.bomberman.game.Screen.ScreenManagment;

import com.badlogic.gdx.Screen;
import com.bomberman.game.Screen.AbstractScreen;
import com.bomberman.game.Screen.GameScreen;
import com.bomberman.game.Screen.HighscoreScreen;
import com.bomberman.game.Screen.LevelSelection;
import com.bomberman.game.Screen.MenuScreen;
import com.bomberman.game.Screen.SplashScreen;

/**
 * Created by huddy on 6/22/16.
 * Enum create to ease screen initialization
 */
public enum ScreenEnum {
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MenuScreen();
        }
    },

    LEVEL_SELECTION {
        public AbstractScreen getScreen(Object... params) {
            return new LevelSelection();
        }
    },

    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen((Integer) params[0]);
        }
    },

    SPLASH {
        public AbstractScreen getScreen(Object... params){
            return new SplashScreen((Runnable)params[0]);
        }
    },

    HIGHSCORE {
        public AbstractScreen getScreen(Object... params) {return  new HighscoreScreen();}
    };

    public abstract AbstractScreen getScreen(Object... params);
}
