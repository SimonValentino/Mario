package com.svalentino.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;
import com.svalentino.tiles.PhysicalObject;

public class Flagpole extends PhysicalObject {
    public Flagpole(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setSensor(true);
        fixture.setUserData(this);
        setCategory(MarioGame.FLAGPOLE_COL);
    }
}
