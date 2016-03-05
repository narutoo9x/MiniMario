package com.hellouniverse.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.hellouniverse.game.Screens.GameScreen;

/**
 * Created by icypr on 05/03/2016.
 */
public class Cloud extends Sprite {

    private GameScreen screen;
    private World world;
    private TextureRegion cloud;

    public Cloud(GameScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        cloud = new TextureRegion(screen.getAtlas().findRegion("cloud"), 389, 35, 16,24);
        setPosition(x, y);
        defineCloud();
    }

    public void defineCloud() {
    }
}
