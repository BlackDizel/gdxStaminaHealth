package org.byters.game.agilityhealth.view;

import org.byters.game.agilityhealth.controller.data.memorycache.CacheGUI;

import java.lang.ref.WeakReference;

public class PresenterScreenGameOver {
    private WeakReference<CacheGUI> refCacheGUI;

    public PresenterScreenGameOver(CacheGUI cacheGUI) {
        this.refCacheGUI = new WeakReference<>(cacheGUI);
    }

    public void onLoad() {
        //todo reset camera
        refCacheGUI.get().resetData();
    }
}
