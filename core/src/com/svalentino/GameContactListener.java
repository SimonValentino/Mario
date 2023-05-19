package com.svalentino;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.svalentino.tiles.Tile;

public class GameContactListener implements ContactListener {
    /*
    called when two fixtures begin to collide
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == null || fixtureB.getUserData() == null)
            return;

        if (fixtureA.getUserData().equals("head") || fixtureB.getUserData().equals("head")) {
            Fixture marioHead = fixtureA.getUserData().equals("head") ? fixtureA : fixtureB;
            Fixture colFixture = fixtureA == marioHead ? fixtureB : fixtureA;

            if (Tile.class.isAssignableFrom(colFixture.getUserData().getClass())) {
                Tile t = (Tile) colFixture.getUserData();
                t.hitMarioHead();
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
