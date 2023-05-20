package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class CoinBlock extends PhysicalObject implements InteractableObject {
    private TiledMapTileSet tileset;
    private Sound coinBlockSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/coin.wav"));
    private Sound blankBlockSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/bump.wav"));

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
            blankBlockSound.play();
        else
            coinBlockSound.play();

        getCell().setTile(tileset.getTile(28));
        GameHud.updateScore(200);

    }

    private boolean isBlankBlock() {
        return getCell().getTile().getId() == 28;
    }
}
