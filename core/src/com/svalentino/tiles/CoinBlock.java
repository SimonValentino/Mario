package com.svalentino.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;

public class CoinBlock extends InteractableObject {
    private TiledMapTileSet tileset;
    private Sound coinBlockSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/coin.wav"));
    private Sound blankBlockSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/bump.wav"));

    public CoinBlock(World world, TiledMap map, Rectangle hitbox) {
        super(world, map, hitbox);
        fixture.setUserData(this);
        setCategory(MarioGame.COIN_BLOCK_BYTE);
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
