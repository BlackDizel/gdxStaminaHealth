package org.byters.game.agilityhealth.controller.data.memorycache;

import java.lang.ref.WeakReference;

public class CacheHero {

    private WeakReference<CacheMeta> refCacheMeta;

    private float deltaX, deltaY;
    private float posX, posY;
    private float speedPixelsPerSecond, speedRunPixelsPerSecond;
    private float staminaMax, stamina, staminaRestoreDelta, staminaRunDecreaseDelta, staminaMinRunValue;
    private boolean isRunPressed;
    private long lastTimeAttack, timeAttackDelay;
    private float staminaAttackDecreaseValue;
    private float attackDistanceSquared;
    private float damageValue;
    private boolean isDie;
    private long lastTimeDamaged;
    private long timeDamagedStaminaRestoreDelayMillis;

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

        lastTimeAttack = 0;
        timeAttackDelay = refCacheMeta.get().initialHeroTimeAttackDelay;
        staminaAttackDecreaseValue = refCacheMeta.get().initialHeroStaminaAttackDecreaseValue;
        damageValue = refCacheMeta.get().initialHeroDamageValue;
        attackDistanceSquared = refCacheMeta.get().initialHeroAttackDistanceSquared;
        isDie = false;
        lastTimeDamaged = 0;

        timeDamagedStaminaRestoreDelayMillis = refCacheMeta.get().initialHeroTimeDamagedStaminaRestoreDelayMillis;
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

    public void update(float damageRecieved, float deltaTimeSeconds, int minX, int minY, int maxX, int maxY) {
        checkPosition(deltaTimeSeconds, minX, minY, maxX, maxY);
        updateStamina(deltaTimeSeconds, damageRecieved);
    }

    private void checkPosition(float deltaTimeSeconds, int minX, int minY, int maxX, int maxY) {
        posX = Math.min(Math.max(minX, posX + deltaX * getSpeed() * deltaTimeSeconds), maxX);
        posY = Math.min(Math.max(minY, posY + deltaY * getSpeed() * deltaTimeSeconds), maxY);
    }

    private void updateStamina(float deltaTimeSeconds, float damageValue) {
        if (damageValue > 0) {
            stamina -= damageValue;
            lastTimeDamaged = System.currentTimeMillis();
            isDie = stamina <= 0;
        }

        if (isRun())
            stamina = Math.max(0, stamina - staminaRunDecreaseDelta * deltaTimeSeconds);

        if (isRunPressed || isAttacking() || isAttackedEarly()) return;
        stamina = Math.min(staminaMax, stamina + staminaRestoreDelta * deltaTimeSeconds);
    }

    private boolean isAttackedEarly() {
        return System.currentTimeMillis() - lastTimeDamaged < timeDamagedStaminaRestoreDelayMillis;
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

    public boolean isMove() {
        return !(deltaY == 0 && deltaX == 0);
    }

    public float getStaminaPercent() {
        return stamina / staminaMax;
    }

    public boolean tryAttack() {
        if (isAttacking()) return false;

        if (stamina < staminaAttackDecreaseValue) return false;

        stamina -= staminaAttackDecreaseValue;
        lastTimeAttack = System.currentTimeMillis();

        return true;
    }

    public boolean isAttacking() {
        return System.currentTimeMillis() - lastTimeAttack < timeAttackDelay;
    }

    public float getAttackDistanceSquared() {
        return attackDistanceSquared;
    }

    public float getDamageValue() {
        return damageValue;
    }

    public boolean isDie() {
        return isDie;
    }

    public boolean isHeroMoveRight() {
        return deltaX > 0 || deltaX == 0 && deltaY != 0;
    }
}
