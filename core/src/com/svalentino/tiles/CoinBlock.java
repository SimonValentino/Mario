package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.svalentino.MarioGame;

public class CoinBlock extends InteractableObject {
    public CoinBlock(World world, TiledMap map, Rectangle hitbox) {
        super(world, map, hitbox);
        fixture.setUserData(this);
        setCategory(MarioGame.COIN_BLOCK_BYTE);
    }

    @Override
    public void hitMarioHead() {
        Gdx.app.log("Coin Block", "Collision");
    }
}
