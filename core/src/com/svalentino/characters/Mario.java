package com.svalentino.characters;

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
import com.svalentino.WorldRenderer;
import com.svalentino.SoundManager;
import com.svalentino.GameHud;
public class Mario extends Sprite implements Disposable {
    private final World world;
    private Body mario;

    // Body dimensions
    public static float marioWidth = MarioGame.TILE_LENGTH / 2 - 0.5f;
    public static float marioHeight = MarioGame.TILE_LENGTH / 2 - 0.5f;
    private final float marioMaxSpeed = 10f;

    // Sounds

    public Mario(WorldRenderer worldRenderer) {
        this.world = worldRenderer.getWorld();

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((marioWidth + MarioGame.TILE_LENGTH * 5) * MarioGame.SCALE,
                (marioHeight + MarioGame.TILE_LENGTH) * MarioGame.SCALE);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mario = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(marioWidth * MarioGame.SCALE, marioHeight * MarioGame.SCALE);

        fixtureDef.filter.categoryBits = MarioGame.MARIO_COL;
        fixtureDef.filter.maskBits = MarioGame.DEFAULT_COL | MarioGame.COIN_COl
                | MarioGame.BRICK_COL | MarioGame.COIN_BLOCK_COL | MarioGame.PIPE_COL
                | MarioGame.ENEMY_HEAD_COL;

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
            SoundManager.JUMP_SOUND.play(0.05f);
        }
    }

    public void moveRight() {
        if (isBelowMaxSpeedRight())
            mario.applyLinearImpulse(new Vector2(0.7f, 0), mario.getWorldCenter(), true);
    }

    public void moveLeft() {
        if (isBelowMaxSpeedLeft())
            mario.applyLinearImpulse(new Vector2(-0.7f, 0), mario.getWorldCenter(), true);
    }

    public void resetPosition() {
        mario.setTransform(new Vector2(5f, 5f), mario.getAngle());
    }
    public boolean isDead() {
        if(getYCoordinate() <= -8 || GameHud.worldTimer <= 3)
            return true;
        return false;
    }
    public float getXCoordinate() {
        return mario.getWorldCenter().x;
    }
    public float getYCoordinate() {
        return mario.getWorldCenter().y;
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
