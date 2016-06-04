package com.bomberman.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
public class BombController extends ChangeListener {
    //private ArrayList<Bomb> bombList = new ArrayList<>();
    private Bomb bomb;
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

    int iter = 0;

    public BombController(Bomberman bomberman, BombermanController bombermanController, TiledMap map, MapCamera camera)
    {
        this.player = bomberman;
        this.map = map;
        this.camera = camera;
        this.layer = (TiledMapTileLayer)map.getLayers().get(Constants.EXPLODING_LAYER);//tile layer explodin
        this.bombermanController = bombermanController;
        this.bombView = new BombView();

        getExplodableElements();
        getSolidElements();
    }

    private void getExplodableElements()
    {
        mapObjects = map.getLayers().get(Constants.EXPLODING_OBJECT).getObjects();
        for(int i=0 ; i < mapObjects.getCount(); i++)
        {
            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
            explodableElements.add(obj.getRectangle());
        }
    }

    private void getSolidElements()
    {
        mapObjects = map.getLayers().get(Constants.SOLID_OBJECT).getObjects();
        for(int i=0 ; i < mapObjects.getCount(); i++)
        {
            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
            solidElements.add(obj.getRectangle());
        }
    }
    //TODO:usuwanie elementów dopiero w momencie gdy bomba wybuchnie nie wcześniej
    private void updateExplodableElements()
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
                    bombermanController.deleteExplodableElements(explodableElements.get(i));
                    explodableElements.remove(i);
                }

            }
        }
        iter++;
    }

    public void addBomb(Bomb bomb)
    {
        exploded = false;
        this.bomb = bomb;
        this.flames.clear();
    }



    //rysowanie bomby przez okreslony czas
    public void drawBomb()
    {
        if(bombPlanted) {
            currentBombTime += Gdx.graphics.getDeltaTime();
            //TODO: jest 3 a powinno byc 3000 bo millisec DZIWNY SZYT
            if (currentBombTime < TIME_TO_DETONATE) {

                bombView.setProjectionMatrix(camera.combined);
                bombView.drawBomb(bomb.getPosition());

                //rysowanie zasiegu bomby
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.WHITE);
                //shapeRenderer.set();
                shapeRenderer.circle(bomb.getBounds().x, bomb.getBounds().y, bomb.getBounds().radius);
                shapeRenderer.end();
            } else {

                currentBombTime = 0;
                updateExplodableElements();
                prepareFlames();
                bombPlanted = false;
                exploded = true;
            }

        }
        if(exploded)
        {
            drawFlames();
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

    private void prepareFlames()
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
    public void changed(ChangeEvent event, Actor actor) {
        if(!bombPlanted) {
            this.addBomb(new Bomb(new Vector2(player.getPosition().x,player.getPosition().y)));
            bombPlanted = true;
        }
    }
}
