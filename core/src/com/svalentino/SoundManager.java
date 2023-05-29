package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
public class SoundManager {
    public static final Music THEME_SONG = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Theme Music.mp3"));
    public static final Music SPED_UP_THEME_SONG = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Sped up Theme Music.mp3"));
    public static final Sound COIN_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/coin.wav"));
    public static final Sound BLANK_BLOCK_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/bump.wav"));
    public static final Sound ONE_UP_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario 1-UP Sound Effect.mp3"));
    public static final Sound JUMP_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario Jump Sound Effect.mp3"));
    public static final Sound BRICK_BREAKING_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/breakblock.wav"));
    public static final Music DEATH_SOUND = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Death Sound Effect.mp3"));
    public static final Sound GOOMBA_DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Goomba Death Sound Effect.mp3"));
    // --Commented out by Inspection (5/29/2023 11:55 AM):public static final Sound PIPE_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario Pipe Sound Effect.mp3"));


}
