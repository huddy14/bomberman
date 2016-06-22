package com.bomberman.game.Screen.ScreenManagment;

import com.badlogic.gdx.Screen;
import com.bomberman.game.Screen.GameScreen;
import com.bomberman.game.Screen.LevelSelection;
import com.bomberman.game.Screen.MenuScreen;
import com.bomberman.game.Screen.SplashScreen;

/**
 * Created by huddy on 6/22/16.
 */
public enum ScreenEnum {
    MAIN_MENU {
        public Screen getScreen(Object... params) {
            return new MenuScreen();
        }
    },

    LEVEL_SELECT {
        public Screen getScreen(Object... params) {
            return new LevelSelection();
        }
    },

    GAME {
        public Screen getScreen(Object... params) {
            return new GameScreen((Integer) params[0]);
        }
    },

    SPLASH {
        public Screen getScreen(Object... params){
            return new SplashScreen();
        }
    };

    public abstract Screen getScreen(Object... params);
}
