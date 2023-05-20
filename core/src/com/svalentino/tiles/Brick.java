package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;

public class Brick extends InteractableObject {
    private Sound brickBreakingSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/breakblock.wav"));

    public Brick(World world, TiledMap map, Rectangle hitbox) {
        super(world, map, hitbox);
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
