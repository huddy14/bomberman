package com.bomberman.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.bomberman.game.GlobalMethods;
import com.bomberman.game.Interfaces.IController;
import com.bomberman.game.Model.Bomberman;
import com.bomberman.game.Constants;
import com.bomberman.game.Model.Bomb;
import com.bomberman.game.Model.ExplosionBounds;
import com.bomberman.game.Model.Map;
import com.bomberman.game.Screen.MapCamera;
import com.bomberman.game.View.BombView;
import com.bomberman.game.View.BoomView;

import java.util.ArrayList;

/**
 * Created by huddy on 6/3/16.
 */
public class BombController implements IController, Bomb.BombListener
{
    private ArrayList<Bomb> bombs = new ArrayList<>();
    //private Bomb bomb;
    private BombermanController bombermanController;
    private BombView bombView;
    private Bomberman player;
    private ArrayList<Rectangle> explodableElements;
    private ArrayList<Rectangle> solidElements;
    private Map map;
    private TiledMapTileLayer layer;
    private Map.CollisionDetector collisionDetector;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    boolean[] isSolid = new boolean[4];
    private Bomb bombToDelete = null;

    public BombController(Bomberman bomberman, BombermanController bombermanController, Map map)
    {
        this.player = bomberman;
        this.map = map;
        this.collisionDetector = map.getCollisionDetector();
        this.layer = (TiledMapTileLayer)map.getTiledMap().getLayers().get(Constants.EXPLODING_LAYER);//tile layer explodin
        this.bombermanController = bombermanController;
        this.bombView = new BombView();

        explodableElements = map.getExplodableElements();
        solidElements = map.getSolidElements();

    }

    private void updateExplodableElements(Bomb bomb)
    {
    }

    public void addBomb()
    {
        bombs.add(new Bomb(new Vector2(player.getPosition().x,player.getPosition().y),this));
        //this.flames.clear();
    }



    //zmienic na prajwat i rysuje wszystkie bomby
    public void drawBomb(Bomb bomb,OrthographicCamera camera)
    {
        bomb.update(Gdx.graphics.getDeltaTime());
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
                shapeRenderer.end();
            }
        }
        if(bomb.getState()!=Bomb.State.EXPLOSION_FINISHED) {
            drawFlames(bomb,camera);
        }

    }

    private void drawFlames(Bomb bomb, OrthographicCamera camera)
    {
        if(bomb.getState()!=Bomb.State.EXPLOSION_FINISHED && bomb.getState()!=Bomb.State.EXPLODED )
            for(BoomView flame : bomb.getFlames())
            {
                flame.setProjectionMatrix(camera.combined);
                flame.drawBoom();
            }

    }

    private void prepareFlames(Bomb bomb)
    {
//        //TODO:rozkminic zeby to prosciej zapisac
//        negateIsSolid();
//
//        int bombX,bombY;
//        //pobieramy wsporzedne bomby sprawdzamy czy element kolidujacy nie znajduje sie na przekatnej
//        bombX = (int)(bomb.getX() - (bomb.getX() % Constants.TILE_SIZE))/Constants.TILE_SIZE;
//        bombY = (int)(bomb.getY() - (bomb.getY() % Constants.TILE_SIZE))/Constants.TILE_SIZE;
//
//        for(int i=0;i<solidElements.size();i++)
//        {
//
//            if(Intersector.overlaps(bomb.getBounds(),solidElements.get(i)))
//            {
//                int x = (int)(solidElements.get(i).x - (solidElements.get(i).x % Constants.TILE_SIZE))/Constants.TILE_SIZE;
//                int y = (int)(solidElements.get(i).y - (solidElements.get(i).y % Constants.TILE_SIZE))/Constants.TILE_SIZE;
//
//                if(bombX + 1 == x && bombY == y)isSolid[0] = true;
//                else if(bombX - 1 == x && bombY == y)isSolid[1] = true;
//                else if(bombX  == x && bombY == y + 1)isSolid[2] = true;
//                else if(bombX  == x && bombY == y - 1)isSolid[3] = true;
//
//            }
//        }
//        ArrayList<BoomView>flames = new ArrayList<>();
//        if(!isSolid[0])flames.add(new BoomView(bombX + 1, bombY));
//        if(!isSolid[1])flames.add(new BoomView(bombX - 1, bombY));
//        if(!isSolid[2])flames.add(new BoomView(bombX , bombY + 1));
//        if(!isSolid[3])flames.add(new BoomView(bombX , bombY - 1));
//        flames.add(new BoomView(bombX,bombY));
//        bomb.setFlames(flames);

    }

    private void negateIsSolid()
    {
        for(int i = 0;i<isSolid.length;i++)
            isSolid[i]=false;
    }



    @Override
    public void draw(OrthographicCamera camera) {
        if(bombToDelete != null)
        {
            bombs.remove(bombToDelete);
            bombToDelete = null;
        }
        for(Bomb b : bombs)
        {
            //b.update(Gdx.graphics.getDeltaTime());
            drawBomb(b,camera);
          //if(b.getState()!=Bomb.State.EXPLOSION_FINISHED)
               // drawFlames(b,camera);
        }
    }

    @Override
    public void onBombExploded(Bomb bomb) {
        map.deleteTiles(bomb.getExplosionBounds());
        //prepareFlames(bomb);
        //drawFlames();
        player.bombExploded();
        bombToDelete = bomb;

    }

    @Override
    public void onExplosionFinished(Bomb bomb) {
        //bombToDelete = bomb;

    }

    @Override
    public ExplosionBounds onBombPlanted(Bomb bomb) {
        return collisionDetector.bombExplosionCollision(bomb);
        //drawBomb(bomb);
        //player.bombPlanted();
    }
}
