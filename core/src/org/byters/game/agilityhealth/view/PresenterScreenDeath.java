package org.byters.game.agilityhealth.view;

import org.byters.game.agilityhealth.controller.data.memorycache.CacheGUI;

import java.lang.ref.WeakReference;

public class PresenterScreenDeath {
    private WeakReference<CacheGUI> refCacheGUI;

    public PresenterScreenDeath(CacheGUI cacheGUI) {
        this.refCacheGUI = new WeakReference<>(cacheGUI);
    }

    public void onLoad() {
        //todo reset camera
        refCacheGUI.get().resetData();
    }
}
