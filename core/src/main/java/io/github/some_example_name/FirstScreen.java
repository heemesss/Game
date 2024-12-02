package io.github.some_example_name;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    private int countFramesAnimHero = 8;

    private Stage stage;
    private Viewport viewport;

    @Override
    public void show() {
        // Animation for Hero
        Texture[] textureHeroAnimStayRight, textureHeroAnimStayLeft,
            textureHeroAnimWalkRight, textureHeroAnimWalkLeft;

        textureHeroAnimStayRight = new Texture[countFramesAnimHero];
        textureHeroAnimStayLeft = new Texture[countFramesAnimHero];
        textureHeroAnimWalkLeft = new Texture[countFramesAnimHero];
        textureHeroAnimWalkRight = new Texture[countFramesAnimHero];
        for (int i = 0; i < countFramesAnimHero; i++){
            textureHeroAnimStayLeft[i] = new Texture("hero_anim_left_stay/Sprite-000" +
                (i + 1) + ".png");
            textureHeroAnimStayRight[i] = new Texture("hero_anim_right_stay/Sprite-000" +
                (i + 1) + ".png");
            textureHeroAnimWalkRight[i] = new Texture("hero_anim_right_walk/Sprite-000" +
                (i + 1) + ".png");
            textureHeroAnimWalkLeft[i] = new Texture("hero_anim_left_walk/Sprite-000" +
                (i + 1) + ".png");
        }
        // End Animation for Hero

        // Prepare your screen here.
        viewport = new FitViewport(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        stage = new Stage(viewport);
        stage.addActor(new HeroActor(textureHeroAnimStayRight, textureHeroAnimStayLeft,
            textureHeroAnimWalkRight, textureHeroAnimWalkLeft));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        // Draw your screen here. "delta" is the time since last render in seconds.
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
