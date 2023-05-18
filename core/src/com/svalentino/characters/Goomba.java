package com.svalentino.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.svalentino.MarioGame;

public class Goomba extends Sprite implements Disposable {
    private World world;
    private Body goomba;

    public static final float goombaWidth = MarioGame.TILE_LENGTH / 2;
    public static final float goombaHeight = MarioGame.TILE_LENGTH / 2;

    public Goomba(World world, int x, int y) {
        this.world = world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((goombaWidth + MarioGame.TILE_LENGTH * 5) * MarioGame.SCALE,
                (goombaHeight + MarioGame.TILE_LENGTH) * MarioGame.SCALE);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        goomba = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(goombaWidth * MarioGame.SCALE, goombaHeight * MarioGame.SCALE);

        fixtureDef.shape = hitbox;
        fixtureDef.friction = 0.6f;
        goomba.createFixture(fixtureDef);
    }

    public void move() {
        if (hitWall())
            goomba.setLinearVelocity(-5f, 0);
        else
            goomba.setLinearVelocity(5f, 0);
    }

    private boolean hitWall() {
        return goomba.getLinearVelocity().x == 0;
    }

    @Override
    public void dispose() {

    }
}
