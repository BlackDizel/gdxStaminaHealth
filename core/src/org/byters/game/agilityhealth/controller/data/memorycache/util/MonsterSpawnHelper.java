package org.byters.game.agilityhealth.controller.data.memorycache.util;

import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;
import org.byters.game.agilityhealth.model.MonsterData;

import java.util.Random;

public class MonsterSpawnHelper {

    public static MonsterData getMonster(CacheMeta cacheMeta, Random random) {

        float x, y;
        int direction = random.nextInt(4);

        //0-west, 1-north, 2-east, 3-south

        x = direction == 1 || direction == 3
                ? random.nextInt(cacheMeta.initialMonsterBoundX)
                : direction == 0
                ? 0
                : cacheMeta.initialMonsterBoundX;


        y = direction == 0 || direction == 2
                ? random.nextInt(cacheMeta.initialMonsterBoundY)
                : direction == 3
                ? 0
                : cacheMeta.initialMonsterBoundY;

        return new MonsterData(cacheMeta, x, y);
    }
}
