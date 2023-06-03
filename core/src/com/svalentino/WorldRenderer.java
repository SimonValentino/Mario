package com.svalentino;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.svalentino.tiles.Brick;
import com.svalentino.tiles.DefaultTile;
import com.svalentino.tiles.Coin;
import com.svalentino.tiles.CoinBlock;
import com.svalentino.tiles.Ground;

public class WorldRenderer implements Disposable {
    private Vector2 gravity = new Vector2(0, -62.5f);
    private World world = new World(gravity, true);
    private TiledMap map;
    private Mario mario;
    public float timeElapsed;

    public TextureAtlas atlas;
    private OrthogonalTiledMapRenderer renderer;
    private List<Goomba> goombas;
    private List<Koopa> koopas;

    private float timeElapsedSinceFlagpole = 0.0f;
    private float timeElapsedSinceWalkedOffStage = 0.0f;


    public WorldRenderer(TiledMap map) {
        atlas = new TextureAtlas(Gdx.files.internal("Downloads/Mario_and_Enemies.pack"));
        mario = new Mario(this);
        this.map = map;
        this.renderer = new OrthogonalTiledMapRenderer(map, MarioGame.SCALE);
        constructWorld();
        world.setContactListener(new GameContactListener());
    }

    public WorldRenderer(String mapName) {
        this(new TmxMapLoader().load(mapName));
    }

    public void render() {
        renderer.render();
//        MarioGame.batch.draw(texture, 100, 10);
//        MarioGame.batch.end();
    }

    public void setView(OrthographicCamera camera) {
        renderer.setView(camera);
    }

    public void updateWorld(float delta, GameHud hud, SpriteManager spriteManager) {
        if (mario.isFlagpoleHit()) {
            hud.stopTimer();
            flagpoleHit(delta);
        } else {
            getInput(delta);

            for (Goomba goomba : goombas)
                goomba.update(delta);
            for (Koopa koopa : koopas)
                koopa.update(delta);
            mario.update(delta);
            MarioGame.batch.begin();
            MarioGame.batch.draw(spriteManager.getSmall_mario_stand(), 50, 10 + delta);
            MarioGame.batch.end();


            world.step(1 / 60f, 6, 6);


            if (mario.isDead()) {
                freeze();
                timeElapsed += delta;
                SoundManager.THEME_SONG.stop();
                SoundManager.SPED_UP_THEME_SONG.stop();
                SoundManager.DEATH_SOUND.setVolume(1f);
                SoundManager.DEATH_SOUND.play();
                if (timeElapsed >= 2.5f) {
                    int newLives = hud.getNumLives() - 1;
                    hud.setNumLives(newLives);
                    hud.updateLives();
                    hud.resetWorldTimer();
                    mario.die();
                    mario.setDead(false);
                    if (hud.getNumLives() > 0) {
                        SoundManager.THEME_SONG.play();
                        timeElapsed = 0;
                        unfreeze();
                        resetEnemies();
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
        } else if (timeElapsedSinceWalkedOffStage <= 6f) {
            timeElapsedSinceWalkedOffStage += dt;
            mario.walkOffStage();
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

        System.exit(0);
    }

    private void getInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            mario.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mario.moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
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
        constructGround();
        constructBricks();
        constructCoinBlocks();
        constructCoins();
        constructGoombas();
        constructCementAndPipes();
        constructKoopas();
        constructFlagpole();
    }

    private void constructFlagpole() {
        Rectangle rect;

        for (RectangleMapObject obj : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            rect = obj.getRectangle();
            new Flagpole(this, rect);
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

    public World getWorld() {
        return world;
    }

    public float getMarioX() {
        return mario.getXCoordinate();
    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void dispose() {
        world.dispose();
        map.dispose();
        renderer.dispose();
        mario.dispose();
    }
}