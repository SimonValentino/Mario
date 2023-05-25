package com.svalentino.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class Goomba extends Enemy {

    private float goombaWidth = MarioGame.TILE_LENGTH / 2 - 0.2f;
    private float goombaHeight = MarioGame.TILE_LENGTH / 2 - 0.2f;
    private float goombaMaxSpeed = 1f;
    private boolean isDead = false;

    private Vector2 movementVector = new Vector2(1f, 0f);

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
        fixtureDef.filter.maskBits = MarioGame.DEFAULT_COL | MarioGame.BRICK_COL |
                MarioGame.COIN_BLOCK_COL | MarioGame.ENEMY_COL | MarioGame.PIPE_COL
                | MarioGame.MARIO_COL;

        fixtureDef.shape = hitbox;
        body.createFixture(fixtureDef);

//        EdgeShape head = new EdgeShape();
//        head.set(new Vector2((-goombaWidth / 2f) * MarioGame.SCALE, (goombaHeight + 0.1f) * MarioGame.SCALE),
//        new Vector2((goombaWidth / 2f) * MarioGame.SCALE, (goombaHeight + 0.1f) * MarioGame.SCALE));

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
        if (isDead)
            world.destroyBody(body);
        if (isBelowMaxSpeedRight())
            body.applyLinearImpulse(movementVector, body.getWorldCenter(), true);
        if (body.getLinearVelocity().x <= 0)
            movementVector = movementVector.scl(-1, 1);
    }

    @Override
    public void receiveHit() {
        isDead = true;
    }

    private boolean isBelowMaxSpeedRight() {
        return body.getLinearVelocity().x < goombaMaxSpeed;
    }

    private boolean isBelowMaxSpeedLeft() {
        return body.getLinearVelocity().y > -goombaMaxSpeed;
    }
}
