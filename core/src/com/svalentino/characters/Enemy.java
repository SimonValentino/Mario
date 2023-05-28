package com.svalentino.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;
import com.svalentino.tiles.InteractableObject;

public abstract class Enemy extends Sprite implements Disposable {
    protected World world;
    protected Body body;
    public Vector2 movement;

    public Enemy(WorldRenderer worldRenderer, float x, float y) {
        this.world = worldRenderer.getWorld();
        setPosition(x, y);
        movement = new Vector2(3f * (Math.random() - 0.5 >= 0 ? 1 : -1), 0);
    }

    public abstract void update();
    public abstract void receiveHit();
    public void reverse() {
        movement.x = -movement.x;
    }
}
