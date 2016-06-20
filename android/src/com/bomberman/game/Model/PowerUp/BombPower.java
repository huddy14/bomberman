package com.bomberman.game.Model.PowerUp;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by necro on 20.06.2016.
 */

public class BombPower extends PowerUp{
    public static String Name =  "bombs";
    public  BombPower  (Vector2 pos){
        super(pos);
        state = State.NONE;
        name = "bombs";
    }
}
