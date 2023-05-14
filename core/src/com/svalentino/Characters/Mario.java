package com.svalentino.Characters;

import org.w3c.dom.css.Rect;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.svalentino.MarioGame;

public class Mario extends Sprite {
    private final float scale = 1 / MarioGame.BOX_2D_SCALE;

    private final World world;
    private final Body mario;

    // Body dimensions
    private final float marioWidth = 8f;
    private final float marioHeight = 8f;
    private final float marioMaxSpeed = 12.5f;

    public Mario(World world) {
        this.world = world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((MarioGame.TILE_LENGTH / 2 + MarioGame.TILE_LENGTH * 5) * scale,
                (MarioGame.TILE_LENGTH / 2 + MarioGame.TILE_LENGTH) * scale);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mario = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(marioWidth * scale, marioHeight * scale);

        fixtureDef.shape = hitbox;
        mario.createFixture(fixtureDef);
    }

    public void jump() {
        mario.applyLinearImpulse(new Vector2(0, 25f), mario.getWorldCenter(), true);
    }

    public void moveRight() {
        if (isBelowMaxSpeed())
            mario.applyLinearImpulse(new Vector2(0.5f, 0), mario.getWorldCenter(), true);
    }

    public void moveLeft() {
        if (isBelowMaxSpeed())
            mario.applyLinearImpulse(new Vector2(-0.5f, 0), mario.getWorldCenter(), true);
    }

    public float getXCoordinate() {
        return mario.getWorldCenter().x;
    }

    private boolean isBelowMaxSpeed() {
        return mario.getLinearVelocity().x < marioMaxSpeed && mario.getLinearVelocity().x > - marioMaxSpeed;
    }
}
