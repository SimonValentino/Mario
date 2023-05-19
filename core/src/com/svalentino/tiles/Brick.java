package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Brick extends InteractableObject {
    public Brick(World world, TiledMap map, Rectangle hitbox) {
        super(world, map, hitbox);
        fixture.setUserData(this);
    }

    @Override
    public void hitMarioHead() {
        Gdx.app.log("Brick", "Collision");
    }
}
