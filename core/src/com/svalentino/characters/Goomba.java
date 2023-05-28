package com.svalentino.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class Goomba extends Enemy {

    private float goombaWidth = MarioGame.TILE_LENGTH / 2 - 0.2f;
    private float goombaHeight = MarioGame.TILE_LENGTH / 2 - 0.2f;
    private float goombaMaxSpeed = 1f;
    private boolean isDead = false;
    private boolean hasDied = false;

    public Goomba(WorldRenderer worldRenderer, float x, float y) {
        super(worldRenderer, x, y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(goombaWidth * MarioGame.SCALE, goombaHeight * MarioGame.SCALE);

        fixtureDef.filter.categoryBits = MarioGame.ENEMY_COL;
        fixtureDef.filter.maskBits = MarioGame.CEMENT_COL | MarioGame.BRICK_COL |
                MarioGame.COIN_BLOCK_COL | MarioGame.ENEMY_COL | MarioGame.PIPE_COL
                | MarioGame.MARIO_COL | MarioGame.GROUND_COL;

        fixtureDef.shape = hitbox;
        body.createFixture(fixtureDef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vericies = new Vector2[4];
        vericies[0] = new Vector2(-7, 10).scl(MarioGame.SCALE);
        vericies[1] = new Vector2(7, 10).scl(MarioGame.SCALE);
        vericies[2] = new Vector2(-7, 3).scl(MarioGame.SCALE);
        vericies[3] = new Vector2(7, 3).scl(MarioGame.SCALE);
        head.set(vericies);

        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = MarioGame.ENEMY_HEAD_COL;
        // How much mario will bounce up
        fixtureDef.restitution = 0.75f;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void update() {
        if (hasDied && !isDead) {
            world.destroyBody(body);
            isDead = true;
        }

        body.setLinearVelocity(movement);
    }

    @Override
    public void receiveHit() {
        hasDied = true;
    }
}
