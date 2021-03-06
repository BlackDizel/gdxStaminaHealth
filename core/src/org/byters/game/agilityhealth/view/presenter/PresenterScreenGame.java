package org.byters.game.agilityhealth.view.presenter;

import org.byters.engine.controller.ControllerCamera;
import org.byters.engine.view.IScreen;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheGUI;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheHero;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMonsters;
import org.byters.game.agilityhealth.view.Navigator;
import org.byters.game.agilityhealth.view.ui.util.MonstersAnimationHelper;

import java.lang.ref.WeakReference;

public class PresenterScreenGame {

    private WeakReference<IScreen> refScreenWin;
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
                               IScreen screerDeath,
                               IScreen screenWin) {
        this.refCacheHero = new WeakReference<>(cacheHero);
        this.refCacheMeta = new WeakReference<>(cacheGame);
        this.refControllerCamera = new WeakReference<>(controllerCamera);
        this.refCacheGUI = new WeakReference<>(cacheGUI);
        this.refCacheMonsters = new WeakReference<>(cacheMonsters);
        this.refNavigator = new WeakReference<>(navigator);
        this.refScreenDeath = new WeakReference<>(screerDeath);
        this.refScreenWin = new WeakReference<>(screenWin);
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

    public void onUpdate(float delta, MonstersAnimationHelper monstersAnimationHelper) {
        if (checkDeath()) return;

        refCacheHero.get().update(
                refCacheMonsters.get().getDamage(),
                delta,
                refCacheMeta.get().heroMinX,
                refCacheMeta.get().heroMinY,
                refCacheMeta.get().heroMaxX,
                refCacheMeta.get().heroMaxY);

        refControllerCamera.get().setPosition(refCacheHero.get().getHeroPosX(), refCacheHero.get().getHeroPosY(), 0);

        refCacheMonsters.get().onUpdate(delta, refCacheHero.get().getHeroPosX(), refCacheHero.get().getHeroPosY(), monstersAnimationHelper);

        updateGUI();

        if (checkWin()) return;
    }

    private boolean checkWin() {
        if (!refCacheMonsters.get().isAllMonstersDefeated()) return false;
        refNavigator.get().navigateScreen(refScreenWin.get());
        return true;
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

    public boolean isHeroMoveRight() {
        return refCacheHero.get().isHeroMoveRight();
    }

    public boolean isHeroAttacking() {
        return refCacheHero.get().isAttacking();
    }

    public boolean isHeroMoving() {
        return refCacheHero.get().isMove();
    }

    public boolean isMonsterDirectionRight(int position) {
        return refCacheMonsters.get().isMonsterDirectionRight(position);
    }

    public boolean isMonsterAttack(int position) {
        return refCacheMonsters.get().isMonsterAttack(position);
    }

    public boolean isMonsterStun(int position) {
        return refCacheMonsters.get().isMonsterStun(position);
    }

    public int getMonsterType(int position) {
        return refCacheMonsters.get().getMonsterType(position);
    }

    public long getMonsterLastTimeAttackMillis(int position) {
        return refCacheMonsters.get().getMonsterLastTimeMillisAttack(position);
    }

    public long getMonsterLastTimeStunMillis(int position) {
        return refCacheMonsters.get().getMonsterLastTimeMillisStun(position);
    }
}
