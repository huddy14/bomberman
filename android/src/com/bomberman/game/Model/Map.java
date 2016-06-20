package com.bomberman.game.Model;

import android.util.Log;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.Constants;
import com.bomberman.game.Interfaces.IMovingModel;

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
    private int id;

    final int MAP_WIDTH;
    final int MAP_HEIGHT;

    private TiledMapTileLayer layer;


    public Map(TiledMap map) {
        this.map = map;
        MAP_WIDTH = map.getProperties().get("width", Integer.class);
        MAP_HEIGHT = map.getProperties().get("height", Integer.class);
        this.solidElements = getElements(map, Constants.SOLID_OBJECT);
        this.explodableElements = getElements(map, Constants.EXPLODING_OBJECT);
        collisionElements.addAll(explodableElements);
        collisionElements.addAll(solidElements);

        this.layer = (TiledMapTileLayer)map.getLayers().get(Constants.EXPLODING_LAYER);//tile layer explodin

    }

    public Rectangle getRectangle(){
        return collisionElements.get(id);
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
        return this.collisionDetector;
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

        Log.w("collision:",""+collisionElements.size()+" Explodable elements: "+explodableElements.size());



    }

    private void setTileToNull(int x, int y)
    {
        if(layer.getCell(x,y)!=null)
            layer.getCell(x,y).setTile(null);
    }


    public class CollisionDetector {
//        private int[][] solidElementsCoords = new int[solidElements.size()][2];
//        private int[][] explodingElements;
//
//        public CollisionDetector()
//        {
//             solidElementsCoords = fillCoordsTable(solidElements,solidElementsCoords);
//        }

//        private int[][] fillCoordsTable(ArrayList<Rectangle>list, int[][]tab)
//        {
//            for(int i = 0 ; i<list.size(); i++)
//            {
//
//                tab[i][0] = calculateTileCord(list.get(i).getX());
//                tab[i][1] = calculateTileCord(list.get(i).getY());
//
//            }
//            return tab;
//        }

        //TODO: generalnie ogarnac nazwy tych funkcji
        private int calculateTileCord(float x)
        {
            return (int)(x - (x % Constants.TILE_SIZE)) / Constants.TILE_SIZE;
        }

        public boolean playerCollision(Rectangle rectangle)
        {
            for (int i = 0; i < collisionElements.size(); i++)
                if (Intersector.overlaps(collisionElements.get(i), rectangle)) {
                    id = i;
                    return true;
                }
            return false;
        }

        public boolean playerGhostsCollision(Rectangle player, ArrayList<Ghost> ghosts)
        {
            for (Ghost ghost : ghosts) {
                if (Intersector.overlaps(player, ghost.getBounds()))
                    return true;
            }
            return false;

        }
        public boolean movingModelBombCollision(IMovingModel model, Bomb bomb)
        {
            return Intersector.overlaps(model.getBounds(),bomb.getExplosionBounds().getVerticalRectangle()) ||
                    Intersector.overlaps(model.getBounds(),bomb.getExplosionBounds().getHorizontalRectangle());
        }
        //sprawdzenie czy bomby nie są tak położone, że wzajemnie się detonują
        //TODO: bobmy wybuchaja razem nawet jesli pomiedzy jest explodable object
        public Bomb bombCollision(Bomb b1, ArrayList<Bomb> bombs)
        {
            for(Bomb b2 : bombs) {
                ExplosionBounds e1 = b1.getExplosionBounds();
                ExplosionBounds e2 = b2.getExplosionBounds();

                //sprawdzamy czy promien razenia jednej bomby nachodzi na promien razenia reszty
                if (Intersector.overlaps(e1.getHorizontalRectangle(), e2.getHorizontalRectangle())) {

                    if ((e1.getY() % 2 != 0 && e2.getY()%2 != 0) && Math.abs(e1.getX() - e2.getX())<=b1.getRange()) {
                        b1.setRemaningSeconds(b2.getRemainingSeconds());
                        return b1;
                    }
                }
                else if(Intersector.overlaps(e1.getVerticalRectangle(), e2.getVerticalRectangle()))
                {
                    if ((e1.getX() % 2 != 0 && e2.getX()%2 != 0) && Math.abs(e1.getY() - e2.getY())<=b1.getRange()) {
                        b1.setRemaningSeconds(b2.getRemainingSeconds());
                        return b1;
                    }
                }
            }
            return b1;
        }
        //TODO: sprobowac prosciej zapisac
        public ExplosionBounds bombExplosionCollision(Bomb bomb) {
            Rectangle[] toDelete = new Rectangle[4];
            int bombX, bombY;

            //pobieramy wsporzedne bomby sprawdzamy czy element kolidujacy nie znajduje sie na przekatnej
            bombX = calculateTileCord(bomb.getX());
            bombY = calculateTileCord(bomb.getY());
            //inicjujemy największe i najmniejsze wartosci wsporzednych dla eksplozji
            //zaincjowane w taki sposob aby najmniejsza wartosc byla mniejsza od najmniejszej mozliwej na planszy, analogicznie max
            int xmin = bombX - bomb.getRange(),xmax = bombX + bomb.getRange() ,ymin = bombY - bomb.getRange() ,ymax = bombY + bomb.getRange();
            //int xmin = -1,xmax = 20 ,ymin = -1 ,ymax = 20;
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
            Log.w("x,y,ymin,ymax,xmin,xmax",""+bombX+" "+bombY+" "+ymin+" "+ymax+" "+xmin+" "+xmax);

            return new ExplosionBounds(bombX,bombY,ymin,ymax,xmin,xmax,toDelete);
        }


    }



}
