package org.byters.game.agilityhealth.model;

import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;

import java.util.ArrayList;
import java.util.Random;

public class MonstersWavesData {
    private ArrayList<MonsterType> types;
    private ArrayList<MonstersWave> waves; //todo must be sorted by spawn time

    public MonstersWave getWave(int index) {
        return waves == null || index < 0 || index >= waves.size() ? null : waves.get(index);
    }

    public ArrayList<MonsterData> getMonsters(CacheMeta cacheMeta, int currentWaveIndex, int currentMonsterSpawnIndex, int lastMonsterSpawnIndex, Random random) {
        MonstersWave currentWave = getWave(currentWaveIndex);
        if (currentWave == null) return null;
        return currentWave.getMonsters(cacheMeta, currentMonsterSpawnIndex, lastMonsterSpawnIndex, types, random);
    }

    public int getWavesNum() {
        return waves == null ? 0 : waves.size();
    }

    public int getTypesNum() {
        return types == null ? 0 : types.size();
    }

    public int getMonsterType(int position) {
        return types == null ? 0 : types.get(position).getId();
    }
}
