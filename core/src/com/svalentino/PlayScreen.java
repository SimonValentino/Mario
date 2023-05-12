package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svalentino.Characters.Mario;

/*
Actual rendering of the MarioGame is delegated to a screen.
Any screen class should implement Screen.
 */
public class PlayScreen implements Screen {
    // Gravity force
    private final Vector2 gravity;

    // Game
    private final MarioGame game;

    // Camera
    private final OrthographicCamera camera;
    private final Viewport vport;

    // Hud
    private final GameHud hud;

    // Map
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    // Physics engine
    private World world;
    private Box2DDebugRenderer box2DRenderer;

    // Player
    private final Mario mario;

    public PlayScreen(MarioGame game) {
        this.gravity = new Vector2(0, -120f);

        this.game = game;
        this.camera = new OrthographicCamera();
        this.vport = new FitViewport(MarioGame.WIDTH, MarioGame.HEIGHT, camera);
        this.hud = new GameHud(game.batch);

        this.map = new TmxMapLoader().load("MarioMap.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(vport.getWorldWidth() / 2, vport.getWorldHeight() / 2, 0);
        camera.update();

        setupPhysicsEngine();

        this.mario = new Mario(world);
    }

    @Override
    public void show() {

    }

    /*
    Renders the image.
    Number of renders per second is fps.
     */
    @Override
    public void render(float delta) {
        clearScreen();

        renderMap(delta);
        renderer.render();

        // render the Box2D blocks
        box2DRenderer.render(world, camera.combined);

        // setup where the batch will project to
        // getting the hud's camera
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.update(delta);

        if(hud.getWorldTimer() <= 0)
            clearScreen();
    }

    /*
    gamePort field is used to resize the screen.
     */
    @Override
    public void resize(int width, int height) {
        vport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void getInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            mario.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mario.moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            mario.moveLeft();
        
        camera.position.x = mario.getXCoordinate();
    }

    private void renderMap(float delta) {
        getInput(delta);

        world.step(1 / 40f, 6, 6);

        camera.update();
        renderer.setView(camera);

    }

    private void setupPhysicsEngine() {
        this.world = new World(gravity, true);
        this.box2DRenderer = new Box2DDebugRenderer();

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
                bodyDef.position.set(rect.x + rect.width / 2, rect.y + rect.height / 2);

                shape.setAsBox(rect.width / 2, rect.height / 2);
                fixtureDef.shape = shape;

                body = world.createBody(bodyDef);
                body.createFixture(fixtureDef);
            }
        }
    }
}
