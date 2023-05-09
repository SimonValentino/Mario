package com.svalentino.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svalentino.MarioGame;
import com.svalentino.Scenes.GameHud;

/*
Actual rendering of the MarioGame is delegated to a screen.
Any screen class should implement Screen.
 */
public class PlayScreen implements Screen {
    private MarioGame game;
    private OrthographicCamera camera;
    private Viewport vport;
    private GameHud hud;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(MarioGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.vport = new FitViewport(MarioGame.WIDTH, MarioGame.HEIGHT, camera);
        this.hud = new GameHud(game.batch);

        this.map = new TmxMapLoader().load("MarioMap.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(vport.getWorldWidth() / 2, vport.getWorldHeight() / 2, 0);
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

        // setup where the batch will project to
        // getting the hud's camera
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.position.x -= 100 * delta;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.position.x += 1 * delta;
    } 

    private void renderMap(float delta) {
        getInput(delta);
        camera.update();
        renderer.setView(camera);
    }
}
