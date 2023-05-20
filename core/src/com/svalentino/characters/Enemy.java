package com.svalentino.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public abstract class Enemy extends Sprite implements Disposable {
    protected World world;
    protected Body body;

    public Enemy(World world, float x, float y) {
        this.world = world;
        setPosition(x, y);
    }

    protected abstract void defBody();
}
