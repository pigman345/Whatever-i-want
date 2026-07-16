package io.github.some_example_name;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainMenu extends ScreenAdapter {
    MainGame game;
    public void MainGame(MainGame game){

this.game = game;}

    private Player p;
    ShapeRenderer sr = new ShapeRenderer();
    @Override
    public void show() {
        p = new Player(0,0,30,40,0,0, Color.GREEN,true );
    }

    @Override
    public void render(float delta) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        p.draw(sr);

        sr.end();
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

    }
}

