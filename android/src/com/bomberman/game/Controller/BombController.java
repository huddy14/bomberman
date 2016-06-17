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
    private TiledMap map;
    private Bomberman player;
    private MapObjects mapObjects;
    private ArrayList<Rectangle> explodableElements = new ArrayList<>();
    private ArrayList<Rectangle> solidElements = new ArrayList<>();

    private TiledMapTileLayer layer;
    private boolean bombPlanted = false;
    private boolean exploded = false;
    private MapCamera camera;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private float currentBombTime = 0;
    private float currentFlameTime = 0;
    private ArrayList<BoomView> flames = new ArrayList<>();

    boolean[] isSolid = new boolean[4];
    private final static float TIME_TO_DETONATE = 3f;
    private final static float EXPLOSION_TIME = 1f;
    private Bomb bombToDelete = null;

    int iter = 0;
    //TODO: usunac kamery, usunac playera(informacje ma pobierac z bombermanController'a)
    public BombController(Bomberman bomberman, BombermanController bombermanController, TiledMap map, MapCamera camera)
    {
        this.player = bomberman;
        this.map = map;
        this.camera = camera;
        this.layer = (TiledMapTileLayer)map.getLayers().get(Constants.EXPLODING_LAYER);//tile layer explodin
        this.bombermanController = bombermanController;
        this.bombView = new BombView();

        explodableElements = GlobalMethods.getElements(map,Constants.EXPLODING_OBJECT);
        solidElements = GlobalMethods.getElements(map,Constants.SOLID_OBJECT);

    }

    private void updateExplodableElements(Bomb bomb)
    {
        for(int i=0;i<explodableElements.size();i++)
        {
            if(Intersector.overlaps(bomb.getBounds(),explodableElements.get(i)))
            {
                int bombX,bombY;
                //pobieramy wsporzedne bomby sprawdzamy czy element kolidujacy nie znajduje sie na przekatnej
                bombX = (int)(bomb.getX() - (bomb.getX() % Constants.TILE_SIZE))/Constants.TILE_SIZE;
                bombY = (int)(bomb.getY() - (bomb.getY() % Constants.TILE_SIZE))/Constants.TILE_SIZE;

                //graficzne usuwanie elementu
                int x = (int)(explodableElements.get(i).x - (explodableElements.get(i).x % Constants.TILE_SIZE))/Constants.TILE_SIZE;
                int y = (int)(explodableElements.get(i).y - (explodableElements.get(i).y % Constants.TILE_SIZE))/Constants.TILE_SIZE;

                if(bombX == x || bombY == y)
                {
                    layer.getCell(x, y).setTile(null);
                    //usuwanie elementu z tablicy kolizji
                    bombermanController.updateCollisionElements(explodableElements.get(i));
                    explodableElements.remove(i);
                }

            }
        }
    }

    public void addBomb()
    {
        bombs.add(new Bomb(new Vector2(player.getPosition().x,player.getPosition().y),this));
        this.flames.clear();
    }



    //zmienic na prajwat i rysuje wszystkie bomby
    public void drawBomb(Bomb bomb,OrthographicCamera camera)
    {
        if(player.isBombPlanted()) {
            bomb.update(Gdx.graphics.getDeltaTime());
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

    }

    private void drawFlames()
    {
        currentFlameTime += Gdx.graphics.getDeltaTime();

        if(currentFlameTime<EXPLOSION_TIME)
        {
            for(BoomView flame : flames)
            {
                flame.setProjectionMatrix(camera.combined);
                flame.drawBoom();
            }
        }
        else
        {
            exploded = false;
            currentFlameTime = 0;
        }
    }

    private void prepareFlames(Bomb bomb)
    {
        //TODO:rozkminic zeby to prosciej zapisac
        negateIsSolid();

        int bombX,bombY;
        //pobieramy wsporzedne bomby sprawdzamy czy element kolidujacy nie znajduje sie na przekatnej
        bombX = (int)(bomb.getX() - (bomb.getX() % Constants.TILE_SIZE))/Constants.TILE_SIZE;
        bombY = (int)(bomb.getY() - (bomb.getY() % Constants.TILE_SIZE))/Constants.TILE_SIZE;

        for(int i=0;i<solidElements.size();i++)
        {

            if(Intersector.overlaps(bomb.getBounds(),solidElements.get(i)))
            {
                int x = (int)(solidElements.get(i).x - (solidElements.get(i).x % Constants.TILE_SIZE))/Constants.TILE_SIZE;
                int y = (int)(solidElements.get(i).y - (solidElements.get(i).y % Constants.TILE_SIZE))/Constants.TILE_SIZE;

                if(bombX + 1 == x && bombY == y)isSolid[0] = true;
                else if(bombX - 1 == x && bombY == y)isSolid[1] = true;
                else if(bombX  == x && bombY == y + 1)isSolid[2] = true;
                else if(bombX  == x && bombY == y - 1)isSolid[3] = true;

            }
        }

        if(!isSolid[0])flames.add(new BoomView(bombX + 1, bombY));
        if(!isSolid[1])flames.add(new BoomView(bombX - 1, bombY));
        if(!isSolid[2])flames.add(new BoomView(bombX , bombY + 1));
        if(!isSolid[3])flames.add(new BoomView(bombX , bombY - 1));
        flames.add(new BoomView(bombX,bombY));

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
            drawBomb(b,camera);
        }
    }

    @Override
    public void onBombExploded(Bomb bomb) {
        updateExplodableElements(bomb);
        //prepareFlames(bomb);
        //drawFlames();
        player.bombExploded();
        bombToDelete = bomb;
    }

    @Override
    public void onBombPlanted(Bomb bomb) {
        //drawBomb(bomb);
        //player.bombPlanted();
    }
}
