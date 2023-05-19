package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.svalentino.screens.PlayScreen;
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
	public static final float SCALE = 1 / 16f;
	public static final float TILE_LENGTH = 16f;

	/*
	These bytes are used to identify what mario can collide
	with in his fixture filter

	DESTROYED_BYTE is for when a tile gets destroyed
	 */
	public static final byte DEFAULT_BYTE = 1;
	public static final byte MARIO_BYTE = 2;
	public static final byte COIN_BYTE = 4;
	public static final byte BRICK_BYTE = 8;
	public static final byte COIN_BLOCK_BYTE = 16;
	public static final byte PIPE_BYTE = 32;
	public static final byte DESTROYED_BYTE = 64;

	// Holds all sprites and images
	// Public so all screens can have access to it
	public SpriteBatch batch;
	public static Music themeSong;

	/*
	init sprite bach and set the screen to PlayScreen
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		playThemeSong();
		super.setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		themeSong.dispose();
	}

	/*
        render frame
         */
	@Override
	public void render () {
		super.render();
	}

	public void playThemeSong() {
		themeSong = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Theme Music.mp3"));
		themeSong.setLooping(true);
		themeSong.setVolume(0.15f);
		themeSong.play();
	}
}
