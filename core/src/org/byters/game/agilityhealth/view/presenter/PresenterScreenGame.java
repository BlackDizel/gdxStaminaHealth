package org.byters.game.agilityhealth.view.presenter;

import org.byters.engine.controller.ControllerCamera;
import org.byters.engine.view.IScreen;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheGUI;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheHero;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMonsters;
import org.byters.game.agilityhealth.view.Navigator;

import java.lang.ref.WeakReference;

public class PresenterScreenGame {

    private WeakReference<IScreen> refScreenDeath;
    private WeakReference<Navigator> refNavigator;
    private WeakReference<CacheMonsters> refCacheMonsters;
    private WeakReference<CacheGUI> refCacheGUI;
    private WeakReference<ControllerCamera> refControllerCamera;
    private WeakReference<CacheMeta> refCacheMeta;
    private WeakReference<CacheHero> refCacheHero;

    public PresenterScreenGame(CacheMonsters cacheMonsters,
                               CacheMeta cacheGame,
                               CacheHero cacheHero,
                               CacheGUI cacheGUI,
                               ControllerCamera controllerCamera,
                               Navigator navigator,
                               IScreen screerDeath) {
        this.refCacheHero = new WeakReference<>(cacheHero);
        this.refCacheMeta = new WeakReference<>(cacheGame);
        this.refControllerCamera = new WeakReference<>(controllerCamera);
        this.refCacheGUI = new WeakReference<>(cacheGUI);
        this.refCacheMonsters = new WeakReference<>(cacheMonsters);
        this.refNavigator = new WeakReference<>(navigator);
        this.refScreenDeath = new WeakReference<>(screerDeath);
    }

    public float getHeroPosX() {
        return refCacheHero.get().getHeroPosX();
    }

    public float getHeroPosY() {
        return refCacheHero.get().getHeroPosY();
    }

    public void onMove(double angle) {
        refCacheHero.get().move(angle);
    }

    public void resetMove() {
        refCacheHero.get().resetMove();
    }

    public void onUpdate(float delta) {
        if (checkDeath()) return;

        refCacheHero.get().update(
                refCacheMonsters.get().getDamage(),
                delta,
                refCacheMeta.get().heroMinX,
                refCacheMeta.get().heroMinY,
                refCacheMeta.get().heroMaxX,
                refCacheMeta.get().heroMaxY);

        refControllerCamera.get().setPosition(refCacheHero.get().getHeroPosX(), refCacheHero.get().getHeroPosY(), 0);

        refCacheMonsters.get().onUpdate(delta, refCacheHero.get().getHeroPosX(), refCacheHero.get().getHeroPosY());

        updateGUI();
    }

    private boolean checkDeath() {
        if (!refCacheHero.get().isDie()) return false;
        refNavigator.get().navigateScreen(refScreenDeath.get());
        return true;
    }

    private void updateGUI() {
        float staminaPercent = refCacheHero.get().getStaminaPercent();
        refCacheGUI.get().setStamina(staminaPercent,
                staminaPercent >= refCacheMeta.get().staminaBarHigh
                        ? refCacheMeta.get().staminaBarColorMax
                        : staminaPercent >= refCacheMeta.get().staminaBarMedium
                        ? refCacheMeta.get().staminaBarColorHigh
                        : refCacheMeta.get().staminaBarColorMedium,
                refCacheHero.get().getHeroPosX() + refCacheMeta.get().staminaBarDeltaX,
                refCacheHero.get().getHeroPosY() + refCacheMeta.get().staminaBarDeltaY);
    }

    public void onMoveMode(boolean isRun) {
        refCacheHero.get().setRunPressed(isRun);
    }

    public int getBonefirePosX() {
        return refCacheMeta.get().borefirePosX;
    }

    public int getBonefirePosY() {
        return refCacheMeta.get().borefirePosY;
    }

    public boolean isDrawDust() {
        return refCacheHero.get().isRun();
    }

    public void onLoad() {
        refCacheMonsters.get().resetData();
        refCacheGUI.get().resetData();
    }

    public void onPressedAttack() {
        if (refCacheHero.get().tryAttack())
            refCacheMonsters.get().onAttack(
                    refCacheHero.get().getHeroPosX(),
                    refCacheHero.get().getHeroPosY(),
                    refCacheHero.get().getAttackDistanceSquared(),
                    refCacheHero.get().getDamageValue());
    }

    public boolean isMonstersExist() {
        return refCacheMonsters.get().isMonstersExist();
    }

    public int getMonstersNum() {
        return refCacheMonsters.get().getMonstersNum();
    }

    public float getMonsterPosX(int i) {
        return refCacheMonsters.get().getMonsterPosX(i);
    }

    public float getMonsterPosY(int i) {
        return refCacheMonsters.get().getMonsterPosY(i);
    }
}
