package org.byters.game.agilityhealth.view;

import org.byters.engine.view.IScreen;

import java.lang.ref.WeakReference;

public class Navigator {
    private WeakReference<org.byters.engine.view.Navigator> refEngineNavigator;

    public Navigator(org.byters.engine.view.Navigator navigator) {
        this.refEngineNavigator = new WeakReference<>(navigator);

    }

    public void navigateScreen(IScreen screen) {
        refEngineNavigator.get().navigateScreen(screen);
    }

}
