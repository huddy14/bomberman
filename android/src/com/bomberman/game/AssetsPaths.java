package com.bomberman.game;

/**
 * Created by huddy on 6/3/16.
 */
public final class AssetsPaths {
    //ścieżki animacji bombermana
    public static final String BOMBERMAN_FRONT_PACK = "Bomberman/Front/BombermanFront.pack" ;
    public static final String BOMBERMAN_BACK_PACK =  "Bomberman/Back/BombermanBack.pack";
    public static final String BOMBERMAN_SIDE_LEFT_PACK= "Bomberman/SideLeft/BombermanSideLeft.pack" ;
    public static final String BOMBERMAN_SIDE_RIGHT = "Bomberman/Side/BombermanSide.pack" ;

    //ścieżki animacji potworków

    public static final String GHOST_SIDE_PACK = "Creep/Side/GhostSide.pack" ;
    public static final String GHOST_SIDE_LEFT_PACK = "Creep/SideLeft/GhostSideLeft.pack" ;
    public static final String GHOST_FRONT_PACK = "Creep/Front/GhostFront.pack" ;
    public static final String GHOST_BACK_PACK = "Creep/Back/GhostBack.pack" ;

    //ścieżki animacji bomby
    public static final String BOMB_ANIMATION = "Bomb/BombAnimation.pack" ;
    public static final String BOOM_ANIMATION = "Flame/FlameAnimation.pack";

    //obraz przycisku bomby
    public static final String BOMB_BUTTON = "Bomb/bombButton.png";

    //tablica map
    public static final String[] MAPS = {"Maps/Bomber.tmx","Maps/Lvl2.tmx","Maps/Lvl3.tmx","Maps/Lvl4.tmx","Maps/Lvl5.tmx","Maps/Lvl6.tmx"};

    //obrazy elementów sterowania
    public static final String TOUCHPAD_BACKGROUND = "Touchpad/touchBackground.png"  ;
    public static final String TOUCHPAD_KNOB = "Touchpad/touchKnob.png" ;

    //tło splashsceen
    public static final String SPLASH_SCREEN = "SplashScreen/SplashScreen.png";

    //elementy main menu
    public static final String PLAY_BUTTON_MENU = "Screens/MainMenu/playButton.png";
    public static final String STOP_BUTTON_MENU = "Screens/MainMenu/stopButton.png";
    public static final String SCORES_BUTTON_MENU = "Screens/MainMenu/scoreButton.png";
    public static final String BUTTON_BACKGROUND_MENU = "Screens/MainMenu/buttonBackground.png";
    public static final String BACKGROUND_MENU = "Screens/MainMenu/background.png";


    //elementy level selection
    public static final String[] LEVEL_SELECTION_MAP = {"Screens/LevelSelection/map1.png","Screens/LevelSelection/map2.png","Screens/LevelSelection/map3.png","Screens/LevelSelection/map4.png","Screens/LevelSelection/map5.png","Screens/LevelSelection/map6.png"};
    public static final String LEVEL_SELECTION_LOCK = "Screens/LevelSelection/Lock.png";
    public static final String LEVEL_SELECTION_BACKGROUND = "Screens/LevelSelection/background.png";

    //ścieżki audio
    public static final String AUDIO_BONUS = "Audio/bonus.wav";
    public static final String AUDIO_EXPLOSION = "Audio/explosion.wav";
    public static final String AUDIO_GAME_OVER = "Audio/gameOver.wav";
    public static final String AUDIO_SOUNDTRACK = "Audio/soundtrack.mp3";

    //ścieżki do czcionek
    public static final String FONT_HIGHSCORE = "Fonts/highscore.ttf";
    public static final String FONT_STATISTICS = "Fonts/statistic.ttf";

    //obiekty i warstwy map

    public static final String SOLID_OBJECT = "Solid";
    public static final String POWER_OBJECT = "Power";
    public static final String PORTAL_OBJECT = "Exit";
    public static final String BOMB_UP = "bomb";
    public static final String FLAME_UP = "fire";
    public static final String SPEED_UP = "speed";
    public static final String EXPLODING_OBJECT = "Exploding";
    public static final String SOLID_LAYER = "SOLID_LAYER";
    public static final String EXPLODING_LAYER = "EXPLODING_LAYER";
    public static final String POWER_LAYER = "POWER_LAYER";
    public static final String BACKGROUND_LAYER = "BACKGROUND_LAYER";

}
