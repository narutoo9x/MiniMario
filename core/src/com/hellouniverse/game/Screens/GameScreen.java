package com.hellouniverse.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hellouniverse.game.MiniMario;
import com.hellouniverse.game.Scenes.HUD;
import com.hellouniverse.game.Sprites.Mario;

/**
 * Created by icypr on 28/02/2016.
 */
public class GameScreen implements Screen {

    private MiniMario game;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private HUD hud;

    // Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Mario player;

    public GameScreen(MiniMario game) {

        this.game = game;

        // camera follow mario
        camera = new OrthographicCamera();
        gamePort = new FitViewport(MiniMario.WIDTH / MiniMario.PPM, MiniMario.HEIGHT / MiniMario.PPM, camera);

        // hud for mario 's num of life
        hud = new HUD(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("minimap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ MiniMario.PPM);

        camera.position.set(gamePort.getWorldWidth()/ 2, gamePort.getScreenHeight() / 2, 0);

        // set gravity of x = 0, and y = -10
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
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
            body.createFixture(fDef);
        }

        player = new Mario(world);

    }


    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x <= 1)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        camera.position.x = player.b2body.getPosition().x;
        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        update(delta);

        // Clear the screen with black background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render the map
        renderer.render();

        // render Box2D
        b2dr.SHAPE_STATIC.set(1,0,0,1);
        b2dr.render(world, camera.combined);


        // Draw Mario's number of life
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
