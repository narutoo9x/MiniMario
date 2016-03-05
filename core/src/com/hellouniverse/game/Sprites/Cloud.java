//package com.hellouniverse.game.Sprites;
//
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.CircleShape;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.physics.box2d.World;
//import com.hellouniverse.game.MiniMario;
//import com.hellouniverse.game.Screens.GameScreen;
//
///**
// * Created by icypr on 05/03/2016.
// */
//public class Cloud extends Sprite {
//
//    private GameScreen screen;
//    private World world;
//    private TextureRegion cloud;
//    private Body b2body;
//    private Vector2 velovity;
//
//    public Cloud(GameScreen screen, float x, float y) {
//        this.screen = screen;
//        this.world = screen.getWorld();
//
//        velovity = new Vector2(-100f , 5f);
////        cloud = new TextureRegion(getTexture(), 385, 35 ,16, 32 );
//        cloud = new TextureRegion(screen.getAtlas().findRegion("cloud"), , 0 ,16, 24);
//        setPosition(x, y);
//        defineCloud();
//    }
//
//    public void defineCloud() {
//        BodyDef bDef = new BodyDef();
//        bDef.position.set(getX(), getY());
//        bDef.type = BodyDef.BodyType.DynamicBody;
//        b2body = world.createBody(bDef);
//        b2body.setLinearVelocity(velovity);
//
//        FixtureDef fDef = new FixtureDef();
//        CircleShape shape = new CircleShape();
//        shape.setRadius(4 / MiniMario.PPM);
//
//        // bit
//        fDef.filter.categoryBits = MiniMario.CLOUD_BIT;
//
//        fDef.shape = shape;
//        b2body.createFixture(fDef).setUserData(this);
//    }
//}
