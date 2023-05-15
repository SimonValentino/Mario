package com.svalentino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import static com.svalentino.MarioGame.music;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;

/*
Actual rendering of the MarioGame is delegated to a screen.
Any screen class should implement Screen.
 */
public class PlayScreen implements Screen {
    private final MarioGame game;

    private float elapsed;

    private final Sound deathSound;
    private final OrthographicCamera camera;
    private final Viewport vport;

    private final GameHud hud;

    private final GameOverScreen gameOverScreen;
    private final WorldRenderer worldRenderer;
    private final Box2DDebugRenderer box2DRenderer;


    public PlayScreen(MarioGame game) {

        this.deathSound = Gdx.audio.newSound(Gdx.files.internal("assets/Downloads/Sounds & Music/Y2Mate.is - Mario Death - Sound Effect (HD)-m9zhgDsd4P4-160k-1659760324829.mp3"));
        this.game = game;
        this.gameOverScreen = new GameOverScreen(game);
        this.camera = new OrthographicCamera();
        this.vport = new FitViewport(MarioGame.WIDTH * MarioGame.SCALE, MarioGame.HEIGHT * MarioGame.SCALE, camera);
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

        if(GameHud.numLives <= 0) {
            music.stop();
            deathSound.play(0.15f);
            elapsed += Gdx.graphics.getDeltaTime();
            game.batch.setProjectionMatrix(gameOverScreen.stage.getCamera().combined);
            game.setScreen(new GameOverScreen(game));
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
