package org.byters.game.agilityhealth.controller.data.memorycache;

import org.byters.game.agilityhealth.controller.data.memorycache.util.MonsterSpawnHelper;
import org.byters.game.agilityhealth.model.MonsterData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class CacheMonsters {

    private ArrayList<MonsterData> data;

    private boolean underAttack;
    private float attackPosX, attackPosY;
    private float damageValue;
    private float attackDistanceSquared;
    private float damage;

    private WeakReference<MonsterSpawnHelper> refMonsterSpawnHelper;

    public CacheMonsters(MonsterSpawnHelper monsterSpawnHelper) {
        refMonsterSpawnHelper = new WeakReference<>(monsterSpawnHelper);
        resetData();
    }

    public void resetData() {
        data = null;
        damage = 0;

        refMonsterSpawnHelper.get().resetData();

        resetUnderAttackState();
    }

    public void onUpdate(float delta, float heroPosX, float heroPosY) {
        checkSpawn();
        updateMonsters(delta, heroPosX, heroPosY);
    }

    private void updateMonsters(float delta, float heroPosX, float heroPosY) {
        damage = 0;

        if (data == null || data.size() == 0) return;
        Iterator<MonsterData> itr = data.iterator();

        while (itr.hasNext()) {
            MonsterData item = itr.next();

            if (underAttack)
                item.checkAttacked(attackPosX, attackPosY, attackDistanceSquared, damageValue);

            if (item.isDie()) {
                itr.remove();
                continue;
            }

            item.update(delta, heroPosX, heroPosY);
            damage += item.getAttack(heroPosX, heroPosY);
        }

        resetUnderAttackState();
    }

    private void resetUnderAttackState() {
        underAttack = false;
        attackPosX = 0;
        attackPosY = 0;
        attackDistanceSquared = 0;
        damageValue = 0;
    }

    private void checkSpawn() {
        if (data == null) data = new ArrayList<>();

        refMonsterSpawnHelper.get().update(data);
    }

    public void onAttack(float heroPosX,
                         float heroPosY,
                         float attackDistanceSquared,
                         float damageValue) {
        this.underAttack = true;
        this.attackPosX = heroPosX;
        this.attackPosY = heroPosY;
        this.damageValue = damageValue;
        this.attackDistanceSquared = attackDistanceSquared;
    }

    public boolean isMonstersExist() {
        return data != null && data.size() > 0;
    }

    public int getMonstersNum() {
        return data == null ? 0 : data.size();
    }

    public float getMonsterPosX(int i) {
        return data == null || data.size() <= i ? 0 : data.get(i).getPosX();
    }

    public float getMonsterPosY(int i) {
        return data == null || data.size() <= i ? 0 : data.get(i).getPosY();
    }

    public float getDamage() {
        return damage;
    }

    public boolean isAllMonstersDefeated() {
        return refMonsterSpawnHelper.get().isAllWavesDefeated(data);
    }

    public boolean isMonsterDirectionRight(int position) {
        return data == null || data.size() <= position ? false : data.get(position).isDirectionRight();
    }

    public boolean isMonsterAttack(int position) {
        return data == null || data.size() <= position ? false : data.get(position).isAttack();
    }

    public boolean isMonsterStun(int position) {
        return data == null || data.size() <= position ? false : data.get(position).isStun();
    }
}
