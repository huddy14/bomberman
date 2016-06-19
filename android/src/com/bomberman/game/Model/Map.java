package com.bomberman.game.Model;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.Constants;

import java.util.ArrayList;

/**
 * Created by Patryk on 15.05.2016.
 */
public class Map {
    private TiledMap map;
    private ArrayList<Rectangle> solidElements = new ArrayList<>();
    private ArrayList<Rectangle> explodableElements = new ArrayList<>();
    private ArrayList<Rectangle> collisionElements = new ArrayList<>();
    private CollisionDetector collisionDetector= new CollisionDetector();

    private TiledMapTileLayer layer;


    public Map(TiledMap map) {
        this.map = map;
        this.solidElements = getElements(map, Constants.SOLID_OBJECT);
        this.explodableElements = getElements(map, Constants.EXPLODING_OBJECT);
        collisionElements.addAll(explodableElements);
        collisionElements.addAll(solidElements);

        this.layer = (TiledMapTileLayer)map.getLayers().get(Constants.EXPLODING_LAYER);//tile layer explodin

    }

    public ArrayList<Rectangle> getSolidElements() {
        return this.solidElements;
    }

    public ArrayList<Rectangle> getExplodableElements() {
        return this.explodableElements;
    }

    public void updateExplodableElements(Rectangle toDelete)
    {
        this.explodableElements.remove(toDelete);
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

    public TiledMap getTiledMap()
    {
        return this.map;
    }



    public ArrayList<Rectangle>getCollisionElements()
    {
        return this.collisionElements;
    }

    public CollisionDetector getCollisionDetector()
    {
        return collisionDetector;
    }

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



    }

    private void setTileToNull(int x, int y)
    {
        if(layer.getCell(x,y)!=null)
            layer.getCell(x,y).setTile(null);
    }


    public class CollisionDetector {
        private int[][] solidElementsCoords = new int[solidElements.size()][2];
        private int[][] explodingElements;

        public CollisionDetector()
        {
             solidElementsCoords = fillCoordsTable(solidElements,solidElementsCoords);
        }

        private int[][] fillCoordsTable(ArrayList<Rectangle>list, int[][]tab)
        {
            for(int i = 0 ; i<list.size(); i++)
            {

                tab[i][0] = calculateTileCord(list.get(i).getX());
                tab[i][1] = calculateTileCord(list.get(i).getY());

            }
            return tab;
        }

        private int calculateTileCord(float x)
        {
            return (int)(x - (x % Constants.TILE_SIZE)) / Constants.TILE_SIZE;
        }

        public boolean playerCollision(Rectangle rectangle)
        {
            for (int i = 0; i < collisionElements.size(); i++)
                if (Intersector.overlaps(collisionElements.get(i), rectangle))
                    return true;
            return false;

        }

        public ExplosionBounds bombExplosionCollision(Bomb bomb) {
            Rectangle[] toDelete = new Rectangle[4];
            int bombX, bombY;

            //pobieramy wsporzedne bomby sprawdzamy czy element kolidujacy nie znajduje sie na przekatnej
            bombX = calculateTileCord(bomb.getX());
            bombY = calculateTileCord(bomb.getY());
            //inicjujemy największe i najmniejsze wartosci wsporzednych dla eksplozji
            //zaincjowane w taki sposob aby najmniejsza wartosc byla mniejsza od najmniejszej mozliwej na planszy, analogicznie max
            int xmin = -1,xmax = 20 ,ymin = -1 ,ymax = 20;

            //sprawdzamy czy zasieg bomby koliduje z obiektami ktore wybuchają
            for (int i = 0; i < explodableElements.size(); i++) {
                if (Intersector.overlaps(bomb.getBounds(), explodableElements.get(i))) {


                    //graficzne usuwanie elementu
                    int x = calculateTileCord(explodableElements.get(i).x);
                    int y = calculateTileCord(explodableElements.get(i).y);

                    if (bombX == x) {
                        if(y > bombY && y < ymax)
                        {
                            ymax = y;
                            toDelete[0]=explodableElements.get(i);

                        }
                        else if(y < bombY && y > ymin)
                        {
                            ymin = y;
                            toDelete[0]=explodableElements.get(i);
                        }

                    }
                    else if(bombY == y)
                    {
                        if(x > bombX && x < xmax)
                        {
                            xmax = x;
                            toDelete[0]=explodableElements.get(i);
                        }
                        else if(x < bombX && x > xmin)
                        {
                            xmin = x;
                            toDelete[0]=explodableElements.get(i);
                        }
                    }

                }
            }
            return new ExplosionBounds(bombX,bombY,ymin,ymax,xmin,xmax,toDelete);
        }


    }



}
