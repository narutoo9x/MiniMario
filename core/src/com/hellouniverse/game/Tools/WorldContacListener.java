package com.hellouniverse.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.hellouniverse.game.MiniMario;
import com.hellouniverse.game.Sprites.Enemy;
import com.hellouniverse.game.Sprites.Mario;

/**
 * Created by icypr on 05/03/2016.
 */
public class WorldContacListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int def = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;
        switch (def) {
            case MiniMario.ENEMY_BIT | MiniMario.OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits == MiniMario.ENEMY_BIT)
                    ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            case MiniMario.MARIO_BIT | MiniMario.ENEMY_BIT:
                if (fixtureA.getFilterData().categoryBits == MiniMario.MARIO_BIT)
                    ((Mario) fixtureA.getUserData()).hit((Enemy) fixtureB.getUserData());
                else
                    ((Mario) fixtureB.getUserData()).hit((Enemy) fixtureA.getUserData());
                break;
            case MiniMario.ENEMY_BIT | MiniMario.ENEMY_BIT:
                ((Enemy) fixtureA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            case MiniMario.ENEMY_HEAD_BIT | MiniMario.MARIO_BIT:
                if (fixtureA.getFilterData().categoryBits == MiniMario.ENEMY_HEAD_BIT)
                    ((Enemy) fixtureA.getUserData()).hitOnHead((Mario) fixtureB.getUserData());
                else
                    ((Enemy) fixtureB.getUserData()).hitOnHead((Mario) fixtureA.getUserData());
                break;
            case MiniMario.MARIO_BIT | MiniMario.HOLE_BIT:
                if (fixtureA.getFilterData().categoryBits == MiniMario.MARIO_BIT)
                    ((Mario) fixtureA.getUserData()).die();
                else
                    ((Mario) fixtureB.getUserData()).die();
                break;
            case MiniMario.MARIO_BIT | MiniMario.CASTLE_BIT:
                if (fixtureA.getFilterData().categoryBits == MiniMario.MARIO_BIT)
                    ((Mario) fixtureA.getUserData()).Win();
                else
                    ((Mario) fixtureB.getUserData()).Win();
                break;
//            case MiniMario.FIRE_BIT | MiniMario.OBJECT_BIT:
//                if (fixtureA.getFilterData().categoryBits == MiniMario.FIRE_BIT)
//                    ((FireBall)fixtureA.getUserData()).setDestroyed();
//                else
//                    ((FireBall)fixtureB.getUserData()).setDestroyed();
//                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
