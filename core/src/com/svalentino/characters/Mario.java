package com.svalentino.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.svalentino.SpriteManager;
public class Mario extends Sprite implements Disposable {
    private final World world;
    private Body mario;
    private boolean isDead;

    // Body dimensions
    public static float marioWidth = MarioGame.TILE_LENGTH / 2 - 0.5f;
    public static float marioHeight = MarioGame.TILE_LENGTH / 2 - 0.5f;
    private final float marioMaxSpeed = 11.4f;

    // Sounds

    public Mario(WorldRenderer worldRenderer) {
        super(worldRenderer.atlas.findRegion("small_marrio_stand"));
        this.isDead = false;
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
        fixtureDef.filter.maskBits = MarioGame.GROUND_COL | MarioGame.COIN_COl
                | MarioGame.BRICK_COL | MarioGame.COIN_BLOCK_COL | MarioGame.ENEMY_COL
                | MarioGame.ENEMY_HEAD_COL | MarioGame.DEFAULT_COL;

        fixtureDef.shape = hitbox;
        mario.createFixture(fixtureDef).setUserData(this);

        EdgeShape top = new EdgeShape();
        top.set(new Vector2((-marioHeight / 1.1f) * MarioGame.SCALE, (marioHeight + 0.1f) * MarioGame.SCALE),
                new Vector2((marioHeight / 1.1f) * MarioGame.SCALE, (marioHeight + 0.1f) * MarioGame.SCALE));
        fixtureDef.shape = top;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = MarioGame.MARIO_HEAD_COL;
        mario.createFixture(fixtureDef).setUserData(this);
    }
    public void update(float dt) {
        setPosition(getXCoordinate() - getWidth() / 2, getYCoordinate() - getHeight() / 2);
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

    public void bounceUpAfterEnemyHit() {
        mario.applyLinearImpulse(new Vector2(0f, 40f), mario.getWorldCenter(), true);
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
        return getYCoordinate() <= -8 || GameHud.worldTimer <= 3 || isDead;
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
        return mario.getLinearVelocity().x > - marioMaxSpeed;
    }

    private boolean isOnGround() {
        return mario.getLinearVelocity().y == 0;
    }

    public Body getBody() {
        return mario;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
