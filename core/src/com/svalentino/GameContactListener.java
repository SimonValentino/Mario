package com.svalentino;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.svalentino.characters.Enemy;
import com.svalentino.tiles.Coin;
import com.svalentino.tiles.InteractableObject;

public class GameContactListener implements ContactListener {
    /*
    called when two fixtures begin to collide
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int col = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        if (fixtureA.getUserData() == null || fixtureB.getUserData() == null)
            return;

        if (InteractableObject.class.isAssignableFrom(fixtureA.getUserData().getClass()) ||
                InteractableObject.class.isAssignableFrom((fixtureB.getUserData().getClass()))) {
            if (fixtureA.getUserData().equals("head") || fixtureB.getUserData().equals("head")) {
                Fixture marioFeet = fixtureA.getUserData().equals("head") ? fixtureA : fixtureB;
                Fixture colFixture = fixtureA == marioFeet ? fixtureB : fixtureA;

                InteractableObject obj = (InteractableObject) colFixture.getUserData();
                obj.hitMarioHead();
            }
        }

        else if (col == (MarioGame.MARIO_COL | MarioGame.ENEMY_HEAD_COL)) {
            if (fixtureA.getFilterData().categoryBits == MarioGame.MARIO_COL) {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                enemy.receiveHit();
            } else {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                enemy.receiveHit();
            }
        }

        else if (Coin.class.isAssignableFrom(fixtureA.getUserData().getClass()) || Coin.class.isAssignableFrom((fixtureB.getUserData().getClass()))) {
            if (fixtureA.getUserData().equals("feet") || fixtureB.getUserData().equals("feet")) {
                Fixture marioFeet = fixtureA.getUserData().equals("feet") ? fixtureA : fixtureB;
                Fixture colFixture = fixtureA == marioFeet ? fixtureB : fixtureA;

                Coin c = (Coin) colFixture.getUserData();
                c.hitMarioFeet();
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
