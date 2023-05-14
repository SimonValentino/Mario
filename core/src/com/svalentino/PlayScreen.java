package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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
    private final float scale = 1 / MarioGame.BOX_2D_SCALE;

    private final MarioGame game;

    private final OrthographicCamera camera;
    private final Viewport vport;

    private final GameHud hud;

    private WorldRenderer worldRenderer;
    private Box2DDebugRenderer box2DRenderer;


    public PlayScreen(MarioGame game) {


        this.game = game;
        this.camera = new OrthographicCamera();
        this.vport = new FitViewport(MarioGame.WIDTH * scale, MarioGame.HEIGHT * scale, camera);
        this.hud = new GameHud(game.batch);

        this.box2DRenderer = new Box2DDebugRenderer();

        this.worldRenderer = new WorldRenderer("MarioMap.tmx");

        camera.position.set(vport.getWorldWidth() / 2, vport.getWorldHeight() / 2, 0);
        camera.update();
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

        update(delta);
        worldRenderer.render();

        // render the Box2D blocks
        box2DRenderer.render(worldRenderer.getWorld(), camera.combined);

        // setup where the batch will project to
        // getting the hud's camera
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.update(delta);

        if(hud.getWorldTimer() <= 0) {
        }
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
        game.dispose();
        box2DRenderer.dispose();
        worldRenderer.dispose();
        hud.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void update(float delta) {
        worldRenderer.updateWorld(delta);
        camera.position.x = worldRenderer.getMarioX();
        camera.update();

        worldRenderer.setView(camera);

    }
}
