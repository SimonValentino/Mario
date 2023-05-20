package com.svalentino.tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public abstract class InteractableObject extends PhysicalObject {
    protected TiledMapTile tile;

    public InteractableObject(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
    }

    public void setCategory(byte filterByte) {
        Filter filter = new Filter();
        filter.categoryBits = filterByte;
        fixture.setFilterData(filter);
    }

    public abstract void hitMarioHead();
}
