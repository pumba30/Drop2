package com.pandroidsoft.minigame.android;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MyDrop2 extends Game {
    SpriteBatch batch;
    BitmapFont fontWelcomeScreen;
    BitmapFont fontGameScreen;
    private static final String FONT_CHARACTERS
            = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    public void create() {
        //Gdx.input.setOnscreenKeyboardVisible(true); test proyden
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("RobotoSlab-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;


        parameter.characters = FONT_CHARACTERS;

        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
// границы букв
        parameter.borderColor = Color.OLIVE;
        parameter.borderWidth = 3;

// тени
        parameter.shadowColor = Color.DARK_GRAY;
        parameter.shadowOffsetX = 5;
        parameter.shadowOffsetY = 5;


        fontWelcomeScreen = generator.generateFont(parameter);
        fontGameScreen = generator.generateFont(parameter);

        fontWelcomeScreen.setColor(Color.GREEN);
        fontGameScreen.setColor(Color.ORANGE);

        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        this.setScreen(new MainScreenMenu(this));
    }

    public void render() {
        super.render(); // важно!
    }

    public void dispose() {
        batch.dispose();
        fontWelcomeScreen.dispose();
        fontGameScreen.dispose();
    }

}
