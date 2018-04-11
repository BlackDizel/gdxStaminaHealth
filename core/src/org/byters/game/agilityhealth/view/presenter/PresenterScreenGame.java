package org.byters.game.agilityhealth.view.presenter;

import org.byters.engine.controller.ControllerCamera;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheGUI;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheHero;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;

import java.lang.ref.WeakReference;

public class PresenterScreenGame {

    private WeakReference<CacheGUI> refCacheGUI;
    private WeakReference<ControllerCamera> refControllerCamera;
    private WeakReference<CacheMeta> refCacheMeta;
    private WeakReference<CacheHero> refCacheHero;

    public PresenterScreenGame(CacheMeta cacheGame,
                               CacheHero cacheHero,
                               CacheGUI cacheGUI,
                               ControllerCamera controllerCamera) {
        this.refCacheHero = new WeakReference<>(cacheHero);
        this.refCacheMeta = new WeakReference<>(cacheGame);
        this.refControllerCamera = new WeakReference<>(controllerCamera);
        this.refCacheGUI = new WeakReference<>(cacheGUI);
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
        refCacheHero.get().update(
                delta,
                refCacheMeta.get().heroMinX,
                refCacheMeta.get().heroMinY,
                refCacheMeta.get().heroMaxX,
                refCacheMeta.get().heroMaxY);

        refControllerCamera.get().setPosition(refCacheHero.get().getHeroPosX(), refCacheHero.get().getHeroPosY(), 0);

        updateGUI();
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
        refCacheGUI.get().resetData();
    }
}
