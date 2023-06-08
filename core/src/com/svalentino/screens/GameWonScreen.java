package com.svalentino.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svalentino.GameHud;
import com.svalentino.MarioGame;
import com.svalentino.SoundManager;
import com.svalentino.characters.Mario;

public class GameWonScreen implements Screen {
    private final Viewport vport;
    public final Stage stage;
    private final MarioGame game;

    public final SpriteBatch batch;

    private final OrthographicCamera camera;

    public GameWonScreen (MarioGame game) {
        SoundManager.GAME_WON_SONG.play();

        this.camera = new OrthographicCamera();
        this.game = game;

        vport = new FitViewport(Mario.marioWidth, Mario.marioHeight, camera);
        batch = new SpriteBatch();
        stage = new Stage(vport, batch);

        camera.position.set(4, 4, 0);
        camera.zoom += 42f;
        camera.update();


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label label = new Label("YOU WIN", font);
        Label timeLabel = new Label("Completed in " + MarioGame.timePlayed + " seconds", font);
        Label creditsLabel = new Label("By Simon Valentino and Marrio Jeyakumar", font);
        Label thanksLabel = new Label("Thanks for playing!", font);

        table.add(label).expandX();
        table.row();
        table.add(timeLabel).expandX().padTop(20);
        table.row();
        table.add(creditsLabel).expandX().padTop(20);
        table.row();
        table.add(thanksLabel).expandX().padTop(20);

        stage.addActor(table);
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
}

