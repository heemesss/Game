package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BlockActor extends Actor {
    private Texture texture;
    private int width = 100, height = 100;

    public BlockActor(Texture texture, int x, int y){
        this.texture = texture;

        setSize(width, height);
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
