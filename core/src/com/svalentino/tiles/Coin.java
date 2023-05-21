package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class Coin extends PhysicalObject implements InteractableObject {
    private Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/coin.wav"));

    public Coin(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setSensor(true);
        fixture.setUserData(this);
        setCategory(MarioGame.COIN_COl);
    }

    @Override
    public void hitMarioHead() {
        Gdx.app.log("Coin", "Collision");
        setCategory(MarioGame.DESTROYED_COL);
        getCell().setTile(null);
        GameHud.updateScore(200);
        GameHud.updateCoins();
        coinSound.play();
    }

    public void hitMarioFeet() {
        hitMarioHead();
    }
}
