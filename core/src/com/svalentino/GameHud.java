package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
import com.svalentino.SoundManager;

public class GameHud implements Disposable {
    // Empty box that holds widgets
    // Need a Table to organize them
    public Stage stage;

    // Hud has it's own viewport so that while the game moves around
    // the hud stays
    private final Viewport vport;

    private int worldTimer;
    private double timeCount;
    private static int score;
    public int numLives;

    private static int coins;
    final private int timeInLevel = 100;

    private Label numLivesLabel;

    private Label coinsLabel;

    private static Label numCoinsLabel;
    private Label livesLabel;
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label marioLabel;


    public GameHud(SpriteBatch batch) {
        worldTimer = timeInLevel;
        timeCount = 0;
        score = 0;
        numLives = 3;
        coins = 0;
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
        table.add(coinsLabel).expandX().padTop(5f);
        table.row();
        // new row
        table.add(scoreLabel).expandX();
        table.add(countdownLabel).expandX();
        table.add(numLivesLabel).expandX();
        table.add(numCoinsLabel).expandX();

        stage.addActor(table);
    }

    /*
    Init all the labels as white.
     */
    public void update(float dt) {
        timeCount += dt;
        if(timeCount >= 1 ) {
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
        if(coins == 100) {
            coins = 0;
            numLives++;
            SoundManager.oneUPSound.play();
            numLivesLabel.setText(String.format("%0" + Integer.toString(numLives).length() + "d", numLives));
        }
    }

    private void constructLabels() {
        countdownLabel = new Label(String.format("%0" + Integer.toString(timeInLevel).length() + "d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numCoinsLabel = new Label(String.format("%01d", coins),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numLivesLabel = new Label(String.format("%01d", numLives),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label("LIVES",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinsLabel = new Label("COINS",
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    }

    public static void updateScore(int points) {
        score += points;
        scoreLabel.setText(String.format("%06d", score));
    }
    public static void updateCoins() {
        coins += 1;
        numCoinsLabel.setText(String.format("%0" + Integer.toString(coins).length() + "d", coins));

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    public int getWorldTimer() {
        return worldTimer;
    }
    public int getNumLives() {
        return numLives;
    }
    public int getTimeInLevel() {
        return timeInLevel;
    }
}
