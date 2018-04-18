package org.byters.game.agilityhealth.view;

import org.byters.engine.controller.ControllerCamera;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheGUI;

import java.lang.ref.WeakReference;

public class PresenterScreenGameOver {
    private WeakReference<ControllerCamera> refControllerCamera;
    private WeakReference<CacheGUI> refCacheGUI;

    public PresenterScreenGameOver(ControllerCamera controllerCamera, CacheGUI cacheGUI) {
        this.refCacheGUI = new WeakReference<>(cacheGUI);
        this.refControllerCamera = new WeakReference<>(controllerCamera);
    }

    public void onLoad() {
        refCacheGUI.get().resetData();
        refControllerCamera.get().resetCamera();
    }
}
