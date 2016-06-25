package com.bomberman.game.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.BombermanPreferances;
import com.bomberman.game.Screen.ScreenManagment.ScreenEnum;
import com.bomberman.game.Screen.ScreenManagment.ScreenManager;

/**
 * Created by huddy on 6/25/16.
 */
public class HighscoreScreen extends AbstractScreen {

    private Label[] labels = new Label[3];
    private Texture texture = new Texture(AssetsPaths.BOMB_BUTTON);

    public HighscoreScreen()
    {
        super();
        buildStage();
    }
    @Override
    public void buildStage() {
        Table table = new Table();
        table.top();
        table.left();
        table.setFillParent(true);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(5f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        BombermanPreferances bp = BombermanPreferances.getInstance();
        for(int i = 0; i < 3 ; i++)
        {
            labels[i] = new Label((i+1)+". "+ bp.getHighScore(i),labelStyle);
        }
        Image im = new Image(texture);
        im.setBounds(0,0,getWidth()/2,getWidth()/2);
        im.setFillParent(false);
        addActor(im);
        table.row();
        table.add(new Label("HIGHEST SCORES ",labelStyle));
        table.row();
        table.add(labels[0]).expandX();
        table.row();
        table.add(labels[1]).expandX();
        table.row();
        table.add(labels[2]).expandX();

        addActor(table);
    }

    @Override
    public void onBackButtonPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) ) {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
    }
}
