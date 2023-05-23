package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
public class SoundManager {
    public static final Music themeSong = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Theme Music.mp3"));
    public static final Music spedUpThemeSong = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Sped up Theme Music.mp3"));
    public static final Sound coinBlockSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/coin.wav"));
    public static final Sound blankBlockSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/bump.wav"));
    public static final Sound oneUPSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario 1-UP Sound Effect.mp3"));
    public static final Sound jumpSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario Jump Sound Effect.mp3"));
    public static final Sound brickBreakingSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/breakblock.wav"));
    public static final Music deathSound = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Death Sound Effect.mp3"));
    public static final Sound pipeSound = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario Pipe Sound Effect.mp3"));


}
