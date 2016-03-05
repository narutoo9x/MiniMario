package com.hellouniverse.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.hellouniverse.game.MiniMario;
import com.hellouniverse.game.Screens.GameScreen;

/**
 * Created by icypr on 05/03/2016.
 */
public class Turtle extends Enemy {

    private float stateTime;
    private Animation walk;
    private Array<TextureRegion> frames;

    public Turtle(GameScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("tutle2"), i*16, 0 ,16, 16));
        }
        walk = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / MiniMario.PPM, 16 / MiniMario.PPM);
    }

    public void update(float dt) {
        stateTime += dt;

        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(walk.getKeyFrame(stateTime, true));
    }

    @Override
    public void defineEnemy() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MiniMario.PPM);

        // bit
        fDef.filter.categoryBits = MiniMario.ENEMY_BIT;
        fDef.filter.maskBits = MiniMario.GROUND_BIT |
                MiniMario.ENEMY_BIT |
                MiniMario.OBJECT_BIT |
                MiniMario.MARIO_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);

//        fDef.filter.categoryBits = MiniMario.TURTLE_BIT;

//        b2body.createFixture(fDef);
    }
}
