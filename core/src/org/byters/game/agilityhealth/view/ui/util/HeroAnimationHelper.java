package org.byters.game.agilityhealth.view.ui.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.byters.engine.controller.ControllerResources;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheResources;

import java.lang.ref.WeakReference;

public class HeroAnimationHelper {
    private WeakReference<CacheResources> refCacheResources;
    private WeakReference<ControllerResources> refControllerResources;

    private Animation<TextureRegion> animationMoveRight, animationMoveLeft, animationStandLeft, animationStandRight;
    private TextureRegion tAttackRight, tAttackLeft;
    private int moveFrameNum;
    private int standFrameNum;

    private TextureAtlas tHeroAtlas;

    public HeroAnimationHelper(ControllerResources controllerResources, CacheResources cacheMeta) {
        this.refControllerResources = new WeakReference<>(controllerResources);
        this.refCacheResources = new WeakReference<>(cacheMeta);
    }

    public void load() {

        tHeroAtlas = new TextureAtlas(refCacheResources.get().TEXTURE_ATLAS_HERO);

        tAttackRight = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_ATTACK_RIGHT);
        tAttackLeft = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_ATTACK_LEFT);

        TextureRegion[] framesMoveRight = new TextureRegion[4];
        framesMoveRight[0] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_MOVE_RIGHT_0);
        framesMoveRight[1] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_MOVE_RIGHT_1);
        framesMoveRight[2] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_MOVE_RIGHT_2);
        framesMoveRight[3] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_MOVE_RIGHT_3);

        TextureRegion[] framesMoveLeft = new TextureRegion[4];
        framesMoveLeft[0] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_MOVE_LEFT_0);
        framesMoveLeft[1] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_MOVE_LEFT_1);
        framesMoveLeft[2] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_MOVE_LEFT_2);
        framesMoveLeft[3] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_MOVE_LEFT_3);

        TextureRegion[] framesStandLeft = new TextureRegion[2];
        framesStandLeft[0] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_STAND_LEFT_0);
        framesStandLeft[1] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_STAND_LEFT_1);

        TextureRegion[] framesStandRight = new TextureRegion[2];
        framesStandRight[0] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_STAND_RIGHT_0);
        framesStandRight[1] = tHeroAtlas.findRegion(refCacheResources.get().REGION_HERO_STAND_RIGHT_1);

        animationMoveRight = new Animation<>(1/refCacheResources.get().ANIMATION_HERO_MOVE_FPS, framesMoveRight);
        animationMoveRight.setPlayMode(Animation.PlayMode.LOOP);

        animationMoveLeft = new Animation<>(1/refCacheResources.get().ANIMATION_HERO_MOVE_FPS, framesMoveLeft);
        animationMoveLeft.setPlayMode(Animation.PlayMode.LOOP);

        animationStandLeft = new Animation<>(1/refCacheResources.get().ANIMATION_HERO_STAND_FPS, framesStandLeft);
        animationStandLeft.setPlayMode(Animation.PlayMode.LOOP);

        animationStandRight = new Animation<>(1/refCacheResources.get().ANIMATION_HERO_STAND_FPS, framesStandRight);
        animationStandRight.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void draw(SpriteBatch spriteBatch, float heroPosX, float heroPosY, boolean isRight, boolean isAttack, boolean isMove) {

        TextureRegion texture = isRight
                ? animationStandRight.getKeyFrames()[standFrameNum]
                : animationStandLeft.getKeyFrames()[standFrameNum];

        texture = !isMove
                ? texture : isRight
                ? animationMoveRight.getKeyFrames()[moveFrameNum]
                : animationMoveLeft.getKeyFrames()[moveFrameNum];

        texture = !isAttack
                ? texture
                : isRight
                ? tAttackRight
                : tAttackLeft;

        DrawHelper.drawCentered(spriteBatch,
                texture,
                heroPosX + getDeltaX(texture), heroPosY);
    }

    private float getDeltaX(TextureRegion texture) {
        return texture == tAttackLeft
                ? refCacheResources.get().tAttackLeftDeltaX
                : texture == tAttackRight
                ? refCacheResources.get().tAttackRightDeltaX
                : 0;
    }

    public void update(float delta) {
        moveFrameNum = animationMoveRight.getKeyFrameIndex(((int) (System.currentTimeMillis() - refControllerResources.get().getGameTimeStartMillis())) / 1000f);
        standFrameNum = animationStandRight.getKeyFrameIndex(((int) (System.currentTimeMillis() - refControllerResources.get().getGameTimeStartMillis())) / 1000f);

    }

    public void dispose() {
        tHeroAtlas.dispose();
    }
}
