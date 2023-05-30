package com.svalentino;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.svalentino.tiles.Brick;
import com.svalentino.tiles.DefaultTile;
import com.svalentino.tiles.Coin;
import com.svalentino.tiles.CoinBlock;
import com.svalentino.tiles.Ground;

public class WorldRenderer implements Disposable {
    private Vector2 gravity = new Vector2(0, -62.5f);
    private World world = new World(gravity, true);
    private TiledMap map;
    private Mario mario = new Mario(this);
    public float timeElapsed;

    private OrthogonalTiledMapRenderer renderer;

    private List<Goomba> goombas;
    private List<Koopa> koopas;

    public WorldRenderer(TiledMap map) {
        timeElapsed = 0;
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
    }

    public void setView(OrthographicCamera camera) {
        renderer.setView(camera);
    }

    public void updateWorld(float delta, GameHud hud) {
        getInput(delta);

        for (Goomba goomba : goombas)
            goomba.update(delta);

        for (Koopa koopa : koopas)
            koopa.update(delta);

        world.step(1 / 60f, 6, 6);


        if(mario.isDead()) {
            timeElapsed += delta;
            SoundManager.THEME_SONG.stop();
            SoundManager.SPED_UP_THEME_SONG.stop();
            SoundManager.DEATH_SOUND.setVolume(1f);
            SoundManager.DEATH_SOUND.play();
            if(timeElapsed >= 2.5f) {
                int newLives = hud.getNumLives()-1;
                hud.setNumLives(newLives);
                hud.updateLives();
                hud.resetWorldTimer();
                mario.die();
                mario.isDead = false;
                SoundManager.THEME_SONG.play();
                timeElapsed = 0;
            }
        }
    }

    private void getInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            mario.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mario.moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            mario.moveLeft();
    }

    private void constructWorld() {
        constructGround();
        constructBricks();
        constructCoinBlocks();
        constructCoins();
        constructGoombas();
        constructCementAndPipes();
        constructKoopas();
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