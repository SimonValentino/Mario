package com.svalentino.tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractableObject extends PhysicalObject {
    protected TiledMapTile tile;

    public InteractableObject(World world, TiledMap map, Rectangle hitbox) {
        super(world, map, hitbox);
    }

    public abstract void hitMarioHead();
}
