package org.byters.game.agilityhealth.model;

import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;

import java.util.ArrayList;

public class MonstersWavesData {
    private ArrayList<MonsterType> types;
    private ArrayList<MonstersWave> waves; //todo must be sorted by spawn time

    public MonstersWave getWave(int index) {
        return waves == null || index < 0 || index >= waves.size() ? null : waves.get(index);
    }

    public ArrayList<MonsterData> getMonsters(CacheMeta cacheMeta, int currentWaveIndex, int currentMonsterSpawnIndex, int lastMonsterSpawnIndex) {
        MonstersWave currentWave = getWave(currentWaveIndex);
        if (currentWave == null) return null;
        return currentWave.getMonsters(cacheMeta,currentMonsterSpawnIndex, lastMonsterSpawnIndex, types);
    }
}
