package com.bomberman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by huddy on 6/26/16.
 * Generating fonts used in application
 */
public class FontGenerator {
    //klasa do tworzenia czcionek
    private static FontGenerator ourInstance;

    private BitmapFont highScoreBig;
    private BitmapFont highScoreSmall;
    private BitmapFont statistics;
    public static FontGenerator getInstance() {

        if(ourInstance==null)
            ourInstance = new FontGenerator();
        return ourInstance;
    }

    private FreeTypeFontGenerator generatorScore = new FreeTypeFontGenerator(Gdx.files.internal(AssetsPaths.FONT_HIGHSCORE));
    private FreeTypeFontGenerator generatorStats = new FreeTypeFontGenerator(Gdx.files.internal(AssetsPaths.FONT_STATISTICS));



    private FontGenerator() {

    }

    public void generateFonts()
    {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getHeight()/8;
        highScoreBig = generatorScore.generateFont(parameter);
        parameter.size = Gdx.graphics.getHeight()/10;
        highScoreSmall = generatorScore.generateFont(parameter);
        parameter.size = Gdx.graphics.getHeight()/25;
        statistics = generatorStats.generateFont(parameter);
    }

    public BitmapFont getHighscoreSmallFont()
    {
        return this.highScoreSmall;
    }

    public BitmapFont getHighscoreBigFont()
    {
        return this.highScoreBig;
    }

    public BitmapFont getStatisticsFont()
    {
        return this.statistics;
    }

}