package com.hellouniverse.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.hellouniverse.game.Screens.GameScreen;

/**
 * Created by icypr on 05/03/2016.
 */
public class Bricks extends TiledObject{
    public Bricks(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
    }
}
