package com.svalentino.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.SoundManager;
import com.svalentino.WorldRenderer;

public class Goomba extends Enemy {

    private final float goombaWidth = MarioGame.TILE_LENGTH / 2 - 1f;
    private final float goombaHeight = MarioGame.TILE_LENGTH / 2 - 1f;
    private boolean isDead = false;
    private boolean hasDied = false;

    public Goomba(WorldRenderer worldRenderer, float x, float y) {
        super(worldRenderer, x, y);

        movement = new Vector2(5f * (Math.random() - 0.5 >= 0 ? 1 : -1), 0);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(goombaWidth * MarioGame.SCALE, goombaHeight * MarioGame.SCALE);

        fixtureDef.filter.categoryBits = MarioGame.ENEMY_COL;
        fixtureDef.filter.maskBits = MarioGame.DEFAULT_COL | MarioGame.BRICK_COL |
                MarioGame.COIN_BLOCK_COL | MarioGame.ENEMY_COL
                | MarioGame.MARIO_COL | MarioGame.GROUND_COL;

        fixtureDef.shape = hitbox;
        body.createFixture(fixtureDef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-6f, 11).scl(MarioGame.SCALE);
        vertice[1] = new Vector2(6f, 11).scl(MarioGame.SCALE);
        vertice[2] = new Vector2(-3f, 3).scl(MarioGame.SCALE);
        vertice[3] = new Vector2(3f, 3).scl(MarioGame.SCALE);
        head.set(vertice);

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

        body.setLinearVelocity(movement);
    }

    @Override
    public void receiveHit() {
        hasDied = true;
        SoundManager.ENEMY_HIT_SOUND.play();
        GameHud.updateScore(300);
    }
}