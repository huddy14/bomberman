package com.bomberman.game;

import com.badlogic.gdx.Game;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;
import com.bomberman.game.Screen.SplashScreen;

/**
 * Created by Patryk on 15.05.2016.
 */
public class BombGame extends Game {

    public SplashScreen game;

    //Utworzenie obiektu ekranu
    @Override
    public void create() {
        ScreenManager.getInstance().initialize(this);
        //tworzymy zadanie, które będzie wykonywane w tle podczas wyświetlania splash screena
        Runnable backgroundWork = new Runnable() {
            @Override
            public void run() {
                FontGenerator.getInstance().generateFonts();
            }
        };
        ScreenManager.getInstance().showScreen(ScreenEnum.SPLASH,backgroundWork);
    }

    @Override
    public void resize(int width, int height) {

    }
}
