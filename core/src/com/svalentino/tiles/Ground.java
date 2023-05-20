package com.svalentino.tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class Ground extends PhysicalObject {
    public Ground(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        setCategory(MarioGame.DEFAULT_BYTE);
    }
}
