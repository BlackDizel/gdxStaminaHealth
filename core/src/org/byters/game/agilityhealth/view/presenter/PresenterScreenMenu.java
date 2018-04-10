package org.byters.game.agilityhealth.view.presenter;

import org.byters.engine.view.IScreen;
import org.byters.game.agilityhealth.view.Navigator;

import java.lang.ref.WeakReference;

public class PresenterScreenMenu implements IPresenterScreenMenu {

    private WeakReference<IScreen> refScreenGame;
    private WeakReference<Navigator> refNavigator;

    public PresenterScreenMenu(Navigator navigator, IScreen screenGame) {
        this.refNavigator = new WeakReference<>(navigator);
        this.refScreenGame = new WeakReference<>(screenGame);
    }

    @Override
    public void onClick() {
        refNavigator.get().navigateScreen(refScreenGame.get());
    }
}
