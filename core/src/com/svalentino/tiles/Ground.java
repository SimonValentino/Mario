package com.svalentino.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class Ground extends PhysicalObject {
    public Ground(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        setCategory(MarioGame.GROUND_COL);
    }
}