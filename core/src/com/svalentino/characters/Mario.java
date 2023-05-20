package com.svalentino.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.svalentino.MarioGame;

public class Mario extends Sprite implements Disposable {
    private final World world;
    private Body mario;

    // Body dimensions
    public static float marioWidth = MarioGame.TILE_LENGTH / 2 - 0.5f;
    public static float marioHeight = MarioGame.TILE_LENGTH / 2 - 0.5f;
    private final float marioMaxSpeed = 10f;

    // Sounds
    private final Sound jumpSound; 

    public Mario(World world) {
        this.world = world;
        this.jumpSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario Jump Sound Effect.mp3"));

        defBody();
    }

    private void defBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((marioWidth + MarioGame.TILE_LENGTH * 5) * MarioGame.SCALE,
                (marioHeight + MarioGame.TILE_LENGTH) * MarioGame.SCALE);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mario = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(marioWidth * MarioGame.SCALE, marioHeight * MarioGame.SCALE);

        fixtureDef.filter.categoryBits = MarioGame.MARIO_BYTE;
        fixtureDef.filter.maskBits = MarioGame.DEFAULT_BYTE | MarioGame.COIN_BYTE
                | MarioGame.BRICK_BYTE | MarioGame.COIN_BLOCK_BYTE;

        fixtureDef.shape = hitbox;
        mario.createFixture(fixtureDef);

        EdgeShape top = new EdgeShape();
        top.set(new Vector2((-marioHeight / 2f) * MarioGame.SCALE, (marioHeight + 0.1f) * MarioGame.SCALE),
                new Vector2((marioHeight / 2f) * MarioGame.SCALE, (marioHeight + 0.1f) * MarioGame.SCALE));
        fixtureDef.shape = top;
        fixtureDef.isSensor = true;
        mario.createFixture(fixtureDef).setUserData("head");

        EdgeShape bottom = new EdgeShape();
        bottom.set(new Vector2((-marioHeight / 2f) * MarioGame.SCALE, (-marioHeight - 0.1f) * MarioGame.SCALE),
                new Vector2((marioHeight / 2f) * MarioGame.SCALE, ((-marioHeight - 0.1f)) * MarioGame.SCALE));
        fixtureDef.shape = bottom;
        mario.createFixture(fixtureDef).setUserData("feet");
    }

    public void jump() {
        if (isOnGround()) {
            mario.applyLinearImpulse(new Vector2(0, 24f), mario.getWorldCenter(), true);
            jumpSound.play(0.05f);
        }
    }

    public void moveRight() {
        if (isBelowMaxSpeedRight())
            mario.applyLinearImpulse(new Vector2(0.7f, 0), mario.getWorldCenter(), true);
    }

    public void update(float dt) {
        setPosition(mario.getPosition().x - getWidth() / 2, mario.getPosition().y - getHeight() / 2);
    }

    public void moveLeft() {
        if (isBelowMaxSpeedLeft())
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

    private boolean isBelowMaxSpeedRight() {
        return mario.getLinearVelocity().x < marioMaxSpeed;
    }
    private boolean isBelowMaxSpeedLeft() {
        return mario.getLinearVelocity().x > - marioMaxSpeed;
    }

    private boolean isOnGround() {
        return mario.getLinearVelocity().y == 0;
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
