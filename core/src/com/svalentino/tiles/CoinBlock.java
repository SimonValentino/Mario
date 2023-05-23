package com.svalentino.tiles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;
import com.svalentino.SoundManager;

public class CoinBlock extends PhysicalObject implements InteractableObject {
    private TiledMapTileSet tileset;

    public CoinBlock(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(MarioGame.COIN_BLOCK_COL);
        tileset = map.getTileSets().getTileSet("MarioTileset");

    }

    @Override
    public void hitMarioHead() {
        Gdx.app.log("Coin Block", "Collision");

        if (isBlankBlock())
            SoundManager.BLANK_BLOCK_SOUND.play();
        else
            SoundManager.COIN_BLOCK_SOUND.play();

        getCell().setTile(tileset.getTile(28));
        GameHud.updateScore(200);
        GameHud.updateCoins();

    }

    private boolean isBlankBlock() {
        return getCell().getTile().getId() == 28;
    }
}
