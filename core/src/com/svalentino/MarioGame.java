package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;

public class MarioGame extends Game {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 240;

	// Holds all sprites and images
	// Public so all screens can have access to it
	public SpriteBatch batch;
	private Music music;
	/*
	init sprite bach and set the screen to PlayScreen
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		music = Gdx.audio.newMusic(Gdx.files.internal("assets\\Downloads\\Super Mario Bros. Theme Song.mp3"));
		music.setLooping(true);
		music.setVolume(0.15f);
		music.play();
		super.setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}

	/*
        render frame
         */
	@Override
	public void render () {
		super.render();
	}
}
