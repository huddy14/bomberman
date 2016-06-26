package com.bomberman.game.Model;

import android.util.Log;
import android.util.Pair;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.AssetsPaths;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IMovingModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Patryk on 15.05.2016.
 * Storing all necessary information about currently used map
 * It stores collisionElements, explodableElements, powerupsElements
 */
public class Map {
    private TiledMap map;

    private ArrayList<Rectangle> solidElements = new ArrayList<>();
    private ArrayList<Rectangle> explodableElements = new ArrayList<>();
    private ArrayList<Rectangle> collisionElements = new ArrayList<>();
    private ArrayList<Rectangle> powerupElements = new ArrayList<>();

    private java.util.Map<Rectangle, String> pE;

    private CollisionDetector collisionDetector= new CollisionDetector();
    private Rectangle portal = new Rectangle();
    private int idCol, idPow;

    final int MAP_WIDTH;
    final int MAP_HEIGHT;

    private TiledMapTileLayer explodingLayer;
    private TiledMapTileLayer powerLayer;


    public Map(TiledMap map) {
        this.map = map;

        //pobieramy właściwości mapy

        MAP_WIDTH = map.getProperties().get("width", Integer.class);
        MAP_HEIGHT = map.getProperties().get("height", Integer.class);

        //wypelnienie tablic kolizji

        this.solidElements = getElements(map, AssetsPaths.SOLID_OBJECT);
        this.explodableElements = getElements(map, AssetsPaths.EXPLODING_OBJECT);
        this.powerupElements = getElements(map, AssetsPaths.POWER_OBJECT);
        this.pE = getPowerProp(map, AssetsPaths.POWER_OBJECT);
        this.portal = getElements(map, AssetsPaths.PORTAL_OBJECT).get(0);

        collisionElements.addAll(explodableElements);
        collisionElements.addAll(solidElements);

        this.explodingLayer = (TiledMapTileLayer)map.getLayers().get(AssetsPaths.EXPLODING_LAYER);
        this.powerLayer = (TiledMapTileLayer)map.getLayers().get(AssetsPaths.POWER_LAYER);

    }

    public Rectangle getRectangle(){
        return collisionElements.get(idCol);
    }

    public String getPowerType() {
        return pE.get(powerupElements.get(idPow));
    }

    private java.util.Map<Rectangle, String> getPowerProp(TiledMap map, String layer_name)
    {
        java.util.Map<Rectangle, String> pair = new HashMap<>();
        MapObjects mapObjects = map.getLayers().get(layer_name).getObjects();
        for(int i=0 ; i < mapObjects.getCount(); i++)
        {
            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
            String type = (String) map.getLayers().get(layer_name).getObjects().get(i).getProperties().get("type");
            pair.put(obj.getRectangle(), type);
        }
        return pair;
    }

    public void deletePower()
    {
        int x = (int) powerupElements.get(idPow).getX();
        int y = (int) powerupElements.get(idPow).getY();
        if(powerLayer.getCell(x/ Constants.TILE_SIZE,y/ Constants.TILE_SIZE)!=null)
            powerLayer.getCell(x/ Constants.TILE_SIZE,y/ Constants.TILE_SIZE).setTile(null);
        powerupElements.remove(idPow);
    }

    private static final ArrayList<Rectangle> getElements(TiledMap map, String layer_name)
    {
        ArrayList<Rectangle> elements = new ArrayList<>();
        MapObjects mapObjects = map.getLayers().get(layer_name).getObjects();
        for(int i=0 ; i < mapObjects.getCount(); i++)
        {
            RectangleMapObject obj = (RectangleMapObject)mapObjects.get(i);
            elements.add(obj.getRectangle());
        }
        return elements;
    }

    public int MAP_WIDTH()
    {
        return this.MAP_WIDTH;
    }

    public int MAP_HEIGHT()
    {
        return this.MAP_HEIGHT;
    }

        public CollisionDetector getCollisionDetector()
    {
        return this.collisionDetector;
    }

    //usuwanie elementów mapy graficzne oraz z tablicy obiektów kolizji

    /**
     * Graphiclly deleting objects from map and from collision tables
     * @param eb
     */
    public void deleteTiles(ExplosionBounds eb)
    {
        for(Rectangle obj : eb.getToDelete())
        {
            collisionElements.remove(obj);
            explodableElements.remove(obj);
        }
        setTileToNull(eb.getXmax(),eb.getY());
        setTileToNull(eb.getXmin(),eb.getY());
        setTileToNull(eb.getX(),eb.getYmax());
        setTileToNull(eb.getX(),eb.getYmin());

        Log.w("collision:",""+collisionElements.size()+" Explodable elements: "+explodableElements.size());
    }

    //usuwamy graficznie wybrany element mapy

    /**
     * Graphically deleting specified tile from map
     * @param x map coordinate x
     * @param y map coordinate y
     */
    private void setTileToNull(int x, int y)
    {
        if(explodingLayer.getCell(x,y)!=null)
            explodingLayer.getCell(x,y).setTile(null);
    }


    /**
     * Class created to detect collision
     */
    public class CollisionDetector {

        //TODO: generalnie ogarnac nazwy tych funkcji

        /**
         * Calculate screen coordinate to map coordinate
         * @param x screen coordinate
         * @return map coordinate
         */
        private int calculateTileCord(float x)
        {
            return (int)(x - (x % Constants.TILE_SIZE)) / Constants.TILE_SIZE;
        }

        /**
         * Checking for terrain collision
         * @param rectangle player/ghost bounds
         * @return
         */
        public boolean terrainCollision(Rectangle rectangle)
        {
            for (int i = 0; i < collisionElements.size(); i++)
                if (Intersector.overlaps(collisionElements.get(i), rectangle)) {
                    idCol = i;
                    return true;
                }
            return false;
        }

        //sprawdzamy czy gracz nie wszedł w portal

        /**
         * Checking for player-portal collision
         * @param rectangle player bounds
         * @return
         */
        public boolean portalCollison(Rectangle rectangle)
        {
            rectangle.width -= 10;
            rectangle.height -= 10;
            rectangle.setX(rectangle.getX()+5);
            rectangle.setY(rectangle.getY()+5);
            if (Intersector.overlaps(portal, rectangle))
                return true;
            return false;
        }

        //sprawdzamy czy gracz nie otrzymał bonusa

        /**
         * Checking for player-powerups collision
         * @param rectangle player bounds
         * @return
         */
        public boolean powerCollision(Rectangle rectangle)
        {
            rectangle.width -= 10;
            rectangle.height -= 10;
            rectangle.setX(rectangle.getX()+5);
            rectangle.setY(rectangle.getY()+5);
            for (int i = 0; i < powerupElements.size(); i++)
                if (Intersector.overlaps(powerupElements.get(i), rectangle)) {
                     idPow = i;
                    return true;
                }
            return false;
        }

        //sprawdzamy czy gracz nie został zabity przez potworki


        public boolean playerGhostsCollision(Rectangle player, ArrayList<Ghost> ghosts)
        {
            for (Ghost ghost : ghosts) {
                if (Intersector.overlaps(player, ghost.getBounds()))
                    return true;
            }
            return false;

        }
        //sprawdzamy czy eksplozja bomby nie zabiła gracza/potworka

        /**
         * Checking if bomb explosion didn't kill player/ghost
         * @param model player or ghost
         * @param bomb
         * @return
         */
        public boolean movingModelExplosionBoundsCollision(IMovingModel model, Bomb bomb)
        {
            return Intersector.overlaps(model.getSmallBounds(),bomb.getExplosionBounds().getVerticalRectangle()) ||
                    Intersector.overlaps(model.getSmallBounds(),bomb.getExplosionBounds().getHorizontalRectangle());
        }

        //sprawdzenie czy bomby nie są tak położone, że wzajemnie się detonują

        /**
         * Checking if bombs range cross each other
         * @param b1 new bomb
         * @param bombs bombs array
         * @return explosion bounds
         */
        public Bomb bombsExplosionBoundsCollision(Bomb b1, ArrayList<Bomb> bombs)
        {
            for(Bomb b2 : bombs) {
                ExplosionBounds e1 = b1.getExplosionBounds();
                ExplosionBounds e2 = b2.getExplosionBounds();

                //sprawdzamy czy promien razenia jednej bomby nachodzi na promien razenia reszty

                if (Intersector.overlaps(e1.getHorizontalRectangle(), e2.getHorizontalRectangle())) {

                    if ((e1.getY() % 2 != 0 && e2.getY()%2 != 0) && Math.abs(e1.getX() - e2.getX())<=b1.getRange() && Math.min(e1.getXmax(),e2.getXmax())!=Math.max(e1.getXmin(),e2.getXmin())) {
                        b1.setRemaningSeconds(b2.getRemainingSeconds());
                        return b1;
                    }
                }
                else if(Intersector.overlaps(e1.getVerticalRectangle(), e2.getVerticalRectangle()))
                {
                    if ((e1.getX() % 2 != 0 && e2.getX()%2 != 0) && Math.abs(e1.getY() - e2.getY())<=b1.getRange() && Math.min(e1.getYmax(),e2.getYmax())!=Math.max(e1.getYmin(),e2.getYmin())) {
                        b1.setRemaningSeconds(b2.getRemainingSeconds());
                        return b1;
                    }
                }
            }
            return b1;
        }


        /**
         * Getting bomb explosion bounds and elements to delete
         */
        public ExplosionBounds bombExplosionCollision(Bomb bomb) {
            Rectangle[] toDelete = new Rectangle[4];
            int bombX, bombY;

            //pobieramy wsporzedne bomby sprawdzamy czy element kolidujacy nie znajduje sie na przekatnej

            bombX = calculateTileCord(bomb.getX());
            bombY = calculateTileCord(bomb.getY());

            //inicjujemy największe i najmniejsze wartosci wsporzednych dla eksplozji
                        //zaincjowane w taki sposob aby najmniejsza wartosc byla mniejsza od najmniejszej mozliwej na planszy, analogicznie max

            int xmin = bombX - bomb.getRange(),xmax = bombX + bomb.getRange() ,ymin = bombY - bomb.getRange() ,ymax = bombY + bomb.getRange();

            //sprawdzamy czy zasieg bomby koliduje z obiektami ktore wybuchają

            for (int i = 0; i < explodableElements.size(); i++) {
                if (Intersector.overlaps(bomb.getBounds(), explodableElements.get(i))) {


                    //graficzne usuwanie elementu

                    int x = calculateTileCord(explodableElements.get(i).x);
                    int y = calculateTileCord(explodableElements.get(i).y);

                    if (bombX == x) {

                        if(y > bombY && y <= ymax)
                        {
                            ymax = y;
                            toDelete[0]=explodableElements.get(i);

                        }
                        if(y < bombY && y >= ymin)
                        {
                            ymin = y;
                            toDelete[1]=explodableElements.get(i);
                        }

                    }
                    if(bombY == y)
                    {
                        if(x > bombX && x <= xmax)
                        {
                            xmax = x;
                            toDelete[2]=explodableElements.get(i);
                        }
                        if(x < bombX && x >= xmin)
                        {
                            xmin = x;
                            toDelete[3]=explodableElements.get(i);
                        }
                    }

                }
                if(bombX%2==0)
                {
                    ymax = bombY;
                    ymin = bombY;
                }
                else if(bombY%2==0)
                {
                    xmax = bombX;
                    xmin = bombX;
                }
            }

            ymin = MathUtils.clamp(ymin,1,MAP_HEIGHT - 2);
            ymax = MathUtils.clamp(ymax,1,MAP_HEIGHT - 2);
            xmin = MathUtils.clamp(xmin,1,MAP_WIDTH - 2);
            xmax = MathUtils.clamp(xmax,1,MAP_WIDTH - 2);

            return new ExplosionBounds(bombX,bombY,ymin,ymax,xmin,xmax,toDelete);
        }


    }



}
