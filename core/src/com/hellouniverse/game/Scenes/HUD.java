package com.hellouniverse.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hellouniverse.game.MiniMario;
import com.hellouniverse.game.Sprites.Mario;

/**
 * Created by icypr on 01/03/2016.
 */
public class HUD implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private static int life;
    private static Label lifeLabel;
    private Label marioLabel;

    public HUD(SpriteBatch sb , int life) {
        this.life = life;

        viewport = new FitViewport(MiniMario.WIDTH, MiniMario.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        marioLabel = new Label("MARIO", font);
        lifeLabel = new Label(String.format("%03d", life), font);

        table.add(marioLabel).expandX().padTop(10);
        table.row();
        table.add(lifeLabel).expandX().padTop(2);

        stage.addActor(table);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public static void minusLife() {
        life = life - 1;
        lifeLabel.setText(String.format("%03d", life));
    }

    public static int getLife() {
        return life;
    }
    public static void setLife(int l) {
        life = l;
    }
}
