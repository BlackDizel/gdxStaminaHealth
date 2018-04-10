package org.byters.game.agilityhealth.view.presenter;

import org.byters.engine.controller.ControllerCamera;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheHero;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;

import java.lang.ref.WeakReference;

public class PresenterScreenGame {

    private WeakReference<ControllerCamera> refControllerCamera;
    private WeakReference<CacheMeta> refCacheMeta;
    private WeakReference<CacheHero> refCacheHero;

    public PresenterScreenGame(CacheMeta cacheGame,
                               CacheHero cacheHero,
                               ControllerCamera controllerCamera) {
        this.refCacheHero = new WeakReference<>(cacheHero);
        this.refCacheMeta = new WeakReference<>(cacheGame);
        this.refControllerCamera = new WeakReference<>(controllerCamera);
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


        refControllerCamera.get().setPosition(refCacheHero.get().getHeroPosX(),refCacheHero.get().getHeroPosY(),0);

    }

    public void onMoveMode(boolean isRun) {
        refCacheHero.get().setRun(isRun);
    }
}
