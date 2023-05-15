package com.svalentino.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
    private final World world;
    private final Body mario;

    // Body dimensions
    public static final float marioWidth = MarioGame.TILE_LENGTH / 2;
    public static final float marioHeight = MarioGame.TILE_LENGTH / 2;
    private final float marioMaxSpeed = 10f;

    // Sounds
    private Music jumpSound = Gdx.audio.newMusic(Gdx.files.internal("assets/Downloads/Sounds & Music/Mario Jump - QuickSounds.com.mp3"));;

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
        fixtureDef.friction = 0.0f;
        mario.createFixture(fixtureDef);
    }

    public void jump() {
        if (isOnGround()) {
            mario.applyLinearImpulse(new Vector2(0, 24f), mario.getWorldCenter(), true);
            jumpSound.play();
        }
    }

    public void moveRight() {
        if (isBelowMaxSpeed())
            mario.applyLinearImpulse(new Vector2(0.7f, 0), mario.getWorldCenter(), true);
    }

    public void moveLeft() {
        if (isBelowMaxSpeed())
            mario.applyLinearImpulse(new Vector2(-0.7f, 0), mario.getWorldCenter(), true);
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