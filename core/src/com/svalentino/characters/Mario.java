package com.svalentino.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.SoundManager;
import com.svalentino.screens.PlayScreen;

public class Mario extends Sprite implements Disposable {
    // Body dimensions
    public static float marioWidth = MarioGame.TILE_LENGTH / 2 - 1.55f;
    public static float marioHeight = MarioGame.TILE_LENGTH / 2 - 1.55f;
    private final World world;
    private final Body mario;
    private final float marioMaxSpeed = 12f;
    private final Animation marioRun;
    private final Animation marioJump;
    public TextureRegion marioStand;
    public State currentState;
    public State previousState;
    private boolean isDead;
    private boolean flagpoleHit = false;
    private float stateTimer;
    private boolean runningRight;

    private boolean walkedOff = false;
    private boolean wentThroughExitDoor = false;

    public Mario(World world) {
        super(PlayScreen.atlas.findRegion("small_mario"));
        this.world = world;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 15, 16) );
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        marioJump = new Animation(0.1f, frames);

        this.isDead = false;

        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setBounds(0, 0, 1, 1);
        setRegion(marioStand);


        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((marioWidth + MarioGame.TILE_LENGTH * 5) * MarioGame.SCALE,
                (marioHeight + MarioGame.TILE_LENGTH) * MarioGame.SCALE);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mario = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
//        CircleShape hitbox = new CircleShape();
//        hitbox.setRadius(6 * MarioGame.SCALE);
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(marioWidth * MarioGame.SCALE, marioHeight * MarioGame.SCALE);

        fixtureDef.filter.categoryBits = MarioGame.MARIO_COL;
        fixtureDef.filter.maskBits = MarioGame.GROUND_COL | MarioGame.COIN_COl
                | MarioGame.BRICK_COL | MarioGame.COIN_BLOCK_COL | MarioGame.ENEMY_COL
                | MarioGame.ENEMY_HEAD_COL | MarioGame.DEFAULT_COL | MarioGame.FLAGPOLE_COL
                | MarioGame.EXIT_DOOR_COL;

        fixtureDef.shape = hitbox;
        mario.createFixture(fixtureDef).setUserData(this);

        EdgeShape top = new EdgeShape();
        top.set(new Vector2((-marioHeight / 1.7f) * MarioGame.SCALE, (marioHeight + 0.1f) * MarioGame.SCALE),
                new Vector2((marioHeight / 1.7f) * MarioGame.SCALE, (marioHeight + 0.1f) * MarioGame.SCALE));
        fixtureDef.shape = top;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = MarioGame.MARIO_HEAD_COL;
        mario.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float delta) {
        setPosition(getXCoordinate() - getWidth() / 2, getYCoordinate() - getHeight() / 2);
        setRegion(getFrame(delta));
        if (flagpoleHit) {
            mario.setLinearVelocity(new Vector2(0f, -10f));
        }
    }
    public void walkOffStage(float delta) {
        if (wentThroughExitDoor)
            setPosition(0, 0);
        else {
            setPosition(getXCoordinate() - getWidth() / 2, getYCoordinate() - getHeight() / 2);
            setRegion(getFrame(delta));
        }

        if (!walkedOff) {
            SoundManager.STAGE_WIN_SOUND.play();
            walkedOff = true;
        }

        mario.setLinearVelocity(new Vector2(15f, -60f));
    }

    public void goThroughExitDoor() {
        wentThroughExitDoor = true;
    }

    public void bounceUpAfterEnemyHit() {
        float yVelocity = mario.getLinearVelocity().y;
        mario.applyLinearImpulse(new Vector2(0f, yVelocity > -10 ? yVelocity * -1f + 9f : yVelocity * (yVelocity > -25f ? -1.8f : -1.6f)), mario.getWorldCenter(), true);
    }

    public void hitFlagpole() {
        SoundManager.THEME_SONG.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        SoundManager.FLAGPOLE_SOUND.play();
        flagpoleHit = true;
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }
        if ((mario.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((mario.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (mario.getLinearVelocity().y > 0 || (mario.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (mario.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (mario.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void die() {
        resetPosition();
    }

    public void jump() {
        if (isOnGround()) {
            mario.applyLinearImpulse(new Vector2(0, 24f), mario.getWorldCenter(), true);
            SoundManager.JUMP_SOUND.play(0.05f);
        }
    }

    public void moveRight() {
        if (isBelowMaxSpeedRight())
            mario.applyLinearImpulse(new Vector2(0.65f, 0), mario.getWorldCenter(), true);
    }

    public void moveLeft() {
        if (isBelowMaxSpeedLeft())
            mario.applyLinearImpulse(new Vector2(-0.65f, 0), mario.getWorldCenter(), true);
    }

    public void resetPosition() {
        mario.setTransform(new Vector2(5f, 2f), mario.getAngle());
    }

    public boolean isDead() {
        return getYCoordinate() <= -8 || GameHud.worldTimer <= 3 || isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public float getXCoordinate() {
        return mario.getPosition().x;
    }

    public float getYCoordinate() {
        return mario.getWorldCenter().y;
    }

    private boolean isBelowMaxSpeedRight() {
        return mario.getLinearVelocity().x < marioMaxSpeed;
    }

    private boolean isBelowMaxSpeedLeft() {
        return mario.getLinearVelocity().x > -marioMaxSpeed;
    }

    private boolean isOnGround() {
        return mario.getLinearVelocity().y <= 0.01 && mario.getLinearVelocity().y >= -0.01;
    }

    public boolean isFlagpoleHit() {
        return flagpoleHit;
    }

     public Body getBody() {
        return mario;
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    public enum State {FALLING, JUMPING, STANDING, RUNNING}
}
