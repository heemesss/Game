package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HeroActor extends Actor {
    private int width = 100, height = 100;

    private short countFramesAnimHero = 8, UP = 2, DOWN = 3, RIGHT = 1, LEFT = 0, route;

    private Animation<Texture> stayRightAnimation, stayLeftAnimation,
        walkLeftAnimation, walkRightAnimation,
        jumpRightAnimation, jumpLeftAnimation;
    private short moveSpeed = 5, jumpSpeed = 3;

    private float stateTime;

    private boolean isWalk = false, isJump = false, isFly = false;

    public HeroActor(){
        Texture[] textureHeroAnimStayRight, textureHeroAnimStayLeft,
            textureHeroAnimWalkRight, textureHeroAnimWalkLeft,
            textureHeroAnimJumpRight, textureHeroAnimJumpLeft;

        textureHeroAnimStayRight = new Texture[countFramesAnimHero];
        textureHeroAnimStayLeft = new Texture[countFramesAnimHero];
        textureHeroAnimWalkRight = new Texture[countFramesAnimHero];
        textureHeroAnimWalkLeft = new Texture[countFramesAnimHero];
        textureHeroAnimJumpRight = new Texture[countFramesAnimHero];
        textureHeroAnimJumpLeft = new Texture[countFramesAnimHero];

        for (int i = 0; i < countFramesAnimHero; i++){
            textureHeroAnimStayRight[i] = new Texture("hero_anim_right_stay/Sprite-000" +
                (i + 1) + ".png");
            textureHeroAnimStayLeft[i] = new Texture("hero_anim_left_stay/Sprite-000" +
                (i + 1) + ".png");
            textureHeroAnimWalkRight[i] = new Texture("hero_anim_right_walk/Sprite-000" +
                (i + 1) + ".png");
            textureHeroAnimWalkLeft[i] = new Texture("hero_anim_left_walk/Sprite-000" +
                (i + 1) + ".png");
            textureHeroAnimJumpRight[i] = new Texture("hero_anim_right_jump/Sprite-000" +
                (i + 1) + ".png");
            textureHeroAnimJumpLeft[i] = new Texture("hero_anim_left_jump/Sprite-000" +
                (i + 1) + ".png");
        }

        stayRightAnimation = new Animation<>(0.1f, textureHeroAnimStayRight);
        stayLeftAnimation = new Animation<>(0.1f, textureHeroAnimStayLeft);
        walkRightAnimation = new Animation<>(0.1f, textureHeroAnimWalkRight);
        walkLeftAnimation = new Animation<>(0.1f, textureHeroAnimWalkLeft);
        jumpRightAnimation = new Animation<>(0.1f, textureHeroAnimJumpRight);
        jumpLeftAnimation = new Animation<>(0.1f, textureHeroAnimJumpLeft);

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
        if (isJump) {
            if (route == RIGHT) {
                currentFrame = jumpRightAnimation.getKeyFrame(stateTime, false);
                if (jumpRightAnimation.isAnimationFinished(stateTime)) {
                    isJump = false;
                }
            } else {
                currentFrame = jumpLeftAnimation.getKeyFrame(stateTime, false);
                if (jumpLeftAnimation.isAnimationFinished(stateTime)) {
                    isJump = false;
                }
            }
        }


        else if (route == RIGHT && isWalk) {
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

        Stage stage = getStage();

        multi_touch(stage);


        Actor actor;

        if (isJump)
            setY(getY() + jumpSpeed);

        // down
        if ((actor = check_collision(stage, DOWN)) != null){
            isFly = false;
            setY(actor.getY() + actor.getHeight());
        }

//        // left
//        if ((actor = check_collision(stage, LEFT)) != null){
//            setX(actor.getX() + actor.getWidth());
//        }
//
//        // right
//        if ((actor = check_collision(stage, RIGHT)) != null) {
//            setX(actor.getX() - getWidth() - 1);
//        }

        // up
        if ((actor = check_collision(stage, UP)) != null) {
            setY(actor.getY() - getHeight());
        }

        if (check_collision(stage, DOWN) == null && !isJump) {
            setY(getY() - jumpSpeed);
        }
        setX(Math.min(Main.SCREEN_WIDTH - getWidth(), getX()));
        setY(Math.min(Main.SCREEN_HEIGHT - getHeight(), getY()));

        setX(Math.max(0, getX()));
        setY(Math.max(0, getY()));
    }

    private void multi_touch(Stage stage) {
        if (!Gdx.input.isTouched()){ // control
            isWalk = false;
        }
        for (int i = 0; Gdx.input.isTouched(i); i++){
            Vector2 touch = getStage().screenToStageCoordinates(new Vector2(Gdx.input.getX(i),
                Gdx.input.getY(i)));
            if (touch.x > (float) Main.SCREEN_WIDTH * 0.75){ // Right
                Actor actor;
                setX(getX() + moveSpeed);
                if ((actor = check_collision(stage, RIGHT)) != null)
                    setX(actor.getX() - getWidth());
                isWalk = true;
                route = RIGHT;
            } else if (touch.x > (float) Main.SCREEN_WIDTH * 0.5){ // Left
                Actor actor;
                setX(getX() - moveSpeed);
                if ((actor = check_collision(stage, LEFT)) != null)
                    setX(actor.getX() + actor.getWidth());
                isWalk = true;
                route = LEFT;
            } else {
                if (check_collision(stage, DOWN) != null || getY() == 0){
                    isJump = true;
                    stateTime = 0;
                }
            }
        }
    }

    private Actor check_collision(Stage stage, int route){
        if (route == RIGHT) {
            for (int i = 1; i < getHeight(); i++)
                if (stage.hit(getX() + getWidth(), getY() + i, true) != null)
                    return stage.hit(getX() + getWidth(), getY() + i, true);
        } else if (route == LEFT) {
            for (int i = 1; i < getHeight(); i++)
                if (stage.hit(getX() - 1, getY() + i, true) != null)
                    return stage.hit(getX() - 1, getY() + i, true);
        } else if (route == UP) {
            for (int i = 1; i < getWidth(); i++)
                if (stage.hit(getX() + i, getY() + getHeight() + 1, true) != null)
                    return stage.hit(getX() + i, getY() + getHeight(), true);
        } else if (route == DOWN) {
            for (int i = 1; i < getWidth(); i++)
                if (stage.hit(getX() + i, getY() - 1, true) != null)
                    return stage.hit(getX() + i, getY() - 1, true);
        }
        return null;
    }
}
