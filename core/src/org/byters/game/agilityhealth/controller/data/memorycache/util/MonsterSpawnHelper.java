package org.byters.game.agilityhealth.controller.data.memorycache.util;

import org.byters.engine.controller.ControllerJsonBase;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheResources;
import org.byters.game.agilityhealth.model.MonsterData;
import org.byters.game.agilityhealth.model.MonstersWave;
import org.byters.game.agilityhealth.model.MonstersWavesData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MonsterSpawnHelper {

    private WeakReference<CacheMeta> refCacheMeta;
    private WeakReference<ControllerJsonBase> refControllerJsonBase;
    private WeakReference<CacheResources> refCacheResources;
    private MonstersWavesData data;
    private long spawnTimeStart;
    private int currentWaveIndex;
    private int currentMonsterSpawnIndex;

    public MonsterSpawnHelper(CacheResources cacheResources,
                              CacheMeta cacheMeta,
                              ControllerJsonBase controllerJsonBase) {
        this.refCacheResources = new WeakReference<>(cacheResources);
        this.refControllerJsonBase = new WeakReference<>(controllerJsonBase);
        this.refCacheMeta = new WeakReference<>(cacheMeta);
        resetData();
    }

    public void load() {
        data = refControllerJsonBase.get().readFile(MonstersWavesData.class, refCacheResources.get().JSON_MONSTERS_WAVES);
    }

    public void resetData() {
        currentWaveIndex = 0;
        resetWaveData();
    }

    public boolean isAllWavesDefeated(ArrayList<MonsterData> monsters) {
        if (monsters != null && monsters.size() > 0) return false;
        if (data == null) return true;

        return currentWaveIndex >= data.getWavesNum();
    }

    public void update(ArrayList<MonsterData> monsters) {
        if (monsters == null) return;

        MonstersWave currentWaveData = data.getWave(currentWaveIndex);
        if (currentWaveData == null) return;

        if (monsters.size() == 0 && monsterWaveLastIsSpawned(currentWaveData)) {
            ++currentWaveIndex;
            resetWaveData();
        }

        int lastMonsterSpawnIndex = currentWaveData.getLastMonsterSpawnIndex(spawnTimeStart);
        if (lastMonsterSpawnIndex <= currentMonsterSpawnIndex) return;

        ArrayList<MonsterData> monstersAdd = data.getMonsters(refCacheMeta.get(), currentWaveIndex, currentMonsterSpawnIndex, lastMonsterSpawnIndex);
        currentMonsterSpawnIndex = lastMonsterSpawnIndex + 1;
        if (monstersAdd == null) return;

        monsters.addAll(monstersAdd);
    }

    private boolean monsterWaveLastIsSpawned(MonstersWave wave) {
        return currentMonsterSpawnIndex == wave.getMonstersNum();
    }

    private void resetWaveData() {
        spawnTimeStart = System.currentTimeMillis();
        currentMonsterSpawnIndex = -1;
    }
}
