package com.hellouniverse.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.hellouniverse.game.MiniMario;
import com.hellouniverse.game.Screens.GameScreen;

/**
 * Created by icypr on 05/03/2016.
 */
public class Water extends TiledObject {

    public Water(GameScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MiniMario.WATER_BIT);
    }
}