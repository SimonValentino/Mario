package com.svalentino.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svalentino.MarioGame;

/*
Actual rendering of the MarioGame is delegated to a screen.
Any screen class should implement Screen.
 */
public class PlayScreen implements Screen {
    private MarioGame game;
    Texture tex;
    private OrthographicCamera gameCamera;
    private Viewport vport;

    public PlayScreen(MarioGame game) {
        this.game = game;
        this.tex = new Texture("badlogic.jpg");
        this.gameCamera = new OrthographicCamera();
        this.vport = new FitViewport(MarioGame.WIDTH, MarioGame.HEIGHT, gameCamera);
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
        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        game.batch.draw(tex, 0, 0);
        game.batch.end();
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
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
