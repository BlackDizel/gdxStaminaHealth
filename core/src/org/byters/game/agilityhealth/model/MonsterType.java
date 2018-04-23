package org.byters.game.agilityhealth.model;

import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;

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

    public MonsterData newMonster(CacheMeta cacheMeta, float spawnX, float spawnY) {
        return new MonsterData(
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
                attackDurationMillis);

    }
}
