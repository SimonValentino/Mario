package com.svalentino.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.svalentino.MarioGame;

public class GameHud {
    // Empty box that holds widgets
    // Need a Table to organize them
    public Stage stage;

    // Hud has it's own viewport so that while the game moves around
    // the hud stays
    private Viewport vport;

    private int timer;
    private double timeCount;
    private int score;

    private Label countdownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label marioLabel;

    public GameHud(SpriteBatch batch) {
        timer = 300;
        timeCount = 0;
        score = 0;

        vport = new FitViewport(MarioGame.WIDTH, MarioGame.HEIGHT, new OrthographicCamera());

        constructLabels();
        constructStage(batch);

    }

    private void constructStage(SpriteBatch batch) {
        stage = new Stage(vport, batch);
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        // expandX() allows all labels to share space on the x-axis
        // top row
        table.add(marioLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        // new row
        table.add(scoreLabel).expandX().padTop(10);
        table.add(countdownLabel).expandX().padTop(10);

        // goofy method name addActor()
        stage.addActor(table);
    }

    private void constructLabels() {
        countdownLabel = new Label(String.format("%03d", timer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreLabel = new Label(String.format("%06d", timer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        timeLabel = new Label("TIME",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        marioLabel = new Label("MARIO",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }
}
