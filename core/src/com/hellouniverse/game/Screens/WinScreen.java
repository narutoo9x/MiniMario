package com.hellouniverse.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hellouniverse.game.MiniMario;

/**
 * Created by icypr on 05/03/2016.
 */
public class WinScreen implements Screen {


    private Viewport viewport;
    private Stage stage;

    private Game game;

    public WinScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(MiniMario.WIDTH, MiniMario.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MiniMario) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("CONGRATULATION TO YOU!! ", font);
        Label play = new Label("PLAY GAME", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(play).expandX().padTop(20);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            game.setScreen(new GameScreen((MiniMario) game, 10));
            dispose();
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        dispose();
    }
}
