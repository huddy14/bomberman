package com.bomberman.game.Controller;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Interfaces.IExplosionListener;
import com.bomberman.game.Interfaces.IStatsChangeListener;
import com.bomberman.game.Model.Bomberman;
import com.bomberman.game.Model.Bomb;
import com.bomberman.game.Model.ExplosionBounds;
import com.bomberman.game.Model.Map;
import com.bomberman.game.View.BombView;
import com.bomberman.game.View.ExplosionView;

import java.util.ArrayList;

/**
 * Created by huddy on 6/3/16.
 */
public class BombController implements IController, Bomb.BombListener
{
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private BombView bombView;
    private ArrayList<IExplosionListener> onExplosionListeners = new ArrayList<>();
    private Bomberman player;
    private ExplosionView explosion = new ExplosionView();
    private Map map;
    private Map.CollisionDetector collisionDetector;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private int range = 1;
    private IStatsChangeListener statsListener;

    private ArrayList<Bomb> bombsToDelete = new ArrayList<>();

    public BombController(Map map)
    {
        this.map = map;
        this.collisionDetector = map.getCollisionDetector();
        this.bombView = new BombView();
    }

    public int getRange()
    {
        return this.range;
    }
    public void setRange(int range) { this.range = MathUtils.clamp(range,1,3);}

    public void addBomb()
    {
        //dodajac bombe od razu sprawdzamy jakie stale elementy wybuchną
        bombs.add(collisionDetector.bombsExplosionBoundsCollision(new Bomb(new Vector2(player.getPosition().x,player.getPosition().y),this),bombs));
        Log.w("ilosc itemow :","" + bombs.size());
    }

    public void setStatsListener(IStatsChangeListener statsListener)
    {
        this.statsListener = statsListener;
    }


    //zmienic na prajwat i rysuje wszystkie bomby
    public void drawBomb(Bomb bomb,OrthographicCamera camera)
    {
        //bomb.update(Gdx.graphics.getDeltaTime());
        if(player.isBombPlanted()) {
            if (bomb.getState() == Bomb.State.COUNT_DOWN) {

                bombView.setProjectionMatrix(camera.combined);
                bombView.drawBomb(bomb.getPosition());

                //rysowanie zasiegu bomby
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.WHITE);
                //shapeRenderer.set();
                shapeRenderer.circle(bomb.getBounds().x, bomb.getBounds().y, bomb.getBounds().radius);
                shapeRenderer.rect(bomb.getExplosionBounds().getHorizontalRectangle().x,bomb.getExplosionBounds().getHorizontalRectangle().y,bomb.getExplosionBounds().getHorizontalRectangle().width,
                        bomb.getExplosionBounds().getHorizontalRectangle().height);
                shapeRenderer.rect(bomb.getExplosionBounds().getVerticalRectangle().x,bomb.getExplosionBounds().getVerticalRectangle().y,bomb.getExplosionBounds().getVerticalRectangle().width,
                        bomb.getExplosionBounds().getVerticalRectangle().height);
//                Log.w("x:",""+bomb.getExplosionBounds().getHorizontalRectangle().x);
//                Log.w("y",""+bomb.getExplosionBounds().getHorizontalRectangle().y);
//                Log.w("widht",""+bomb.getExplosionBounds().getHorizontalRectangle().width);
//                Log.w("height",""+
//                        bomb.getExplosionBounds().getHorizontalRectangle().height);
                shapeRenderer.end();
            }
        }
        if(bomb.getState()==Bomb.State.EXPLODED) {
            drawExplosion(bomb,camera);
        }

    }

    private void drawExplosion(Bomb bomb, OrthographicCamera camera) {
        explosion.setProjectionMatrix(camera.combined);
        ExplosionBounds exBounds = bomb.getExplosionBounds();
        for (int i = exBounds.getYmin(); i <= exBounds.getYmax(); i++)
            explosion.drawBoom(exBounds.getX(), i);
        for (int i = exBounds.getXmin(); i <= exBounds.getXmax(); i++)
            explosion.drawBoom(i, exBounds.getY());


    }
    public void setPlayer(Bomberman player) {
        this.player = player;
    }

    public void addOnExplosionListener(IExplosionListener listener)
    {
        onExplosionListeners.add(listener);
    }

    @Override
    public void draw(OrthographicCamera camera) {
        bombs.removeAll(bombsToDelete);
        bombsToDelete.clear();

        for(Bomb b : bombs)
        {
            b.update(Gdx.graphics.getDeltaTime());
            drawBomb(b,camera);
        }
    }

    @Override
    public void onBombExploded(Bomb bomb) {
        //usuwamy z mapy elementy ktore wybuchly
        map.deleteTiles(bomb.getExplosionBounds());
        //powiadamiamy obiekty nasłuchujące, że bomby eksplodowała
        for(IExplosionListener listener : onExplosionListeners)
            listener.onExplosion(bomb);

        player.bombExploded();
        statsListener.onScoreChange(bomb.getExplosionBounds().toDeleteCount() * 10);
        statsListener.onBombCountChange(player.getAvalibleBombs());
    }

    @Override
    public void onExplosionFinished(Bomb bomb) {
        bombsToDelete.add(bomb);

    }

    @Override
    public ExplosionBounds onBombPlanted(Bomb bomb) {
        player.bombPlanted();
        statsListener.onBombCountChange(player.getAvalibleBombs());
        return collisionDetector.bombExplosionCollision(bomb);
    }
}
