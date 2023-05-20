package com.svalentino.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

public class Goomba extends Enemy {

    private float goombaWidth = MarioGame.TILE_LENGTH / 2 - 0.2f;
    private float goombaHeight = MarioGame.TILE_LENGTH / 2 - 0.2f;

    public Goomba(World world, float x, float y) {
        super(world, x, y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((goombaWidth + MarioGame.TILE_LENGTH * 5) * MarioGame.SCALE,
                (goombaHeight + MarioGame.TILE_LENGTH) * MarioGame.SCALE);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(goombaWidth * MarioGame.SCALE, goombaHeight * MarioGame.SCALE);

        fixtureDef.filter.categoryBits = MarioGame.MARIO_BYTE;
        fixtureDef.filter.maskBits = MarioGame.DEFAULT_BYTE | MarioGame.BRICK_BYTE | MarioGame.COIN_BLOCK_BYTE | MarioGame.ENEMY_BYTE;

        fixtureDef.shape = hitbox;
        body.createFixture(fixtureDef);
    }

    @Override
    public void dispose() {

    }
}
