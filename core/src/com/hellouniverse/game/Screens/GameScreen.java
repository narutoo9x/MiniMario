package com.hellouniverse.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hellouniverse.game.MiniMario;
import com.hellouniverse.game.Scenes.HUD;
import com.hellouniverse.game.Sprites.Enemy;
import com.hellouniverse.game.Sprites.Mario;
import com.hellouniverse.game.Tools.WorldContacListener;
import com.hellouniverse.game.Tools.WorldCreator;

/**
 * Created by icypr on 28/02/2016.
 */
public class GameScreen implements Screen {

    private MiniMario game;
    private TextureAtlas atlas;

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
    private WorldCreator creator;

    private Mario player;
    private int life;

    public GameScreen(MiniMario game, int life) {
        // Load Mario and other entities
        atlas = new TextureAtlas("Mario_entities.pack");

        this.game = game;
        this.life = life;

        // camera follow mario
        camera = new OrthographicCamera();
        gamePort = new FitViewport(MiniMario.WIDTH / MiniMario.PPM, MiniMario.HEIGHT / MiniMario.PPM, camera);

        // hud for mario 's num of life
        hud = new HUD(game.batch, this.life);

        maploader = new TmxMapLoader();
        map = maploader.load("minimap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ MiniMario.PPM);

        camera.position.set(gamePort.getWorldWidth()/ 2, gamePort.getScreenHeight() / 2, 0);

        // set gravity of x = 0, and y = -10
        world = new World(new Vector2(0, -12), true);
        b2dr = new Box2DDebugRenderer();

        creator = new WorldCreator(this);

        player = new Mario(this);

        world.setContactListener(new WorldContacListener());
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 3.8f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x <= 1)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1 / 60f, 6, 2);
        for (Enemy enemy : creator.getEnermies()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / MiniMario.PPM);
                enemy.b2body.setActive(true);
        }
        player.update(dt);

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

        // Draw Mario and goombla
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy: creator.getEnermies()) {
            enemy.draw(game.batch);
        }
        game.batch.end();



        // Draw Mario's number of life
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        gameOver();
        if (player.isWin()) {
            game.setScreen(new WinScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }
    @Override
    public void resume() {

    }

    public void gameOver() {
        if (player.currentState == Mario.State.DEAD) {
            game.setScreen(new GameOverScreen(game));
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
