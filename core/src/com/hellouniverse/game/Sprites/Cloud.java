package com.hellouniverse.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.hellouniverse.game.MiniMario;
import com.hellouniverse.game.Screens.GameScreen;

/**
 * Created by icypr on 05/03/2016.
 */
public class Cloud extends Enemy {
    private float stateTime;
    private Animation fly;
    private Array<TextureRegion> frames;

    public Cloud(GameScreen screen, float x, float y) {
        super(screen, x, y);
        velocity = new Vector2(-98f , 0);

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("cloud"), i*16, 0 ,16, 24));
        }
        fly = new Animation(0.5f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / MiniMario.PPM, 24 / MiniMario.PPM);
    }

    public void update(float dt) {
        stateTime += dt;
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(fly.getKeyFrame(stateTime, true));
    }

    @Override
    public void defineEnemy() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(), getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / MiniMario.PPM);

        // bit
        fDef.filter.categoryBits = MiniMario.CLOUD_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);
    }

    @Override
    public void hitOnHead(Mario mario) {
    }

}
