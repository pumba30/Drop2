package com.pandroidsoft.minigame.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pumba30 on 27.02.2015.
 */
public class MainScreenMenu implements Screen {

    final MyDrop2 game;
    OrthographicCamera camera;
    Texture imageTerra;
    Music wellSound;

    public MainScreenMenu(final MyDrop2 game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        imageTerra = new Texture(Gdx.files.internal("bg_well_screen.jpg")); //доб. текстуру заднего фона
        wellSound = Gdx.audio.newMusic(Gdx.files.internal("well_sound.mp3"));

    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.5f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);



        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();
        game.batch.draw(imageTerra, 0, 0);
        game.fontWelcomeScreen.draw(game.batch, "\"Поймай котейку - 2\"", 250, 300);
        game.fontWelcomeScreen.draw(game.batch, "Жмакни для начала игры по экрану!", 110, 250);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

    }
    @Override
    public void show() {
        wellSound.play();
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
        wellSound.dispose();

    }
}
