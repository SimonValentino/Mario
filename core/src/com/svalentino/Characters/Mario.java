package com.svalentino.Characters;

import org.w3c.dom.css.Rect;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Mario extends Sprite {
    private final World world;
    private final Body mario;

    private final float MARIO_WIDTH = 8f;
    private final float MARIO_HEIGHT = 8f;

    private final int MARIO_MAX_SPEED = 2;

    public Mario(World world) {
        this.world = world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(50f, 50f);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mario = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(MARIO_WIDTH, MARIO_HEIGHT);

        fixtureDef.shape = hitbox;
        mario.createFixture(fixtureDef);
    }

    public void jump() {
        mario.applyLinearImpulse(new Vector2(0, 4f), mario.getWorldCenter(), true);
    }

    public void moveRight() {
        mario.applyLinearImpulse(new Vector2(0.1f, 0), mario.getWorldCenter(), true);
    }

    public void moveLeft() {
        mario.applyLinearImpulse(new Vector2(-0.1f, 0), mario.getWorldCenter(), true);
    }

    public float getXCoordinate() {
        return mario.getWorldCenter().x;
    }
}
