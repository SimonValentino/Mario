package com.svalentino.tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Coin extends Tile {
    public Coin(World world, TiledMap map, Rectangle hitbox) {
        super(world, map, hitbox);
    }

    @Override
    public void hitMarioTop() {

    }
}
