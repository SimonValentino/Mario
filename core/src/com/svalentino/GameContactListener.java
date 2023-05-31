package com.svalentino;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.svalentino.characters.Enemy;
import com.svalentino.characters.Koopa;
import com.svalentino.tiles.Coin;
import com.svalentino.tiles.InteractableObject;
import com.svalentino.characters.Mario;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int col = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        if (fixtureA.getUserData() == null || fixtureB.getUserData() == null)
            return;

        if (col == (MarioGame.ENEMY_COL | MarioGame.DEFAULT_COL) ||
                col == (MarioGame.ENEMY_COL)) {
            if (fixtureA.getFilterData().categoryBits == MarioGame.ENEMY_COL &&
                    fixtureB.getFilterData().categoryBits == MarioGame.ENEMY_COL) {
                Enemy enemy1 = (Enemy) fixtureA.getUserData();
                Enemy enemy2 = (Enemy) fixtureB.getUserData();
                enemy1.reverse();
                enemy2.reverse();
            } else if (fixtureA.getFilterData().categoryBits == MarioGame.ENEMY_COL) {
                Enemy enemy = (Enemy) fixtureA.getUserData();
                enemy.reverse();
            } else {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                enemy.receiveHit();
            }
        }

        if (col == (MarioGame.MARIO_HEAD_COL | MarioGame.BRICK_COL) ||
                col == (MarioGame.MARIO_HEAD_COL | MarioGame.COIN_BLOCK_COL) ||
                col == (MarioGame.MARIO_HEAD_COL | MarioGame.COIN_COl)) {
            if (fixtureA.getFilterData().categoryBits == MarioGame.MARIO_HEAD_COL) {
                InteractableObject obj = (InteractableObject) fixtureB.getUserData();
                obj.hitMarioHead();
            } else {
                InteractableObject obj = (InteractableObject) fixtureA.getUserData();
                obj.hitMarioHead();
            }
        }

        if (Coin.class.isAssignableFrom(fixtureA.getUserData().getClass()) || Coin.class.isAssignableFrom((fixtureB.getUserData().getClass()))) {
            if (fixtureA.getUserData().equals("feet") || fixtureB.getUserData().equals("feet")) {
                Fixture marioFeet = fixtureA.getUserData().equals("feet") ? fixtureA : fixtureB;
                Fixture colFixture = fixtureA == marioFeet ? fixtureB : fixtureA;

                Coin c = (Coin) colFixture.getUserData();
                c.hitMarioFeet();
            }
        }

        if (col == (MarioGame.MARIO_COL | MarioGame.ENEMY_HEAD_COL)) {
            if (fixtureA.getFilterData().categoryBits == MarioGame.MARIO_COL) {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                enemy.receiveHit();
            } else {
                Enemy enemy = (Enemy) fixtureA.getUserData();
                enemy.receiveHit();
            }
        }
        if (col == (MarioGame.MARIO_COL | MarioGame.ENEMY_COL)) {
            if (fixtureB.getUserData() instanceof Koopa && ((Koopa) fixtureB.getUserData()).isShell()) {
                Koopa koopa = (Koopa) fixtureB.getUserData();
                koopa.receiveHit();
            } else if (fixtureA.getUserData() instanceof Koopa && ((Koopa) fixtureA.getUserData()).isShell()) {
                Koopa koopa = (Koopa) fixtureA.getUserData();
                koopa.receiveHit();
            } else {
                if (fixtureA.getFilterData().categoryBits == MarioGame.MARIO_COL) {
                    Mario mario = (Mario) fixtureA.getUserData();
                    mario.setDead(true);
                } else {
                    Mario mario = (Mario) fixtureB.getUserData();
                    mario.setDead(true);
                }
            }
        }

        // mario hits enemy
    }

    /*
    when two fixtures stop colliding
     */
    @Override
    public void endContact(Contact contact) {

    }

    /*
    changing characteristics of a collision before it happens
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /*
    updating the fixtures after they collide
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

