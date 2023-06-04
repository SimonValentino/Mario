package com.svalentino.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class ExitDoor extends PhysicalObject {
    public ExitDoor(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(MarioGame.EXIT_DOOR_COL);
    }
}
