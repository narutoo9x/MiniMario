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

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
}
