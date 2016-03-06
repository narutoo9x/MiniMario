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
import com.hellouniverse.game.Scenes.HUD;

/**
 * Created by icypr on 05/03/2016.
 */
public class GameOverScreen implements Screen {

    private Viewport viewport;
    private Stage stage;

    private Game game;

    public GameOverScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(MiniMario.WIDTH, MiniMario.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MiniMario) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER!!", font);
        Label lifeNumber = new Label(String.format("MARIO X %02d", HUD.getLife()), font);
        Label aContinue = new Label("Touch to continue!!", font);
        Label playAgain = new Label("Touch to play again!", font);

        if (HUD.getLife() > 0) {
            table.add(lifeNumber).expandX();
            table.row();
            table.add(aContinue).expandX().padTop(10);
        } else {
            table.add(gameOverLabel).expandX();
            table.row();
            table.add(playAgain).expandX().padTop(10);
        }

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
            if (HUD.getLife() == 0)
                HUD.setLife(10);
            game.setScreen(new GameScreen((MiniMario) game, HUD.getLife()));
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
        stage.dispose();
    }
}
