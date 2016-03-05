package com.hellouniverse.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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
public class Goomba extends Enemy{
    private float stateTime;
    private Animation walk;
    private Array<TextureRegion> frames;
    private boolean setDesTroy;
    private boolean desTroyed;


    public Goomba(GameScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i*16, 0 ,16, 16));
        }
        walk = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / MiniMario.PPM, 16 / MiniMario.PPM);
        setDesTroy = false;
        desTroyed = false;
    }

    public void update(float dt) {
        stateTime += dt;
        if (setDesTroy && !desTroyed) {
            world.destroyBody(b2body);
            desTroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
            stateTime = 0;

        } else if (!desTroyed) {

            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walk.getKeyFrame(stateTime, true));
        }
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

        PolygonShape head = new PolygonShape();
        Vector2[] Vector = new Vector2[4];
        Vector[0] = new Vector2(-5, 8).scl(1 / MiniMario.PPM);
        Vector[1] = new Vector2(5,8 ).scl(1 / MiniMario.PPM);
        Vector[2] = new Vector2(-3, 3).scl(1 / MiniMario.PPM);
        Vector[3] = new Vector2(3, 3).scl(1 / MiniMario.PPM);
        head.set(Vector);

        fDef.shape = head;
        fDef.restitution = 0.5f;
        fDef.filter.categoryBits = MiniMario.ENEMY_HEAD_BIT;
        b2body.createFixture(fDef).setUserData(this);
    }

    // delete goomba
    public void draw(Batch batch){
        if (!desTroyed || stateTime < .5)
            super.draw(batch);
    }
    @Override
    public void hitOnHead(Mario mario) {
        setDesTroy = true;
    }

}
