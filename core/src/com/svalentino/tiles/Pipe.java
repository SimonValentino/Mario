package com.svalentino.tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Pipe extends PhysicalObject {
    public Pipe(World world, TiledMap map, Rectangle hitbox) {
        super(world, map, hitbox);
        fixture.setUserData(this);
    }
}
