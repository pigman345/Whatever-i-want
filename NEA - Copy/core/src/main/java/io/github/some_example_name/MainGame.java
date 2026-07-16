package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import java.awt.*;

public class MainGame extends Game {

    public MainGame() {

    }

    @Override
    public void create() {
        setScreen((Screen) new GameScreen());
        if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            setScreen((Screen) new MainMenu());

        }
    }


}
