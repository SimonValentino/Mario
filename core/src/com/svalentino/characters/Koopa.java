package com.svalentino.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class Koopa extends Enemy {
    public boolean isShell = false;

    private final float koopaWidth = MarioGame.TILE_LENGTH / 2 - 1f;
    private final float koopaHeight = MarioGame.TILE_LENGTH / 2 - 1f;
    private boolean isDead = false;
    private boolean hasDied = false;
    private float timeInState = 0.0f;

    public Koopa(WorldRenderer worldRenderer, float x, float y) {
        super(worldRenderer, x, y);

        movement = new Vector2(3f * (Math.random() - 0.5 >= 0 ? 1 : -1), 0);

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
        Vector2[] vericies = new Vector2[4];
        vericies[0] = new Vector2(-5.5f, 9).scl(MarioGame.SCALE);
        vericies[1] = new Vector2(5.5f, 9).scl(MarioGame.SCALE);
        vericies[2] = new Vector2(-5.5f, 3).scl(MarioGame.SCALE);
        vericies[3] = new Vector2(5.5f, 3).scl(MarioGame.SCALE);
        head.set(vericies);

        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = MarioGame.ENEMY_HEAD_COL;
        fixtureDef.filter.maskBits = MarioGame.MARIO_COL;
        // How much mario will bounce up
        fixtureDef.restitution = 0.75f;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        timeInState += dt;

        if (isShell && timeInState > 5) {
            isShell = false;
            movement = new Vector2(3f * (Math.random() - 0.5 >= 0 ? 1 : -1), 0);
            timeInState = 0;
        }

        body.setLinearVelocity(movement);
    }

    @Override
    public void receiveHit() {
        if (!isShell) {
            isShell = true;
            movement = new Vector2(0, 0);
            timeInState = 0;
        }
    }

    @Override
    public void dispose() {

    }
}