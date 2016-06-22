package com.bomberman.game.Screen.CameraManagment;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.bomberman.game.Constants;
import com.bomberman.game.Model.Bomberman;

/**
 * Created by huddy on 6/2/16.
 */
public class MapCamera extends OrthographicCamera {
    //Map properties to get TiledMap width and height
    private MapProperties map;
    //player instacne to get velocity and movement directions
    private Bomberman player;

    private int mapLeft,mapRight,mapTop,mapBottom;

    float playerVelocity;
    int mapWidth, mapHeight;

    boolean firstCall = true;

    //float moveLeft,moveRight,moveBottom, moveTop;

    public MapCamera(MapProperties map, Bomberman player)
    {
        super();
        this.player = player;
        this.map = map;
        mapWidth = map.get("width", Integer.class);
        mapHeight = map.get("height", Integer.class);
        int tilePixelWidth = map.get("tilewidth", Integer.class);
        int tilePixelHeight = map.get("tileheight", Integer.class);
        playerVelocity = player.getVelocity();
        mapLeft = 0;
        mapRight = mapWidth * tilePixelWidth;
        mapTop = mapHeight *tilePixelHeight;
        mapBottom = 0;

    }

    public int getMapWidth(){
        return mapWidth * Constants.TILE_SIZE;
    }

    public int getMapHeight(){
        return mapHeight * Constants.TILE_SIZE;
    }


    @Override
    public void update() {

        //here we are updating cameras position
        float halfWidth = this.viewportWidth * .5f;
        float halfHeight = this.viewportHeight * .5f;
        //if player is moving we make sure we aint gonna move through map boundries
        if(player.getStatus().equals(Bomberman.Status.MOVE) || firstCall)//&& !player.isColiding()
        {
//            switch (player.getDirection())
//            {
//                case UP:
//                    //this.position.y = this.position.y + playerVelocity;
//
//                    this.position.y = MathUtils.clamp(player.getPosition().y, mapBottom + halfHeight,mapTop - halfHeight);
//                    break;
//                case DOWN:
//                    this.position.y = MathUtils.clamp(player.getPosition().y, mapBottom+ halfHeight,mapTop-halfHeight);
//                    break;
//                case LEFT:
//                    this.position.x = MathUtils.clamp(player.getPosition().x, mapLeft + halfWidth,mapRight - halfWidth);
//                    break;
//                case RIGHT:
//                    this.position.x = MathUtils.clamp(player.getPosition().x, mapLeft + halfWidth,mapRight - halfWidth);
//                    //this.position.x = this.position.x + playerVelocity;
//
//                    break;
//                default:break;
//            }
            this.position.y = MathUtils.clamp(player.getPosition().y, mapBottom + halfHeight,mapTop - halfHeight);
            this.position.x = MathUtils.clamp(player.getPosition().x, mapLeft + halfWidth,mapRight - halfWidth);

            firstCall = false;

        }
        super.update();
    }
}
