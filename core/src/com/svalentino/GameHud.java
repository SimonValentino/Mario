package com.svalentino;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameHud implements Disposable {
    // Empty box that holds widgets
    // Need a Table to organize them
    public Stage stage;

    // Hud has it's own viewport so that while the game moves around
    // the hud stays
    private final Viewport vport;

    private int worldTimer;
    private double timeCount;
    private final int score;
    private int numLives;

    final private int timeInLevel = 5;
    private Label numLivesLabel;
    private Label livesLabel;
    private Label countdownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label marioLabel;

    public int getWorldTimer() {
        return worldTimer;
    }

    public GameHud(SpriteBatch batch) {
        worldTimer = timeInLevel;
        timeCount = 0;
        numLives = 3;
        score = 0;

        vport = new FitViewport(MarioGame.WIDTH, MarioGame.HEIGHT, new OrthographicCamera());

        constructLabels();
        constructStage(batch);
    }

    /*
    Construct the stage and its table. Add all labels to teh table.
    Add table to stage.
     */
    private void constructStage(SpriteBatch batch) {
        stage = new Stage(vport, batch);
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        // expandX() allows all labels to share space on the x-axis
        // top row
        table.add(marioLabel).expandX().padTop(5f);
        table.add(timeLabel).expandX().padTop(5f);
        table.add(livesLabel).expandX().padTop(5f);
        table.row();
        // new row
        table.add(scoreLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(numLivesLabel);

        stage.addActor(table);
    }

    /*
    Init all the labels as white.
     */
    public void update(float dt) {
        timeCount += dt;
        if(timeCount >=1 ) {
            worldTimer--;
            String strWorldTimer = Integer.toString(worldTimer);
            countdownLabel.setText(String.format("%0" + strWorldTimer.length() + "d", worldTimer));
            timeCount = 0;
        }
        if(worldTimer <= 0) {
            numLives--;
            numLivesLabel.setText(String.format("%01d", numLives));
            worldTimer = timeInLevel;
        }
    }

    private void constructLabels() {
        countdownLabel = new Label(String.format("%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numLivesLabel = new Label(String.format("%01d", numLives),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label("LIVES",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    }
//    public void setCountdownLabel(float delta) {
//        String strCountDownLabel = countdownLabel.getText().toString();
//        float f = (float) (Integer.parseInt(strCountDownLabel)) - delta;
//        countdownLabel.setText(String.valueOf(f));
//    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
