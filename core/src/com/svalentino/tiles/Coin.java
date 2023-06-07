package com.svalentino.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.SoundManager;
import com.svalentino.WorldRenderer;

public class Coin extends PhysicalObject implements InteractableObject {

    public Coin(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setSensor(true);
        fixture.setUserData(this);
        setCategory(MarioGame.COIN_COl);
    }

    @Override
    public void hitMarioHead() {
        setCategory(MarioGame.DESTROYED_COL);
        getCell().setTile(null);
        GameHud.updateScore(200);
        GameHud.updateCoins(1);
        SoundManager.COIN_SOUND.play();
    }
}
