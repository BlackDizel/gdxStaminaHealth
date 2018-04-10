package org.byters.game.agilityhealth.controller.data.memorycache;

import java.lang.ref.WeakReference;

public class CacheHero {

    private WeakReference<CacheMeta> refCacheMeta;

    private float deltaX, deltaY;
    private float posX, posY;
    private float speedPixelsPerSecond, speedRunPixelsPerSecond;
    private boolean isRun;

    public CacheHero(CacheMeta cacheMeta) {
        this.refCacheMeta = new WeakReference<>(cacheMeta);
        resetCache();
    }

    public void resetCache() {
        resetMove();
        posX = refCacheMeta.get().initialHeroPosX;
        posY = refCacheMeta.get().initialHeroPosY;
        speedPixelsPerSecond = refCacheMeta.get().initialHeroSpeedPixelsPerSecond;
        speedRunPixelsPerSecond = refCacheMeta.get().initialHeroSpeedRunPixelsPerSecond;
    }

    public float getHeroPosX() {
        return posX;
    }

    public float getHeroPosY() {
        return posY;
    }

    public void move(double angle) {

        deltaX += Math.cos(angle);
        deltaY += Math.sin(angle);
    }

    public void update(float deltaTimeSeconds, int minX, int minY, int maxX, int maxY) {
        posX = Math.min(Math.max(minX, posX + deltaX * getSpped() * deltaTimeSeconds), maxX);
        posY = Math.min(Math.max(minY, posY + deltaY * getSpped() * deltaTimeSeconds), maxY);
    }

    private float getSpped() {
        return isRun ? speedRunPixelsPerSecond : speedPixelsPerSecond;
    }

    public void resetMove() {
        deltaX = 0;
        deltaY = 0;
    }

    public void setRun(boolean isRun) {
        this.isRun = isRun;
    }
}
