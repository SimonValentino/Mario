package com.svalentino.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
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

        if (fixtureA.getUserData() == null || fixtureB.getUserData() == null)
            return;

        if (fixtureA.getUserData().equals("head") || fixtureB.getUserData().equals("head")) {
            Fixture marioHead = fixtureA.getUserData().equals("head") ? fixtureA : fixtureB;
            Fixture colFixture = fixtureA == marioHead ? fixtureB : fixtureA;

            if (InteractableObject.class.isAssignableFrom(colFixture.getUserData().getClass())) {
                InteractableObject t = (InteractableObject) colFixture.getUserData();
                t.hitMarioHead();
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
