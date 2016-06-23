package com.bomberman.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bomberman.game.BombGame;
import com.bomberman.game.BombermanPreferances;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Interfaces.IMovingModel;

/**
 * Created by Patryk on 23.06.2016.
 */
public class TopBarView implements Disposable {
    private Stage stage;
    private Viewport viewport;
    private float elapsedTime;
    private float timeCount;

    private Label _rangeLabel;
    private Label _bombLabel;
    private Label _speedLabel;
    private Label _levelLabel;
    private Label _timeLabel;
    private Label _lifeLabel;

    private final int TIME_TO_SCORE_UPDATE = 1;


    public TopBarView(){
        timeCount = 0;
        elapsedTime = 0;
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport);
        setElements();
    }

    private void setElements(){
        Table table = new Table();
        table.top();
        table.left();
        table.setFillParent(true);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.5f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        _levelLabel = new Label("Level: 1", labelStyle);
        _bombLabel = new Label("Bombs: 1", labelStyle);
        _rangeLabel = new Label("Range: 1", labelStyle);
        _speedLabel = new Label("Speed: 1", labelStyle);
        _timeLabel  = new Label("Time: 0", labelStyle);
        _lifeLabel  = new Label("Lifes: 3", labelStyle);
        table.row();
        table.add(_levelLabel).expandX();
        table.add(_lifeLabel).expandX();
        table.add(_bombLabel).expandX();
        table.add(_rangeLabel).expandX();
        table.add(_speedLabel).expandX();
        table.add(_timeLabel).expandX();
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(int level, int life, int bombs, int range, int speed, float dt)
    {
        timeCount += dt;
        if(timeCount >= TIME_TO_SCORE_UPDATE) //aktualizacja wyniku co 1 sekunde
        {
            elapsedTime++;
            _timeLabel.setText("Time: " + (int)elapsedTime);
            timeCount = 0;
            _lifeLabel.setText("Lifes: " + life);
            _rangeLabel.setText("Range: " + range);
            _levelLabel.setText("Level: " + level);
            _bombLabel.setText("Bombs: " + bombs);
            _speedLabel.setText("Speed: " + speed);
        }
    }

    public void draw()
    {
        stage.draw();
    }

}

