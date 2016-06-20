package com.bomberman.game.Model.PowerUp;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by necro on 20.06.2016.
 */
public class Portal extends PowerUp {
    public Portal (Vector2 pos){
        super(pos);
        state = State.NONE;
        name = "Door";
    }
}
