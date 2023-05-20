package com.svalentino.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.svalentino.MarioGame;
import com.svalentino.utils.WorldRenderer;

public class Pipe extends PhysicalObject {
    public Pipe(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(MarioGame.PIPE_BYTE);
    }
}
