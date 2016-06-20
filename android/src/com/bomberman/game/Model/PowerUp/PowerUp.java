package com.bomberman.game.Model.PowerUp;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by necro on 20.06.2016.
 */
public class PowerUp {

    public enum State {NONE, TAKEN, DESTROYED };
    public State state;
    public static  float SIZE = 1f;
    boolean isHidden;
    protected  String name ;
    protected Vector2 position = new Vector2();
    protected Rectangle bounds = new Rectangle();

    public PowerUp  (Vector2 pos){
        this.position = pos;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
        name = "HiddenObject";
    }

    public Vector2 getPosition() {
        return position;
    }
    public String getName(){
        return name;
    }

    public Rectangle getBounds() {
        return bounds;

    }
}
