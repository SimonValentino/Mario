package com.svalentino.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.SoundManager;
import com.svalentino.WorldRenderer;

public class Koopa extends Enemy {
    private WorldRenderer wr;

    private final float koopaWidth = MarioGame.TILE_LENGTH / 2 - 1f;
    private final float koopaHeight = MarioGame.TILE_LENGTH / 2 - 1f;

    private KoopaState currentState = KoopaState.WALKING;
    private KoopaState previousState;
    private float stateTime = 0.0f;

    private boolean isDead = false;
    private boolean hasDied = false;

    public Koopa(WorldRenderer worldRenderer, float x, float y) {
        super(worldRenderer, x, y);

        wr = worldRenderer;
        movement = new Vector2(5f * (Math.random() - 0.5 >= 0 ? 1 : -1), 0);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(koopaWidth * MarioGame.SCALE, koopaHeight * MarioGame.SCALE);

        fixtureDef.filter.categoryBits = MarioGame.ENEMY_COL;
        fixtureDef.filter.maskBits = MarioGame.DEFAULT_COL | MarioGame.BRICK_COL |
                MarioGame.COIN_BLOCK_COL | MarioGame.ENEMY_COL
                | MarioGame.MARIO_COL | MarioGame.GROUND_COL;

        fixtureDef.shape = hitbox;
        body.createFixture(fixtureDef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-6f, 9).scl(MarioGame.SCALE);
        vertices[1] = new Vector2(6f, 9).scl(MarioGame.SCALE);
        vertices[2] = new Vector2(-3f, 3).scl(MarioGame.SCALE);
        vertices[3] = new Vector2(3f, 3).scl(MarioGame.SCALE);
        head.set(vertices);

        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = MarioGame.ENEMY_HEAD_COL;
        fixtureDef.filter.maskBits = MarioGame.MARIO_COL;
        // How much mario will bounce up
        fixtureDef.restitution = 0.9f;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void update(float dt) {
        if (hasDied && !isDead) {
            world.destroyBody(body);
            isDead = true;
        }

        stateTime += dt;

        if (currentState == KoopaState.SHELL && stateTime > 4) {
            currentState = KoopaState.WALKING;
            movement.x = 5f * (Math.random() - 0.5 >= 0 ? 1 : -1);
            stateTime = 0;
        }

        body.setLinearVelocity(movement);
    }

    @Override
    public void receiveHit() {
        if (currentState != KoopaState.SHELL) {
            currentState = KoopaState.SHELL;
            movement.x = 0;
            stateTime = 0f;
        }

        SoundManager.ENEMY_HIT_SOUND.play();
        GameHud.updateScore(300);
    }

    @Override
    public void obliterate() {
        hasDied = true;
        SoundManager.ENEMY_HIT_SOUND.play();
        GameHud.updateScore(300);
    }

    public void kickShell(boolean kickingLeft) {
        if (kickingLeft)
            movement.x = -10f;
        else
            movement.x = 10f;

        currentState = KoopaState.MOVING_SHELL;
        stateTime = 0;
    }

    public KoopaState getCurrentState() {
        return currentState;
    }

    public KoopaState getPreviousState() {
        return previousState;
    }
}