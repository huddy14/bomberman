package com.bomberman.game.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.bomberman.game.Model.Player;

/**
 * Created by huddy on 6/2/16.
 */
public class MapCamera extends OrthographicCamera {
    //Map properties to get TiledMap width and height
    private MapProperties map;
    //player instacne to get velocity and movement directions
    private Player player;

    private int mapLeft,mapRight,mapTop,mapBottom;

    float playerVelocity;

    //float moveLeft,moveRight,moveBottom, moveTop;

    public MapCamera(MapProperties map, Player player)
    {
        super();
        this.player = player;
        this.map = map;

        int mapWidth = map.get("width", Integer.class);
        int mapHeight = map.get("height", Integer.class);
        int tilePixelWidth = map.get("tilewidth", Integer.class);
        int tilePixelHeight = map.get("tileheight", Integer.class);
        playerVelocity = player.getVelocity();
        mapLeft = 0;
        mapRight = mapWidth * tilePixelWidth;
        mapTop = mapHeight *tilePixelHeight;
        mapBottom = 0;

    }



    @Override
    public void update() {

        //here we are updating cameras position
        float halfWidth = this.viewportWidth * .5f;
        float halfHeight = this.viewportHeight * .5f;
        //if player is moving we make sure we aint gonna move through map boundries
        if(player.getStatus().equals(Player.Status.MOVE))
        {
            switch (player.getDirection())
            {
                case UP:
                    this.position.y = MathUtils.clamp(this.position.y + playerVelocity, mapBottom + halfHeight,mapTop - halfHeight);
                    break;
                case DOWN:
                    this.position.y = MathUtils.clamp(this.position.y - playerVelocity, mapBottom+ halfHeight,mapTop-halfHeight);
                    break;
                case LEFT:
                    this.position.x = MathUtils.clamp(this.position.x - playerVelocity, mapLeft + halfWidth,mapRight - halfWidth);
                    break;
                case RIGHT:
                    this.position.x = MathUtils.clamp(this.position.x + playerVelocity, mapLeft + halfWidth,mapRight - halfWidth);
                    break;
                default:break;
            }


        }
        super.update();
    }
}
