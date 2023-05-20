package com.svalentino.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.svalentino.WorldRenderer;
import com.svalentino.tiles.InteractableObject;

public abstract class Enemy extends Sprite implements Disposable, InteractableObject {
    protected World world;
    protected Body body;

    public Enemy(WorldRenderer worldRenderer, float x, float y) {
        this.world = worldRenderer.getWorld();
        setPosition(x, y);
    }
}
