package com.hellouniverse.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hellouniverse.game.Screens.GameScreen;

public class MiniMario extends Game {
    public SpriteBatch batch;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 420;

    // pixel per meter
    public static final float PPM = 100;

    // Box2d Collision bit
    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short MARIO_BIT = 2;
    public static final short OBJECT_BIT = 4;
    public static final short ENEMY_BIT = 8;
    public static final short ENEMY_HEAD_BIT = 16;
    public static final short CASTLE_BIT = 32;
    public static final short HOLE_BIT = 64;
    public static final short CLOUD_BIT = 128;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new GameScreen(this, 10));

    }


    @Override
    public void render() {
        super.render();
    }
}
