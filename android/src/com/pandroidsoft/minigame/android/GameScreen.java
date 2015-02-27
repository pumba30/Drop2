package com.pandroidsoft.minigame.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pumba30 on 27.02.2015.
 */
public class GameScreen implements Screen {
    final MyDrop2 game;

    Texture dropImage;
    Texture bucketImage;
    Texture bg_level_1;
    Sound dropSound;
    Sound missSoundCat;
    Music rainMusic;
    Music bg_music_lv1;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    int dropsNotGathered;
    Vector3 touchPos = new Vector3();

    public GameScreen(final MyDrop2 game) {
        this.game = game;

        // загрузка изображений для капли и ведра, 64x64 пикселей каждый
        dropImage = new Texture(Gdx.files.internal("cat.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        bg_level_1 = new Texture(Gdx.files.internal("bg_level_1.jpg"));

        // загрузка звукового эффекта падающей капли и фоновой "музыки" дождя
        missSoundCat = Gdx.audio.newSound(Gdx.files.internal("catmeow2.wav"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("cat1.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("soundrain.mp3"));
        bg_music_lv1 = Gdx.audio.newMusic(Gdx.files.internal("bg_music_lv1.mp3"));

        rainMusic.setLooping(true);
        bg_music_lv1.setLooping(true);


        // создает камеру
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // создается Rectangle для представления ведра
        bucket = new Rectangle();
        // центрируем ведро по горизонтали
        bucket.x = 800 / 2 - 64 / 2;
        // размещаем на 20 пикселей выше нижней границы экрана.
        bucket.y = 20;

        bucket.width = 64;
        bucket.height = 64;

        // создает массив капель и возрождает первую
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // очищаем экран темно-синим цветом.
        // Аргументы для glClearColor красный, зеленый
        // синий и альфа компонент в диапазоне [0,1]
        // цвета используемого для очистки экрана.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // сообщает камере, что нужно обновить матрицы.
        camera.update();

        // сообщаем SpriteBatch о системе координат
        // визуализации указанных для камеры.
        game.batch.setProjectionMatrix(camera.combined);

        // начитаем новую серию, рисуем ведро и
        // все капли
        game.batch.begin();

        game.batch.draw(bg_level_1, 0, 0);
        game.fontGameScreen.draw(game.batch, "Поймано котов: " + " " + dropsGathered, 10, 470);
        game.fontGameScreen.draw(game.batch, "Упущено котов: " + " " + dropsNotGathered, 477, 475);
        game.batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        // обработка пользовательского ввода, ведро бегает за пальцем
        if (Gdx.input.isTouched()) {
            //touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

        // убедитесь, что ведро остается в пределах экрана
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // проверка, нужно ли создавать новую каплю
        if (TimeUtils.nanoTime() - lastDropTime > 500000000)
            spawnRaindrop();

        // движение капли, удаляем все капли выходящие за границы экрана
        // или те, что попали в ведро. Воспроизведение звукового эффекта
        // при попадании.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                iter.remove();
                dropsNotGathered++;
                missSoundCat.play();
            }
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // воспроизведение фоновой музыки
        // когда отображается экран
        rainMusic.play();
        bg_music_lv1.play();

    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}
