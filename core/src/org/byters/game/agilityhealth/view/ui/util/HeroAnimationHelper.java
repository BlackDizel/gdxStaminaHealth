package org.byters.game.agilityhealth.view.ui.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.byters.engine.controller.ControllerResources;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheResources;

import java.lang.ref.WeakReference;

public class HeroAnimationHelper {
    private WeakReference<CacheResources> refCacheResources;
    private WeakReference<ControllerResources> refControllerResources;

    private Animation<TextureAtlas.AtlasRegion> animationMoveRight, animationMoveLeft, animationStandLeft, animationStandRight;
    private TextureAtlas.AtlasRegion tAttackRight, tAttackLeft;
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

        animationMoveRight = new Animation<>(1 / refCacheResources.get().ANIMATION_HERO_MOVE_FPS, tHeroAtlas.findRegions(refCacheResources.get().REGION_HERO_MOVE_RIGHT));
        animationMoveRight.setPlayMode(Animation.PlayMode.LOOP);

        animationMoveLeft = new Animation<>(1 / refCacheResources.get().ANIMATION_HERO_MOVE_FPS, tHeroAtlas.findRegions(refCacheResources.get().REGION_HERO_MOVE_LEFT));
        animationMoveLeft.setPlayMode(Animation.PlayMode.LOOP);

        animationStandLeft = new Animation<>(1 / refCacheResources.get().ANIMATION_HERO_STAND_FPS, tHeroAtlas.findRegions(refCacheResources.get().REGION_HERO_STAND_LEFT));
        animationStandLeft.setPlayMode(Animation.PlayMode.LOOP);

        animationStandRight = new Animation<>(1 / refCacheResources.get().ANIMATION_HERO_STAND_FPS, tHeroAtlas.findRegions(refCacheResources.get().REGION_HERO_STAND_RIGHT));
        animationStandRight.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void draw(SpriteBatch spriteBatch, float heroPosX, float heroPosY, boolean isRight, boolean isAttack, boolean isMove) {

        TextureAtlas.AtlasRegion texture = isRight
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

        spriteBatch.draw(
                texture,
                heroPosX - texture.offsetX,
                heroPosY - texture.offsetY);
    }

    public void update(float delta) {
        moveFrameNum = animationMoveRight.getKeyFrameIndex(((int) (System.currentTimeMillis() - refControllerResources.get().getGameTimeStartMillis())) / 1000f);
        standFrameNum = animationStandRight.getKeyFrameIndex(((int) (System.currentTimeMillis() - refControllerResources.get().getGameTimeStartMillis())) / 1000f);

    }

    public void dispose() {
        tHeroAtlas.dispose();
    }
}
