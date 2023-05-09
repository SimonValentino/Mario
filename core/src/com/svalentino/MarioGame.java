package com.svalentino;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;
import com.svalentino.Screens.PlayScreen;

public class MarioGame extends Game {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 230;

	// Holds all sprites and images
	// Public so all screens can have access to it
	public SpriteBatch batch;

	/*
	init sprite bach and set the screen to PlayScreen
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		super.setScreen(new PlayScreen(this));
	}

	/*
	render frame
	 */
	@Override
	public void render () {
		super.render();
	}
}
