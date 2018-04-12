package org.byters.game.agilityhealth.model;

import com.badlogic.gdx.math.Vector2;
import org.byters.game.agilityhealth.MathHelper;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;

public class MonsterData {

    private final float speed;
    private float x, y;
    private float stamina;
    private Vector2 direction;
    private long lastTimeCalcDirectionMillis, timeCalcDirectionDelay;
    private long lastTimeStunMillis, timeStunMillis;
    private long lastTimeAttackMillis, timeAttackDelayMillis;
    private float monsterAttackDistanceSquared;
    private float damageValue;

    public MonsterData(CacheMeta cacheMeta, float x, float y) {
        stamina = cacheMeta.initialMonsterStamina;
        speed = cacheMeta.initialMonsterSpeedPixelsPerSecond;
        monsterAttackDistanceSquared = cacheMeta.initialMonsterAttackDistanceSquared;
        damageValue = cacheMeta.initialMonsterDamageValue;
        this.x = x;
        this.y = y;
        direction = new Vector2();

        timeStunMillis = cacheMeta.initialMonsterTimeStunMillis;
        timeCalcDirectionDelay = cacheMeta.initialMonsterTimeCalcDirectionDelayMillis;
        timeAttackDelayMillis = cacheMeta.initialMonsterTimeAttackDelayMillis;

        lastTimeAttackMillis = 0;
        lastTimeStunMillis = 0;
        lastTimeCalcDirectionMillis = 0;
    }

    public void update(float delta, float heroPosX, float heroPosY) {

        if (isTimeToCalcDirection()) {
            direction.x = heroPosX - x;
            direction.y = heroPosY - y;
            direction.nor();
        }

        checkMove(delta);

        //todo try move to hero and attack
    }

    private void checkMove(float delta) {
        if (isStun()) return;
        x += direction.x * speed * delta;
        y += direction.y * speed * delta;
    }

    private boolean isStun() {
        return System.currentTimeMillis() - lastTimeStunMillis < timeStunMillis;
    }

    private boolean isTimeToCalcDirection() {
        if (System.currentTimeMillis() - lastTimeCalcDirectionMillis < timeCalcDirectionDelay) return false;
        lastTimeCalcDirectionMillis = System.currentTimeMillis();
        return true;
    }

    public boolean isDie() {
        return stamina < 0;
    }

    public void checkAttacked(float attackPosX,
                              float attackPosY,
                              float attackDistanceSquared,
                              float damageValue) {

        //todo add attack direction check

        if (MathHelper.distanceSquared(x, y, attackPosX, attackPosY) > attackDistanceSquared) return;
        stamina -= damageValue;

        stun();
    }

    public float getAttack(float heroPosX, float heroPosY) {
        if (isStun()) return 0;
        if (System.currentTimeMillis() - lastTimeAttackMillis < timeAttackDelayMillis) return 0;

        if (MathHelper.distanceSquared(x, y, heroPosX, heroPosY) > monsterAttackDistanceSquared) return 0;


        System.out.println("kick");

        lastTimeAttackMillis = System.currentTimeMillis();

        return damageValue;
    }

    private void stun() {
        lastTimeStunMillis = System.currentTimeMillis();
    }

    public float getPosX() {
        return x;
    }


    public float getPosY() {
        return y;
    }
}
