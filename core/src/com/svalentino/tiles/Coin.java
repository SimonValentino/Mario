package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;

public class Coin extends InteractableObject {
    public Coin(World world, TiledMap map, Rectangle hitbox) {
        super(world, map, hitbox);
        fixture.setSensor(true);
        fixture.setUserData(this);
        setCategory(MarioGame.COIN_BYTE);
    }

    @Override
    public void hitMarioHead() {
        Gdx.app.log("Coin", "Collision");
        setCategory(MarioGame.DESTROYED_BYTE);
        getCell().setTile(null);
        GameHud.updateScore(200);
    }

    public void hitMarioFeet() {
        hitMarioHead();
    }
}
