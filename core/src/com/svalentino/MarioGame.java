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

	/*
	By default, Box2D uses MKS (meters, kg, and sec) as units.
	Out Mario is an 8 x 8 rectangle, so 64m squared in volume, so
	he is affected by gravity and linear impulse very weird in a world
	that was coded based off pixels.
	Everything must be scaled from pixels to meters.
	This game will have 16pixels / 1m.
	each tile is 1m x 1m.
	The rendering env and actors must be tailored to this.
	 */
	public static final float BOX_2D_SCALE = 16f;
	public static final float TILE_LENGTH = 16f;

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
		playMusic();
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

	private void playMusic() {
		music = Gdx.audio.newMusic(Gdx.files.internal("assets/Downloads/Super Mario Bros. Theme Song.mp3"));
		music.setLooping(true);
		music.setVolume(0.15f);
		music.play();
	}
}
