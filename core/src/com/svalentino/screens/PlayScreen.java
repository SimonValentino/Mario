package com.svalentino.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.WorldRenderer;
import com.svalentino.SoundManager;

import java.util.HashMap;
import java.util.Map;

/*
Actual rendering of the MarioGame is delegated to a screen.
Any screen class should implement Screen.
 */
public class PlayScreen implements Screen {
    private final MarioGame game;

    int levelNumber;
    Map<Integer, String> levels;

    private final OrthographicCamera camera;
    private final Viewport vport;

    private GameHud hud;

    private final GameOverScreen gameOverScreen;
    private WorldRenderer worldRenderer;
    private final Box2DDebugRenderer box2DRenderer;


    public static TextureAtlas atlas;


    public PlayScreen(MarioGame game, GameHud hud, int levelNumber) {
        this.levelNumber = levelNumber;
        constructLevels();
        atlas = new TextureAtlas(Gdx.files.internal("Downloads/Mario_and_Enemies.pack"));

        this.game = game;
        this.gameOverScreen = new GameOverScreen(game);
        this.camera = new OrthographicCamera();
        this.vport = new FitViewport(MarioGame.WIDTH * MarioGame.SCALE, MarioGame.HEIGHT * MarioGame.SCALE, camera);
        this.hud = hud;
        this.box2DRenderer = new Box2DDebugRenderer();

        this.worldRenderer = new WorldRenderer(levels.get(this.levelNumber), this);

        camera.position.set(vport.getWorldWidth() / 2, vport.getWorldHeight() / 2, 0);
        camera.update();

        hud.resetWorldTimer();
        hud.resumeTimer();

        configureThemeSong();
        game.playThemeSong();
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
        //box2DRenderer.render(worldRenderer.getWorld(), camera.combined);

        // setup where the batch will project to
        // getting the hud's camera
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
        worldRenderer.updateWorld(delta, hud);
        hud.update(delta);
        if (levelNumber == 4 && camera.position.y < 85f) {
            camera.position.y = camera.position.y + 0.02f;
        }
        else if (worldRenderer.getMarioX() > 12.5 * MarioGame.TILE_LENGTH * MarioGame.SCALE)
            camera.position.x = worldRenderer.getMarioX();
        else
            camera.position.x = 12.5f * MarioGame.TILE_LENGTH * MarioGame.SCALE;

        camera.update();
        worldRenderer.setView(camera);

        if(hud.getNumLives() <= 0) {
            SoundManager.SPED_UP_THEME_SONG.stop();
            SoundManager.DEATH_SOUND.play();
            MarioGame.batch.setProjectionMatrix(gameOverScreen.stage.getCamera().combined);
            game.setScreen(new GameOverScreen(game));
        }
        if(hud.getWorldTimer() <= hud.getTimeInLevel() / 5) {
            SoundManager.THEME_SONG.stop();
            SoundManager.SPED_UP_THEME_SONG.setVolume(0.15f);
            SoundManager.SPED_UP_THEME_SONG.play();
        }

    }

    private void constructLevels() {
        levels = new HashMap<>();
        levels.put(1, "Level1.tmx");
        levels.put(2, "Level2.tmx");
        levels.put(3, "Level3.tmx");
        levels.put(4, "Level4.tmx");
        levels.put(5, "CreditsLevel.tmx");
    }

    private void configureThemeSong() {
        switch (levelNumber) {
            case 1:
                SoundManager.THEME_SONG = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Theme Music.mp3"));
                SoundManager.THEME_SONG.setVolume(0.15f);
                break;
            case 2:
                SoundManager.THEME_SONG = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Underground.mp3"));
                SoundManager.THEME_SONG.setVolume(1f);
                break;
            case 3:
                SoundManager.THEME_SONG = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Sky Level Theme.mp3"));
                SoundManager.THEME_SONG.setVolume(0.3f);
            case 4:
                SoundManager.THEME_SONG = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Theme Music.mp3"));
                SoundManager.THEME_SONG.setVolume(0.15f);
                break;
            case 5:
                SoundManager.THEME_SONG = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Theme Music.mp3"));
                SoundManager.THEME_SONG.setVolume(0.15f);
                break;
        }
    }

    public MarioGame getGame() {
        return game;
    }

    public GameHud getHud() {
        return hud;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public float getCameraY() {
        return camera.position.y;
    }
}
