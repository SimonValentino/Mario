package com.svalentino;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;
import com.svalentino.Screens.PlayScreen;

public class MarioGame extends Game {
	// Holds all sprites and images
	// Public so all screens can have access to it
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		super.setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
