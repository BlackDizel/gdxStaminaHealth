package org.byters.game.agilityhealth.model;

import com.badlogic.gdx.math.Vector2;
import org.byters.game.agilityhealth.MathHelper;

import java.util.Random;

public class MonsterData {

    private float speed;
    private long timeAttackPrepareMillis;
    private long attackDurationMillis;
    private int id;
    private float x, y;
    private float stamina;

    private Vector2 direction;
    private long lastTimeCalcDirectionMillis, timeCalcDirectionDelay;
    private long lastTimeStunMillis, timeStunMillis;
    private long lastTimeAttackMillis, timeAttackDelayMillis;
    private float monsterAttackDistanceSquared;
    private float damageValue;

    public MonsterData(int id,
                       float x,
                       float y,
                       float stamina,
                       float speed,
                       float attackDistanceSquared,
                       float damageValue,
                       long stunMillis,
                       long directionCalc,
                       long attackDelayMillis,
                       long attackPrepareMillis,
                       long attackDurationMillis,
                       Random random) {
        this.id = id;
        this.stamina = stamina;
        this.speed = speed;
        monsterAttackDistanceSquared = attackDistanceSquared;
        this.damageValue = damageValue;
        this.x = x;
        this.y = y;
        direction = new Vector2();
        this.attackDurationMillis = attackDurationMillis;

        timeStunMillis = stunMillis;
        timeCalcDirectionDelay = directionCalc;
        timeAttackDelayMillis = attackDelayMillis;
        this.timeAttackPrepareMillis = attackPrepareMillis;

        lastTimeAttackMillis = 0;
        lastTimeStunMillis = 0;
        lastTimeCalcDirectionMillis = System.currentTimeMillis() - random.nextInt((int) timeAttackDelayMillis);
    }

    public void update(float delta, float heroPosX, float heroPosY) {

        if (isTimeToCalcDirection() && !isAttack() && !isStun()) {
            direction.x = heroPosX - x;
            direction.y = heroPosY - y;
            direction.nor();
        }

        checkMove(delta);
    }

    private void checkMove(float delta) {
        if (isStun() || isAttack()) return;
        x += direction.x * speed * delta;
        y += direction.y * speed * delta;
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
        if (isAttackDelay()) return 0;

        if (MathHelper.distanceSquared(x, y, heroPosX, heroPosY) > monsterAttackDistanceSquared) return 0;

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

    public boolean isDirectionRight() {
        return direction.x > 0;
    }

    public boolean isAttack() {
        return System.currentTimeMillis() - lastTimeAttackMillis < attackDurationMillis;
    }

    private boolean isAttackDelay() {
        return System.currentTimeMillis() - lastTimeAttackMillis < timeAttackDelayMillis;
    }

    public boolean isStun() {
        return System.currentTimeMillis() - lastTimeStunMillis < timeStunMillis;
    }

    public int getType() {
        return id;
    }

    public long getLastTimeMillisAttack() {
        return lastTimeAttackMillis;
    }

    public long getLastTimeMillisStun() {
        return lastTimeStunMillis;
    }
}
