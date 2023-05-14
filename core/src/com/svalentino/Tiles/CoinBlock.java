package com.svalentino.Tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class CoinBlock extends Tile {
    public CoinBlock(World world, TiledMap map, Rectangle hitbox) {
        super(world, map, hitbox);
    }
}
