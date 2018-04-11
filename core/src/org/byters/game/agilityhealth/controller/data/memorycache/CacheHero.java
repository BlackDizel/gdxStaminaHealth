package org.byters.game.agilityhealth.controller.data.memorycache;

import java.lang.ref.WeakReference;

public class CacheHero {

    private WeakReference<CacheMeta> refCacheMeta;

    private float deltaX, deltaY;
    private float posX, posY;
    private float speedPixelsPerSecond, speedRunPixelsPerSecond;
    private float staminaMax, stamina, staminaRestoreDelta, staminaRunDecreaseDelta, staminaMinRunValue;
    private boolean isRunPressed;

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

        stamina = refCacheMeta.get().initialHeroStamina;
        staminaMax = refCacheMeta.get().initialHeroStamina;
        staminaRestoreDelta = refCacheMeta.get().initialHeroStaminaRestoreDeltaPerSecond;
        staminaRunDecreaseDelta = refCacheMeta.get().initialHeroStaminaDecreaseDeltaPerSecond;
        staminaMinRunValue = refCacheMeta.get().initialHeroStaminaMinRunValue;
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
        checkPosition(deltaTimeSeconds, minX, minY, maxX, maxY);
        restoreStamina(deltaTimeSeconds);
    }

    private void checkPosition(float deltaTimeSeconds, int minX, int minY, int maxX, int maxY) {
        posX = Math.min(Math.max(minX, posX + deltaX * getSpeed() * deltaTimeSeconds), maxX);
        posY = Math.min(Math.max(minY, posY + deltaY * getSpeed() * deltaTimeSeconds), maxY);

        if (isRun())
            stamina = Math.max(0, stamina - staminaRunDecreaseDelta * deltaTimeSeconds);
    }

    private void restoreStamina(float deltaTimeSeconds) {
        if (isRunPressed) return;
        stamina = Math.min(staminaMax, stamina + staminaRestoreDelta * deltaTimeSeconds);
    }

    private float getSpeed() {
        return isRun() ? speedRunPixelsPerSecond : speedPixelsPerSecond;
    }

    public void resetMove() {
        deltaX = 0;
        deltaY = 0;
    }

    public boolean isRun() {
        return isRunPressed && isMove() && stamina > staminaMinRunValue;
    }

    public void setRunPressed(boolean isRun) {
        this.isRunPressed = isRun;
    }

    private boolean isMove() {
        return !(deltaY == 0 && deltaX == 0);
    }

    public float getStaminaPercent() {
        return stamina / staminaMax;
    }
}
