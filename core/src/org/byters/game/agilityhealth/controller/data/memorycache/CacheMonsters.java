package org.byters.game.agilityhealth.controller.data.memorycache;

import org.byters.game.agilityhealth.controller.data.memorycache.util.MonsterSpawnHelper;
import org.byters.game.agilityhealth.model.MonsterData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class CacheMonsters {

    private WeakReference<Random> refRandom;
    private WeakReference<CacheMeta> refCacheMeta;
    private ArrayList<MonsterData> data;

    private long lastTimeSpawn, timeSpawnDelay;
    private boolean underAttack;
    private float attackPosX, attackPosY;
    private float damageValue;
    private float attackDistanceSquared;
    private int maxMonstersNum;
    private float damage;

    public CacheMonsters(CacheMeta cacheMeta, Random random) {
        refCacheMeta = new WeakReference<>(cacheMeta);
        refRandom = new WeakReference<>(random);
        resetData();
    }

    public void resetData() {
        data = null;
        damage = 0;

        lastTimeSpawn = 0;
        timeSpawnDelay = refCacheMeta.get().timeMonsterSpawnDelay;
        maxMonstersNum = refCacheMeta.get().initialMonstersmaxNum;

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
        //todo some very complex logic here to calc monsters spawn.


        //todo this stupid logic just temp :D
        if (System.currentTimeMillis() - lastTimeSpawn < timeSpawnDelay) return;
        lastTimeSpawn = System.currentTimeMillis();
        addMonster();
    }

    private void addMonster() {
        if (data == null) data = new ArrayList<>();

        if (data.size() >= maxMonstersNum) return;
        data.add(MonsterSpawnHelper.getMonster(refCacheMeta.get(), refRandom.get()));
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
}
