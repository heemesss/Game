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

    private Stage stage;
    private Viewport viewport;

    @Override
    public void show() {

        Texture textureBlock = new Texture("block.jpeg");

        // Prepare your screen here.
        viewport = new FitViewport(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        stage = new Stage(viewport);
        stage.addActor(new BlockActor(textureBlock, 0, 0));
        stage.addActor(new BlockActor(textureBlock, 200, 100));
        stage.addActor(new HeroActor());
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
