package com.svalentino;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.svalentino.characters.Goomba;
import com.svalentino.characters.Koopa;
import com.svalentino.characters.Mario;
import com.svalentino.screens.GameWonScreen;
import com.svalentino.screens.PlayScreen;
import com.svalentino.tiles.Brick;
import com.svalentino.tiles.DefaultTile;
import com.svalentino.tiles.Coin;
import com.svalentino.tiles.CoinBlock;
import com.svalentino.tiles.EnemyBorder;
import com.svalentino.tiles.ExitDoor;
import com.svalentino.tiles.Flagpole;
import com.svalentino.tiles.Ground;

public class WorldRenderer implements Disposable {
    private final Vector2 gravity = new Vector2(0, -62.5f);
    private final World world = new World(gravity, true);
    private final TiledMap map;
    private final Mario mario;
    public float timeElapsed;

    private float timeElapsedSinceFlagpole = 0.0f;
    private float timeElapsedSinceWalkedOffStage = 0.0f;
    private final OrthogonalTiledMapRenderer renderer;
    private List<Goomba> goombas;
    private List<Koopa> koopas;
    private final MarioGame game;
    private final OrthographicCamera camera;

    private boolean isGameOver = false;
    private static PlayScreen screen;

    public WorldRenderer(TiledMap map, PlayScreen screen) {
        this.screen = screen;
        this.game = screen.getGame();
        this.camera = screen.getCamera();
        mario = new Mario(world);
        this.map = map;
        this.renderer = new OrthogonalTiledMapRenderer(map, MarioGame.SCALE);
        constructWorld();
        world.setContactListener(new GameContactListener());
    }

    public WorldRenderer(String mapName, PlayScreen screen) {

        this(new TmxMapLoader().load(mapName), screen);
    }

    public void render() {
        renderer.render();
        MarioGame.batch.setProjectionMatrix(camera.combined);
        MarioGame.batch.begin();
        mario.draw(MarioGame.batch);
        for(Goomba goomba : goombas) {
            goomba.draw(MarioGame.batch);
        }
        for(Koopa koopa : koopas) {
            koopa.draw(MarioGame.batch);
        }
        MarioGame.batch.end();
    }

    public void setView(OrthographicCamera camera) {
        renderer.setView(camera);
    }

    public void updateWorld(float delta, GameHud hud) {
        if (mario.isFlagpoleHit()) {
            hud.stopTimer();
            flagpoleHit(delta);
        } else {
            if (!mario.isDead()) {
                getInput(delta);

                for (Goomba goomba : goombas) {
                    if (mario.getXCoordinate() > goomba.getXCoordinate() - (13 * MarioGame.TILE_LENGTH * MarioGame.SCALE))
                        goomba.getBody().setActive(true);
                    goomba.update(delta);
                }

                for (Koopa koopa : koopas) {
                    if (mario.getXCoordinate() > koopa.getXCoordinate() - (13 * MarioGame.TILE_LENGTH * MarioGame.SCALE))
                        koopa.getBody().setActive(true);

                    koopa.update(delta);
                }

                world.step(1 / 60f, 10, 10);
                mario.update(delta);
            }

            else {
                freeze();
                timeElapsed += delta;
                SoundManager.THEME_SONG.stop();
                SoundManager.SPED_UP_THEME_SONG.stop();
                SoundManager.SPEED_UP_MUSIC.stop();
                SoundManager.DEATH_SOUND.setVolume(1f);
                SoundManager.DEATH_SOUND.play();
                if (timeElapsed >= 2.5f) {
                    int newLives = hud.getNumLives() - 1;
                    hud.setNumLives(newLives);
                    hud.updateLives();
                    hud.resetWorldTimer();
                    mario.setDead(false);
                    if (hud.getNumLives() > 0) {
                        game.setScreen(new PlayScreen(game, screen.getHud(), screen.getLevelNumber()));
                    } else {
                        SoundManager.GAME_OVER_SOUND.play();
                    }
                }
            }
        }
    }
    private void flagpoleHit(float dt) {
        if (timeElapsedSinceFlagpole <= 3.5f) {
            timeElapsedSinceFlagpole += dt;
            mario.update(dt);
        } else if (timeElapsedSinceWalkedOffStage <= 7f) {
            timeElapsedSinceWalkedOffStage += dt;
            mario.walkOffStage(dt);
        } else {
            end();
        }
        world.step(1 / 60f, 6, 6);
    }
        private void end() {
            SoundManager.ITSA_ME_SOUND.play();

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (screen.getLevelNumber() == 5) {
                game.setScreen(new GameWonScreen(game));
            } else {
                game.setScreen(new PlayScreen(game, screen.getHud(), screen.getLevelNumber() + 1));
            }
        }
    private void getInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W))
            mario.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D))
            mario.moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A))
            mario.moveLeft();
    }

    private void freeze() {
        for (Goomba goomba : goombas)
            goomba.getBody().setActive(false);
        for (Koopa koopa : koopas)
            koopa.getBody().setActive(false);
        mario.getBody().setActive(false);
    }

    private void unfreeze() {
        for (Goomba goomba : goombas)
            goomba.getBody().setActive(true);
        for (Koopa koopa : koopas)
            koopa.getBody().setActive(true);
        mario.getBody().setActive(true);
    }

    private void resetEnemies() {
        for (Goomba goomba : goombas)
            world.destroyBody(goomba.getBody());
        for (Koopa koopa : koopas)
            world.destroyBody(koopa.getBody());
        constructGoombas();
        constructKoopas();
    }

    private void constructWorld() {
        constructGoombas();
        constructGround();
        constructBricks();
        constructCoinBlocks();
        constructCoins();
        constructCementAndPipes();
        constructKoopas();
        constructFlagpole();
        constructExitDoor();
        constructEnemyBorders();
    }

    private void constructExitDoor() {
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            new ExitDoor(this, rect);
        }
    }

    private void constructGoombas() {
        goombas = new ArrayList<>();
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            goombas.add(new Goomba(this, rect.getX() * MarioGame.SCALE,
                    rect.getY() * MarioGame.SCALE));
        }
    }

    private void constructKoopas() {
        koopas = new ArrayList<>();
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            koopas.add(new Koopa(this, rect.getX() * MarioGame.SCALE,
                    rect.getY() * MarioGame.SCALE));
        }
    }

    private void constructEnemyBorders() {
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            new EnemyBorder(this, rect);
        }
    }

    private void constructGround() {
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            new Ground(this, rect);
        }
    }

    public void constructCoins() {
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            new Coin(this, rect);
        }
    }

    public void constructCoinBlocks() {
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            new CoinBlock(this, rect);
        }
    }

    public void constructCementAndPipes() {
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            new DefaultTile(this, rect);
        }
    }

    private void constructBricks() {
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            new Brick(this, rect);
        }
    }
    private void constructFlagpole() {
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            new Flagpole(this, rect);
        }
    }

    public World getWorld() {
        return world;
    }

    public float getMarioX() {
        return mario.getXCoordinate();
    }
    public float getMarioY() {
        return mario.getYCoordinate();
    }

    public TiledMap getMap() {
        return map;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public static float getCameraY() {
        return screen.getCameraY();
    }

    @Override
    public void dispose() {
        world.dispose();
        map.dispose();
        renderer.dispose();
        mario.dispose();
    }
}