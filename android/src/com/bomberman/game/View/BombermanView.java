package com.bomberman.game.View;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bomberman.game.Model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huddy on 6/1/16.
 */
public class BombermanView extends Sprite {
    private Player player;
    private Map<Player.Direction,Animation>animations=new HashMap<>();
    private int i;

    public BombermanView(Player player)
    {
        this.player = player;
        animations.put(Player.Direction.DOWN, new Animation(1/10f,new TextureAtlas("Bomberman/Front/BombermanFront.pack").getRegions()));
        animations.put(Player.Direction.UP, new Animation(1/10f,new TextureAtlas("Bomberman/Back/BombermanBack.pack").getRegions()));
        animations.put(Player.Direction.LEFT, new Animation(1/10f,new TextureAtlas("Bomberman/SideLeft/BombermanSideLeft.pack").getRegions()));
        animations.put(Player.Direction.RIGHT, new Animation(1/10f,new TextureAtlas("Bomberman/Side/BombermanSide.pack").getRegions()));

    }

    public Animation getAnimation(Player.Direction dir)
    {
        return animations.get(dir);
    }
}
