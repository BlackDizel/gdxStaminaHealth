package org.byters.game.agilityhealth.controller.data.memorycache;

import com.badlogic.gdx.graphics.Color;

public class CacheMeta {
    public int screenWidth = 640;
    public int screenHeight = 360;

    public float initialHeroPosX = 300;
    public float initialHeroPosY = 200;
    public float initialHeroSpeedPixelsPerSecond = 100;
    public float initialHeroSpeedRunPixelsPerSecond = 200;
    public float initialHeroStamina = 100;
    public float initialHeroStaminaRestoreDeltaPerSecond = 40;
    public float initialHeroStaminaDecreaseDeltaPerSecond = 60;
    public float initialHeroStaminaMinRunValue = 10;
    public long initialHeroTimeAttackDelay = 500;
    public float initialHeroDamageValue = 60;
    public float initialHeroAttackDistanceSquared = 2500;
    public long initialHeroTimeDamagedStaminaRestoreDelayMillis = 1200;

    public int heroMaxX = 600;
    public int heroMaxY = 320;
    public int heroMinX = 20;
    public int heroMinY = 20;

    public int borefirePosX = 360;
    public int borefirePosY = 210;

    public int uiStaminaBarWidth = 64;
    public int uiStaminaBarHeight = 8;
    public int uiStaminaBarBorderWidth = 2;
    public int uiStaminaBarBorderDistance = 1;

    public float staminaBarHigh = 0.9f;
    public float staminaBarMedium = 0.3f;
    public Color staminaBarColorMax = Color.CLEAR;
    public Color staminaBarColorHigh = Color.YELLOW;
    public Color staminaBarColorMedium = Color.RED;

    public float staminaBarDeltaX = -36;
    public float staminaBarDeltaY = -36;
    public float initialHeroStaminaAttackDecreaseValue = 15;

    public long initialMonsterTimeStunMillis = 800;
}
