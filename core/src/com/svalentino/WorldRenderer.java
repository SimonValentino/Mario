package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.svalentino.Characters.Mario;

public class WorldRenderer implements Disposable {
    private final Vector2 gravity = new Vector2(0, -62.5f);
    private final float scale = 1 / MarioGame.BOX_2D_SCALE;

    private World world = new World(gravity, true);
    private TiledMap map;
    private Mario mario = new Mario(world);

    private OrthogonalTiledMapRenderer renderer;

    public WorldRenderer(TiledMap map) {
        this.map = map;
        this.renderer = new OrthogonalTiledMapRenderer(map, scale);

        constructWorld();
    }

    public WorldRenderer(String mapName) {
        this(new TmxMapLoader().load(mapName));
    }

    public void render() {
        renderer.render();
    }

    public void setView(OrthographicCamera camera) {
        renderer.setView(camera);
    }

    public void updateWorld(float delta) {
        getInput(delta);
        world.step(1 / 60f, 6, 6);
    }

    private void getInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            mario.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mario.moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            mario.moveLeft();
    }

    private void constructWorld() {
        Body body;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Rectangle rect;

        for (int i = 2; i <= 6; i++) {
            for (RectangleMapObject obj : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
                rect = obj.getRectangle();

                // StaticBody means not effected by gravity, cant move, etc.
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set((rect.x + rect.width / 2) * scale, (rect.y + rect.height / 2)  * scale);

                shape.setAsBox(rect.width / 2 * scale, rect.height / 2 * scale);
                fixtureDef.shape = shape;

                body = world.createBody(bodyDef);
                body.createFixture(fixtureDef);
            }
        }
    }

    public World getWorld() {
        return world;
    }

    public float getMarioX() {
        return mario.getXCoordinate();
    }

    @Override
    public void dispose() {
        world.dispose();
        map.dispose();
        renderer.dispose();
        mario.dispose();
    }
}
