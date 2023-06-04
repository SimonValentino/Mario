package com.svalentino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.svalentino.characters.Enemy;
import com.svalentino.characters.Koopa;
import com.svalentino.characters.KoopaState;
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

        if (col == (MarioGame.ENEMY_COL)) {
            if (fixtureA.getUserData() instanceof Koopa && ((Koopa) fixtureA.getUserData()).getCurrentState() == KoopaState.MOVING_SHELL) {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                enemy.obliterate();
            } else if (fixtureB.getUserData() instanceof Koopa && ((Koopa) fixtureB.getUserData()).getCurrentState() == KoopaState.MOVING_SHELL) {
                Enemy enemy = (Enemy) fixtureA.getUserData();
                enemy.obliterate();
            } else {
                Enemy enemy1 = (Enemy) fixtureA.getUserData();
                Enemy enemy2 = (Enemy) fixtureB.getUserData();
                enemy1.reverse();
                enemy2.reverse();
            }
        }

        else if (col == (MarioGame.MARIO_COL | MarioGame.ENEMY_COL)) {
            if (fixtureB.getUserData() instanceof Koopa && ((Koopa) fixtureB.getUserData()).getCurrentState() == KoopaState.SHELL) {
                Mario mario = (Mario) fixtureA.getUserData();
                Koopa koopa = (Koopa) fixtureB.getUserData();
                koopa.kickShell(mario.getXCoordinate() >= koopa.getXCoordinate());
            } else if (fixtureA.getUserData() instanceof Koopa && ((Koopa) fixtureA.getUserData()).getCurrentState() == KoopaState.SHELL) {
                Koopa koopa = (Koopa) fixtureA.getUserData();
                Mario mario = (Mario) fixtureB.getUserData();
                koopa.kickShell(mario.getXCoordinate() >= koopa.getXCoordinate());
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

        if (col == (MarioGame.ENEMY_COL | MarioGame.DEFAULT_COL)) {
            if (fixtureA.getFilterData().categoryBits == MarioGame.ENEMY_COL) {
                Enemy enemy = (Enemy) fixtureA.getUserData();
                enemy.reverse();
            } else {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                enemy.reverse();
            }
        }

        else if (col == (MarioGame.MARIO_HEAD_COL | MarioGame.BRICK_COL) ||
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

        else if (col == (MarioGame.MARIO_COL | MarioGame.COIN_COl)) {
            if (fixtureA.getFilterData().categoryBits == MarioGame.MARIO_COL) {
                Coin coin = (Coin) fixtureB.getUserData();
                coin.hitMarioHead();
            } else {
                Coin coin = (Coin) fixtureA.getUserData();
                coin.hitMarioHead();
            }
        }

        else if (col == (MarioGame.MARIO_COL | MarioGame.ENEMY_HEAD_COL)) {
            if (fixtureA.getUserData() instanceof Koopa && ((Koopa) fixtureA.getUserData()).getCurrentState() == KoopaState.SHELL) {
                Koopa koopa = (Koopa) fixtureA.getUserData();
                Mario mario = (Mario) fixtureB.getUserData();
                koopa.kickShell(mario.getXCoordinate() >= koopa.getXCoordinate());
            } else if (fixtureB.getUserData() instanceof Koopa && ((Koopa) fixtureB.getUserData()).getCurrentState() == KoopaState.SHELL) {
                Koopa koopa = (Koopa) fixtureB.getUserData();
                Mario mario = (Mario) fixtureA.getUserData();
                koopa.kickShell(mario.getXCoordinate() >= koopa.getXCoordinate());
            } else if (fixtureA.getFilterData().categoryBits == MarioGame.MARIO_COL) {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                Mario mario = (Mario) fixtureA.getUserData();
                mario.bounceUpAfterEnemyHit();
                enemy.receiveHit();
            } else {
                Enemy enemy = (Enemy) fixtureA.getUserData();
                Mario mario = (Mario) fixtureB.getUserData();
                mario.bounceUpAfterEnemyHit();
                enemy.receiveHit();
            }
        }

        else if (col == (MarioGame.MARIO_COL | MarioGame.FLAGPOLE_COL)) {
            if (fixtureA.getFilterData().categoryBits == MarioGame.MARIO_COL) {
                Mario mario = (Mario) fixtureA.getUserData();
                mario.hitFlagpole();
            } else {
                Mario mario = (Mario) fixtureB.getUserData();
                mario.hitFlagpole();
            }
        }

        else if (col == (MarioGame.MARIO_COL | MarioGame.EXIT_DOOR_COL)) {
            if (fixtureA.getFilterData().categoryBits == MarioGame.MARIO_COL) {
                Mario mario = (Mario) fixtureA.getUserData();
                mario.goThroughExitDoor();
            } else {
                Mario mario = (Mario) fixtureB.getUserData();
                mario.goThroughExitDoor();
            }
        }
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
