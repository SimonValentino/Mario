package com.svalentino.tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.svalentino.MarioGame;

public abstract class Tile {
    protected final World world;
    protected final TiledMap map;
    protected TiledMapTile tile;
    protected final Rectangle hitbox;
    protected Body body;

    public Tile(World world, TiledMap map, Rectangle hitbox) {
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
        bodyDef.position.set((hitbox.x + hitbox.width / 2) * MarioGame.SCALE, (hitbox.y + hitbox.height / 2)  * MarioGame.SCALE);

        shape.setAsBox(hitbox.width / 2 * MarioGame.SCALE, hitbox.height / 2 * MarioGame.SCALE);
        fixtureDef.shape = shape;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
    }
}
