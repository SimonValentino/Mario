package com.svalentino.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.svalentino.MarioGame;

public class Mario extends Sprite implements Disposable {
    private World world;
    private Body mario;

    // Body dimensions
    public static final float marioWidth = MarioGame.TILE_LENGTH / 2;
    public static final float marioHeight = MarioGame.TILE_LENGTH / 2;
    private final float marioMaxSpeed = 10f;

    // Sounds
    private final Sound jumpSound; 

    public Mario(World world) {
        this.world = world;
        jumpSound.setVolume(0.9f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((MarioGame.TILE_LENGTH / 2 + MarioGame.TILE_LENGTH * 5) * MarioGame.SCALE,
                (MarioGame.TILE_LENGTH / 2 + MarioGame.TILE_LENGTH) * MarioGame.SCALE);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mario = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(marioWidth * MarioGame.SCALE, marioHeight * MarioGame.SCALE);

        fixtureDef.shape = hitbox;
        mario.createFixture(fixtureDef);
    }

    public void jump() {
        if (isOnGround()) {
            mario.applyLinearImpulse(new Vector2(0, 24f), mario.getWorldCenter(), true);
            jumpSound.play(0.05f);
        }
    }

    public void moveRight() {
        if (!isOnGround()) {
            mario.applyLinearImpulse(new Vector2(0.7f, 0), mario.getWorldCenter(), true);
        }
        else if (isBelowMaxSpeed())
            mario.applyLinearImpulse(new Vector2(0.7f, 0), mario.getWorldCenter(), true);
    }

    public void moveLeft() {
        if (!isOnGround()) {
            mario.applyLinearImpulse(new Vector2(-0.7f, 0), mario.getWorldCenter(), true);
        }
        else if (isBelowMaxSpeed())
            mario.applyLinearImpulse(new Vector2(-0.7f, 0), mario.getWorldCenter(), true);
    }

    public void resetPosition() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((MarioGame.TILE_LENGTH / 2 + MarioGame.TILE_LENGTH * 5) * MarioGame.SCALE,
                (MarioGame.TILE_LENGTH / 2 + MarioGame.TILE_LENGTH) * MarioGame.SCALE);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mario = world.createBody(bodyDef);
    }

    public float getXCoordinate() {
        return mario.getWorldCenter().x;
    }

    private boolean isBelowMaxSpeed() {
        return mario.getLinearVelocity().x < marioMaxSpeed && mario.getLinearVelocity().x > - marioMaxSpeed;
    }

    private boolean isOnGround() {
        return mario.getLinearVelocity().y == 0;
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
