package org.byters.game.agilityhealth.view.ui.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.byters.engine.controller.ControllerResources;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheResources;
import org.byters.game.agilityhealth.controller.data.memorycache.util.MonsterSpawnHelper;
import org.byters.game.agilityhealth.view.presenter.PresenterScreenGame;
import org.byters.game.agilityhealth.view.ui.FrameBufferDrawer;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class MonstersAnimationHelper {

    private final WeakReference<MonsterSpawnHelper> refMonsterSpawnHelper;
    private final WeakReference<ControllerResources> refControllerResources;
    private final WeakReference<CacheResources> refCacheResources;
    private final WeakReference<FrameBufferDrawer> refFrameBufferDrawer;
    private HashMap<Integer, MonsterAnimationTemplate> monsterAnimationHelper;


    public MonstersAnimationHelper(MonsterSpawnHelper monsterSpawnHelper,
                                   ControllerResources controllerResources,
                                   CacheResources cacheResources,
                                   FrameBufferDrawer frameBufferDrawer) {
        this.refMonsterSpawnHelper = new WeakReference<>(monsterSpawnHelper);
        this.refControllerResources = new WeakReference<>(controllerResources);
        this.refCacheResources = new WeakReference<>(cacheResources);
        this.refFrameBufferDrawer = new WeakReference<>(frameBufferDrawer);
    }

    public void init() {

        monsterAnimationHelper = new HashMap<>();
        for (int i = 0; i < refMonsterSpawnHelper.get().getMonsterTypesNum(); ++i)
            monsterAnimationHelper.put(refMonsterSpawnHelper.get().getMonsterType(i),
                    new MonsterAnimationTemplate(refControllerResources.get(),
                            refCacheResources.get(),
                            refFrameBufferDrawer.get(),
                            refCacheResources.get().TEXTURE_ATLAS_MONSTER[refMonsterSpawnHelper.get().getMonsterType(i)]));
    }

    public void draw(SpriteBatch spriteBatch, PresenterScreenGame presenterScreenGame) {
        for (int i = 0; i < presenterScreenGame.getMonstersNum(); ++i) {

            TextureAtlas.AtlasRegion texture = monsterAnimationHelper.get(presenterScreenGame.getMonsterType(i)).
                    getTextureCurrent(presenterScreenGame.isMonsterAttack(i),
                            presenterScreenGame.isMonsterStun(i),
                            presenterScreenGame.getMonsterLastTimeAttackMillis(i),
                            presenterScreenGame.getMonsterLastTimeStunMillis(i));


            monsterAnimationHelper.get(presenterScreenGame.getMonsterType(i)).draw(spriteBatch,
                    texture,
                    presenterScreenGame.getMonsterPosX(i),
                    presenterScreenGame.getMonsterPosY(i),
                    presenterScreenGame.isMonsterDirectionRight(i));
        }
    }

    public void load() {
        for (Integer key : monsterAnimationHelper.keySet())
            monsterAnimationHelper.get(key).load();
    }

    public void dispose() {
        for (Integer key : monsterAnimationHelper.keySet())
            monsterAnimationHelper.get(key).dispose();
    }

    public void addCorpse(int type, float posX, float posY) {
        monsterAnimationHelper.get(type).addCorpse(posX, posY);
    }
}
