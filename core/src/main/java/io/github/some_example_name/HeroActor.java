package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HeroActor extends Actor {
    private int width = 100, height = 100;

    private Animation<Texture> stayRightAnimation, stayLeftAnimation,
        walkLeftAnimation, walkRightAnimation;
    private int moveSpeed = 5;

    private float stateTime;

    private boolean route, isWalk = false;
    private final boolean RIGHT = true, LEFT = false;

    public HeroActor(Texture[] stayRightTextures, Texture[] stayLeftTextures,
                     Texture[] walkRightTextures, Texture[] walkLeftTextures){
        stayRightAnimation = new Animation<>(0.1f, stayRightTextures);
        stayLeftAnimation = new Animation<>(0.1f, stayLeftTextures);
        walkRightAnimation = new Animation<>(0.1f, walkRightTextures);
        walkLeftAnimation = new Animation<>(0.1f, walkLeftTextures);

        // Center Screen
        setPosition((float) Main.SCREEN_WIDTH / 2 - (float) width / 2,
            (float) Main.SCREEN_HEIGHT / 2 - (float) height / 2);
        setSize(width, height);

        // Start Time
        stateTime = 0f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame;
        if (route == RIGHT && isWalk) {
            currentFrame = walkRightAnimation.getKeyFrame(stateTime, true);
        } else if (route == RIGHT) {
            currentFrame = stayRightAnimation.getKeyFrame(stateTime, true);
        } else if (route == LEFT && isWalk) {
            currentFrame = walkLeftAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = stayLeftAnimation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Vector2 vector2 = getStage().screenToStageCoordinates(new Vector2(Gdx.input.getX(),
            Gdx.input.getY()));

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            route = RIGHT;
            isWalk = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            route = LEFT;
            isWalk = true;
        } else {
            isWalk = false;
        }

        if (!Gdx.input.isTouched()){
            isWalk = false;
        } else if (vector2.x - getX() > getWidth()) {
            route = RIGHT;
            isWalk = true;
            setX(getX() + moveSpeed);
        } else if (getX() - vector2.x > 0){
            route = LEFT;
            isWalk = true;
            setX(getX() - moveSpeed);
        }


        if (!Gdx.input.isTouched()){
            isWalk = false;
        } else if (vector2.y - getY() > getHeight()) {
            isWalk = true;
            setY(getY() + moveSpeed);
        } else if (getY() - vector2.y > 0){
            isWalk = true;
            setY(getY() - moveSpeed);
        }

        setX(Math.min(Main.SCREEN_WIDTH - getWidth(), getX()));
        setY(Math.min(Main.SCREEN_HEIGHT - getHeight(), getY()));

        setX(Math.max(0, getX()));
        setY(Math.max(0, getY()));
    }
}
