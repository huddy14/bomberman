package com.bomberman.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.bomberman.game.Audio.AudioManager;
import com.bomberman.game.BombermanPreferances;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.FontGenerator;
import com.bomberman.game.Interfaces.IGameStatus;
import com.bomberman.game.Interfaces.IStatsChangeListener;
import com.bomberman.game.Model.*;
import com.bomberman.game.Screen.CameraManagment.MapCamera;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;
import com.bomberman.game.View.*;
import com.bomberman.game.Controller.*;

/**
 * Created by Patryk on 16.05.2016.
 */

public class GameScreen extends AbstractScreen implements Screen, IGameStatus {
    private Controllers controller;
    private Touchpad touchpad;
    private Button bombButton;
    private MapCamera camera;
    private TiledMap tiledMap;
    private Map map;
    private TopBarView hud;
    private TiledMapRenderer tiledMapRenderer;
    private int level;
    private AudioManager audioManager;


//TODO: implementacja pozycji gracza i ghostow przy wczytywaniu mapy
    public GameScreen(int level )
    {
        super();
        this.level=level;

        initializeMap();
        initializeControllers();
        initializeView();

        buildStage();
        audioManager = AudioManager.getInstance();
        audioManager.playSoundtrack();

    }

    @Override
    public void buildStage() {
        touchpad = (new TouchpadView(10,new Touchpad.TouchpadStyle()));
        bombButton = (new BombButton());
        bombButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(controller.bomberman().getPlayer().canPlant()) {
                    controller.bomb().addBomb();
                }
            }
        });

        this.addActor(touchpad);
        this.addActor(bombButton);


    }

    @Override
    public void onBackButtonPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            ScreenManager.getInstance().showScreen(ScreenEnum.LEVEL_SELECTION);
        }
    }

    private void initializeControllers()
    {

        controller = Controllers.getInstance();
        controller.initializeControllers(map);

        //ustawiamy te klase jako sluchacza zmiany stanu gry

        controller.bomberman().setOnGameStatusChangeListener(this);
    }

    private void initializeMap()
    {
        tiledMap = new TmxMapLoader().load(AssetsPaths.MAPS[level]);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        map = new Map(tiledMap);

    }

    private void initializeView()
    {
        hud = new TopBarView();

        camera = new MapCamera(tiledMap.getProperties(),controller.bomberman().getPlayer());

        if(camera.getMapWidth() < Gdx.graphics.getWidth())
            camera.setToOrtho(false,camera.getMapWidth() ,Gdx.graphics.getHeight());
        else
            camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        hud.update(delta);

        camera.update();
        controller.update(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        controller.draw(camera);
        this.act(delta);
        this.draw();
    }

    @Override
    public void onGameStatusChange(GameStatus gameStatus) {
        if(gameStatus.equals(GameStatus.LOSE))
        {
            BombermanPreferances.getInstance().setHighScore(controller.bomberman().getPlayer().getScore());
            controller.resetStats();
            controller.savePreferances(level);
            ScreenManager.getInstance().showScreen(ScreenEnum.HIGHSCORE);
        }
        if(gameStatus.equals(GameStatus.WIN)) {

            //dodajemy bonus za czas
            hud.onScoreChange(2 * hud.getRemaningTime());

            //zapisujemy statystyki w preferencjach, odblokowujemy kolejną mape
            controller.savePreferances(level+1);

            ScreenManager.getInstance().showScreen(ScreenEnum.LEVEL_SELECTION);
        }

    }

    @Override
    public void dispose() {
        audioManager.stopSoundtrack();
        super.dispose();
    }

    private class TopBarView implements IStatsChangeListener {
        private int elapsedTime;
        private float timeCount;
        private final String LIFES = "Lifes: ", SCORE = "Score: ", BOMBS = "Bombs: ", LEVEL = "Level: ", RANGE = "Range: ", TIME = "Time: ", SPEED = "Speed: ";

        private Label _rangeLabel;
        private Label _bombLabel;
        private Label _speedLabel;
        private Label _levelLabel;
        private Label _timeLabel;
        private Label _lifeLabel;
        private Label _score;


        private final int TIME_TO_SCORE_UPDATE = 1;
        private final int MAX_TIME = 300;


        private TopBarView(){
            controller.setStatsListeners(this);
            timeCount = 0;
            elapsedTime = 0;
            setElements();
        }

        private void setElements(){
            Table table = new Table();
            table.top();
            table.left();
            table.setFillParent(true);
            BitmapFont font = FontGenerator.getInstance().getStatisticsFont();
            Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
            _levelLabel = new Label(LEVEL, labelStyle);
            _bombLabel = new Label(BOMBS, labelStyle);
            _rangeLabel = new Label(RANGE, labelStyle);
            _speedLabel = new Label(SPEED, labelStyle);
            _timeLabel  = new Label(TIME, labelStyle);
            _lifeLabel  = new Label(LIFES, labelStyle);
            _score  = new Label(SCORE, labelStyle);

            table.row();
            table.add(_levelLabel).expandX();
            table.add(_lifeLabel).expandX();
            table.add(_bombLabel).expandX();
            table.add(_rangeLabel).expandX();
            table.add(_speedLabel).expandX();
            table.add(_score).expandX();
            table.add(_timeLabel).expandX();
            addActor(table);

            initValues();

        }

        private void initValues()
        {
            BombermanPreferances bp = BombermanPreferances.getInstance();
            onLevelChange(level+1);
            onLifeCountChange(bp.getLifes());
            onBombCountChange(bp.getBombCount());
            onBombRangeChange(bp.getBombRange());
            onSpeedChange(bp.getVelocity());
            onScoreChange( bp.getScore());
        }
        public int getRemaningTime()
        {
            return MAX_TIME - elapsedTime;
        }

        public void update(float dt)
        {
            timeCount += dt;
            if(timeCount >= TIME_TO_SCORE_UPDATE) //aktualizacja wyniku co 1 sekunde
            {
                elapsedTime++;
                _timeLabel.setText("Time: " + String.format("%02d:%02d",(MAX_TIME - elapsedTime)/60,(MAX_TIME - elapsedTime) % 60));
                timeCount = 0;
            }
            if(MAX_TIME-elapsedTime == 0)
                //jeśli czas upłynie to koniec, sorry vinetu
                onGameStatusChange(GameStatus.LOSE);
        }


        @Override
        public void onLevelChange(int level) {
            _levelLabel.setText(LEVEL + level);

        }

        @Override
        public void onLifeCountChange(int lifes) {
            _lifeLabel.setText(LIFES + lifes);

        }

        @Override
        public void onBombCountChange(int bombs) {
            _bombLabel.setText(BOMBS + bombs);

        }

        @Override
        public void onBombRangeChange(int range) {
            _rangeLabel.setText(RANGE + range);

        }

        @Override
        public void onSpeedChange(float velocity) {
            _speedLabel.setText(SPEED + velocity);

        }

        @Override
        public void onScoreChange(int amount) {
            controller.bomberman().getPlayer().addToScore(amount);
            _score.setText(SCORE + controller.bomberman().getPlayer().getScore());
        }

    }
}
