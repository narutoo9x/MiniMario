package com.hellouniverse.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.hellouniverse.game.MiniMario;
import com.hellouniverse.game.Screens.GameScreen;
import com.hellouniverse.game.Sprites.Bricks;
import com.hellouniverse.game.Sprites.Enemy;
import com.hellouniverse.game.Sprites.Goomba;
import com.hellouniverse.game.Sprites.Turtle;

/**
 * Created by icypr on 04/03/2016.
 */
public class WorldCreator {
    private Array<Goomba> goombas;
    private Array<Turtle> turtles;

    public WorldCreator(GameScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        // creat ground bodies and fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth()/2) / MiniMario.PPM, (rect.getY() + rect.getHeight() / 2) / MiniMario.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / MiniMario.PPM, rect.getHeight() / 2 / MiniMario.PPM);
            fDef.shape = shape;
            fDef.filter.categoryBits = MiniMario.OBJECT_BIT;
            body.createFixture(fDef);
        }

        // creat brick bodies and fixtures
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Bricks(screen, rect);
        }

        // create all gooba
        goombas = new Array<Goomba>();
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            goombas.add(new Goomba(screen, rect.getX() / MiniMario.PPM, rect.getY() / MiniMario.PPM));
        }

        //create all turtle
        turtles = new Array<Turtle>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen, rect.getX() / MiniMario.PPM, rect.getY() / MiniMario.PPM));
        }

    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }

    public Array<Enemy> getEnermies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(turtles);
        return enemies;
    }
}