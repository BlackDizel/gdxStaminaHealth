package org.byters.game.agilityhealth.model;

import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;

import java.util.Random;

public class MonsterType {
    private String title;
    private int id;
    private float speedPxSec;
    private float stamina;
    private float damage;
    private long directionCalcDelay;
    private long attackDelay;
    private long attackPrepareMillis;
    private long attackDistanceSquared;
    private long attackDurationMillis;

    public boolean isType(int id) {
        return this.id == id;
    }

    public MonsterData newMonster(CacheMeta cacheMeta, float spawnX, float spawnY, Random random) {
        return new MonsterData(
                id,
                spawnX,
                spawnY,
                stamina,
                speedPxSec,
                attackDistanceSquared,
                damage,
                cacheMeta.initialMonsterTimeStunMillis,
                directionCalcDelay,
                attackDelay,
                attackPrepareMillis,
                attackDurationMillis,
                random);

    }

    public int getId() {
        return id;
    }
}
