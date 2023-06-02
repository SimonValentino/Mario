package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteManager extends Sprite {

    public static TextureRegion small_mario_stand;
    public static TextureAtlas atlas;

    public SpriteManager () {
        atlas = new TextureAtlas(Gdx.files.internal("Downloads/Mario_and_Enemies.pack"));
        small_mario_stand = atlas.findRegion("small_mario_stand");
        setBounds(500, 5, 32, 100);
        
    }
    public TextureRegion getSmall_mario_stand() {
        return small_mario_stand;
    }
    public static TextureAtlas getAtlas() {
        return atlas;
    }

}
