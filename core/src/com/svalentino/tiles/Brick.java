package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;
import com.svalentino.SoundManager;

public class Brick extends PhysicalObject implements InteractableObject {


    public Brick(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(MarioGame.BRICK_COL);
    }

    @Override
    public void hitMarioHead() {
        Gdx.app.log("Brick", "Collision");
        setCategory(MarioGame.DESTROYED_COL);
        getCell().setTile(null);
        GameHud.updateScore(50);
        SoundManager.brickBreakingSound.play();
    }
}
