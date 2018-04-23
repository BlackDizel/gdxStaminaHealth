package org.byters.game.agilityhealth.view.ui.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.byters.engine.controller.ControllerResources;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheResources;

import java.lang.ref.WeakReference;

public class MonstersAnimationHelper {

    private WeakReference<CacheResources> refCacheResources;
    private WeakReference<ControllerResources> refControllerResources;

    private Animation<TextureAtlas.AtlasRegion> animationMove;
    private TextureAtlas.AtlasRegion tAttack, tStun, tDie;
    private int moveFrameNum;

    private TextureAtlas tMonsterAtlas;

    public MonstersAnimationHelper(ControllerResources controllerResources, CacheResources cacheMeta) {
        this.refControllerResources = new WeakReference<>(controllerResources);
        this.refCacheResources = new WeakReference<>(cacheMeta);
    }

    public void load() {
        tMonsterAtlas = new TextureAtlas(refCacheResources.get().TEXTURE_ATLAS_MONSTER);

        tAttack = tMonsterAtlas.findRegion(refCacheResources.get().REGION_MONSTER_ATTACK);
        tStun = tMonsterAtlas.findRegion(refCacheResources.get().REGION_MONSTER_STUN);
        tDie = tMonsterAtlas.findRegion(refCacheResources.get().REGION_MONSTER_DIE);

        animationMove = new Animation<>(
                1 / refCacheResources.get().ANIMATION_MONSTER_MOVE_FPS,
                tMonsterAtlas.findRegions(refCacheResources.get().REGION_MONSTER_MOVE));
        animationMove.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void draw(SpriteBatch spriteBatch,
                     float monsterPosX,
                     float monsterPosY,
                     boolean isRight,
                     boolean isAttack,
                     boolean isStun) {

        TextureAtlas.AtlasRegion texture = animationMove.getKeyFrames()[moveFrameNum];
        if (isAttack)
            texture = tAttack;
        if (isStun)
            texture = tStun;


        DrawHelper.draw(spriteBatch,
                texture,
                monsterPosX - texture.offsetX,
                monsterPosY - texture.offsetY,
                isRight);
    }

    public void update(float delta) {
        moveFrameNum = animationMove.getKeyFrameIndex(((int) (System.currentTimeMillis() - refControllerResources.get().getGameTimeStartMillis())) / 1000f);
    }

    public void dispose() {
        tMonsterAtlas.dispose();
    }
}
