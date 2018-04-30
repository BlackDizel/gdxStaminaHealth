package org.byters.game.agilityhealth.view.ui.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.byters.engine.controller.ControllerResources;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheResources;
import org.byters.game.agilityhealth.view.ui.FrameBufferDrawer;

import java.lang.ref.WeakReference;

public class MonsterAnimationTemplate {

    private String atlasFilename;
    private WeakReference<FrameBufferDrawer> refFrameBufferDrawer;
    private WeakReference<CacheResources> refCacheResources;
    private WeakReference<ControllerResources> refControllerResources;

    private TextureAtlas tMonsterAtlas;
    private Animation<TextureAtlas.AtlasRegion> aMove, aAttack, aStun, aDie;
    private TextureAtlas.AtlasRegion tDie;

    public MonsterAnimationTemplate(ControllerResources controllerResources,
                                    CacheResources cacheMeta,
                                    FrameBufferDrawer frameBufferDrawer,
                                    String atlasFilename) {
        this.refControllerResources = new WeakReference<>(controllerResources);
        this.refCacheResources = new WeakReference<>(cacheMeta);
        this.refFrameBufferDrawer = new WeakReference<>(frameBufferDrawer);
        this.atlasFilename = atlasFilename;
    }

    public void load() {
        tMonsterAtlas = new TextureAtlas(atlasFilename);

        aAttack = new Animation<>(
                1 / refCacheResources.get().ANIMATION_MONSTER_MOVE_FPS,
                tMonsterAtlas.findRegions(refCacheResources.get().REGION_MONSTER_ATTACK));
        aAttack.setPlayMode(Animation.PlayMode.NORMAL);

        aStun =
                new Animation<>(
                        1 / refCacheResources.get().ANIMATION_MONSTER_MOVE_FPS,
                        tMonsterAtlas.findRegions(refCacheResources.get().REGION_MONSTER_STUN));
        aStun.setPlayMode(Animation.PlayMode.NORMAL);

        aDie = new Animation<>(
                1 / refCacheResources.get().ANIMATION_MONSTER_MOVE_FPS,
                tMonsterAtlas.findRegions(refCacheResources.get().REGION_MONSTER_DIE));
        aDie.setPlayMode(Animation.PlayMode.NORMAL);
        tDie = aDie.getKeyFrames()[aDie.getKeyFrames().length - 1];

        aMove = new Animation<>(
                1 / refCacheResources.get().ANIMATION_MONSTER_MOVE_FPS,
                tMonsterAtlas.findRegions(refCacheResources.get().REGION_MONSTER_MOVE));
        aMove.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void draw(SpriteBatch spriteBatch,
                     TextureAtlas.AtlasRegion texture,
                     float monsterPosX,
                     float monsterPosY,
                     boolean isRight) {

        DrawHelper.draw(spriteBatch,
                texture,
                monsterPosX, texture.offsetX,
                monsterPosY, texture.offsetY,
                isRight);
    }

    public TextureAtlas.AtlasRegion getTextureCurrent(
            boolean isAttack,
            boolean isStun,
            long lastTimeMillisAttack,
            long lastTimeMillisStun) {

        int frameNum = 0;
        if (isAttack) {
            frameNum = aAttack.getKeyFrameIndex(((int) (System.currentTimeMillis() - lastTimeMillisAttack)) / 1000f);
            return aAttack.getKeyFrames()[frameNum];
        }

        if (isStun) {
            frameNum = aStun.getKeyFrameIndex(((int) (System.currentTimeMillis() - lastTimeMillisStun)) / 1000f);
            return aStun.getKeyFrames()[frameNum];//todo implement
        }

        frameNum = aMove.getKeyFrameIndex(((int) (System.currentTimeMillis() - refControllerResources.get().getGameTimeStartMillis())) / 1000f);
        return aMove.getKeyFrames()[frameNum];
    }

    public void dispose() {
        tMonsterAtlas.dispose();
    }

    public void addCorpse(float posX, float posY) {
        refFrameBufferDrawer.get().addTexture(tDie, posX - tDie.offsetX, posY - tDie.offsetY);
    }
}
