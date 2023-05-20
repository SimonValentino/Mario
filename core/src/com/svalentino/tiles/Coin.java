package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class Coin extends InteractableObject {
    private Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/coin.wav"));

    public Coin(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
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
        coinSound.play();
    }

    public void hitMarioFeet() {
        hitMarioHead();
    }
}
