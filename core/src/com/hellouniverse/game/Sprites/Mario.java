package com.hellouniverse.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.hellouniverse.game.MiniMario;
import com.hellouniverse.game.Scenes.HUD;
import com.hellouniverse.game.Screens.GameScreen;

/**
 * Created by icypr on 04/03/2016.
 */
public class Mario extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private TextureRegion marioStand;
    private TextureRegion marioDead;
    private Animation marioRun;
    private Animation marioJump;

    private float stateTimer;
    private boolean runningRight;
    private boolean marioIsDead;

    public Mario(GameScreen screen) {
        super(screen.getAtlas().findRegion("big_mario"));
        this.world = screen.getWorld();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i*16 + 1 , 27, 16,32));
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i*16 + 1, 27, 16, 32));
        }
        marioJump = new Animation(0.1f, frames);
        frames.clear();
        marioStand = new TextureRegion(getTexture(),1 , 27, 16, 32);
        marioDead = new TextureRegion(getTexture(),129, 27 ,16, 32 );

        defineMario();
        setBounds(1, 27, 16/MiniMario.PPM, 32/MiniMario.PPM);
        setRegion(marioStand);
        marioIsDead = false;
    }

    public void update(float dt) {
        if (isDead()) {
            die();
        }
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 6 / MiniMario.PPM);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        // get mario current state such as jumping running
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case DEAD:
                region = marioDead;
                break;
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        // if mario running left and
        if (b2body.getLinearVelocity().x < 0  || !runningRight && !region.isFlipX()) {
            region.flip(true, false);
            runningRight =false;


        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (marioIsDead)
            return State.DEAD;
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return  State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public void defineMario() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(32 / MiniMario.PPM, 32 / MiniMario.PPM);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MiniMario.PPM);


        fDef.filter.categoryBits = MiniMario.MARIO_BIT;
        fDef.filter.maskBits = MiniMario.GROUND_BIT |
                MiniMario.OBJECT_BIT |
                MiniMario.ENEMY_BIT |
                MiniMario.ENYMY_HEAD_BIT |
                MiniMario.TURTLE_BIT |
                MiniMario.NOTHING_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);

        shape.setPosition(new Vector2(0, -14 / MiniMario.PPM));
        b2body.createFixture(fDef).setUserData(this);

        // create a sensor when mario touch the bricks
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MiniMario.PPM, 7 / MiniMario.PPM), new Vector2(2 / MiniMario.PPM, 7 / MiniMario.PPM));
        fDef.shape = head;
        fDef.isSensor = true;

        b2body.createFixture(fDef).setUserData("head");
    }
    public void die() {
        if (!marioIsDead) {
            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = MiniMario.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
            HUD.minusLife();
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public boolean isDead() {
        return marioIsDead;
    }

    public void hit(Enemy enemy) {
        if (enemy instanceof Turtle){
            die();
        }
    }
}
