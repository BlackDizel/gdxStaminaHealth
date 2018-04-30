package org.byters.game.agilityhealth.model;

import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;

import java.util.ArrayList;
import java.util.Random;

public class MonstersWave {
    private ArrayList<MonsterSpawnInfo> items;

    public int getMonstersNum() {
        return items == null ? 0 : items.size();
    }

    /**
     * @param spawnTimeStart current wave spawn start time in millis
     * @return last index of spawned monster to current time
     */
    public int getLastMonsterSpawnIndex(long spawnTimeStart) {
        if (items == null) return 0;

        long currentTime = System.currentTimeMillis();
        for (int i = items.size() - 1; i > 0; --i) {
            if (currentTime < spawnTimeStart + items.get(i).getSpawnMillis())
                continue;
            return i;
        }

        return 0;
    }

    public ArrayList<MonsterData> getMonsters(CacheMeta cacheMeta, int indexStart, int indexEnd, ArrayList<MonsterType> types, Random random) {
        ArrayList<MonsterData> result = null;

        for (int i = indexStart; i <= indexEnd; ++i) {
            MonsterSpawnInfo item = getItem(i);
            if (item == null) continue;

            MonsterType type = getMonsterType(types, item.getId());
            if (type == null) continue;

            if (result == null) result = new ArrayList<>();
            result.add(type.newMonster(cacheMeta, item.getSpawnX(), item.getSpawnY(), random));
        }

        return result;
    }

    private MonsterType getMonsterType(ArrayList<MonsterType> types, int id) {
        if (types == null) return null;
        for (MonsterType item : types) {
            if (!item.isType(id)) continue;
            return item;
        }
        return null;
    }

    private MonsterSpawnInfo getItem(int position) {
        return items == null || position < 0 || position >= items.size() ? null : items.get(position);
    }

}

