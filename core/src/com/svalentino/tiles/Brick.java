package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.utils.WorldRenderer;

public class Brick extends PhysicalObject implements InteractableObject {
    private Sound brickBreakingSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/breakblock.wav"));

    public Brick(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(MarioGame.BRICK_BYTE);
    }

    @Override
    public void hitMarioHead() {
        Gdx.app.log("Brick", "Collision");
        setCategory(MarioGame.DESTROYED_BYTE);
        getCell().setTile(null);
        GameHud.updateScore(50);
        brickBreakingSound.play();
    }
}
