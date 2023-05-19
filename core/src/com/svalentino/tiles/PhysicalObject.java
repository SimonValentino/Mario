package com.svalentino.tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.svalentino.MarioGame;

public class PhysicalObject {
    protected final World world;
    protected Body body;
    protected Fixture fixture;
    protected final Rectangle hitbox;
    protected final TiledMap map;

    public PhysicalObject(World world, TiledMap map, Rectangle hitbox) {
        this.world = world;
        this.map = map;
        this.hitbox = hitbox;

        constructMapObj();
    }
    public void constructMapObj() {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // StaticBody means not effected by gravity, cant move, etc.
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((hitbox.getX() + hitbox.getWidth() / 2) * MarioGame.SCALE, (hitbox.getY() + hitbox.getHeight() / 2)  * MarioGame.SCALE);

        shape.setAsBox(hitbox.getWidth() / 2 * MarioGame.SCALE, hitbox.getHeight() / 2 * MarioGame.SCALE);
        fixtureDef.shape = shape;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
    }
}
