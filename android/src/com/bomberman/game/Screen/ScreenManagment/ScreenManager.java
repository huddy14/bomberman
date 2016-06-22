package com.bomberman.game.Screen.ScreenManagment;

import com.badlogic.gdx.Screen;
import com.bomberman.game.BombGame;

/**
 * Created by huddy on 6/22/16.
 */
public class ScreenManager {
    private static ScreenManager instance;

    private BombGame game;

    public static ScreenManager getInstance()
    {
        if(instance==null)
            instance = new ScreenManager();
        return instance;
    }

    private ScreenManager()
    {
        super();
    }

    public void initialize(BombGame game)
    {
        this.game = game;
    }

    public void showScreen(ScreenEnum screen, Object... params)
    {
        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        Screen newScreen = screen.getScreen(params);
        game.setScreen(newScreen);

        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
