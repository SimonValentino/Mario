package com.svalentino.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class DefaultTile extends PhysicalObject {
    public DefaultTile(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(MarioGame.DEFAULT_COL);
    }
}
