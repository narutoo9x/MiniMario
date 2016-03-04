package com.hellouniverse.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.hellouniverse.game.Screens.GameScreen;

/**
 * Created by icypr on 05/03/2016.
 */
public abstract class Enemy extends Sprite {
    protected World world;
    protected GameScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public Enemy(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1,0);
    }
    public abstract void update(float dt);
    protected abstract void defineEnemy();
    public void reverseVelocity(boolean x, boolean y) {
        if (x) {
            velocity.x = -velocity.x;
        }
        if (y){
            velocity.y = -velocity.y;
        }
    }
}
