package org.byters.game.agilityhealth.controller;

import org.byters.engine.Engine;
import org.byters.engine.view.IScreen;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheHero;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;
import org.byters.game.agilityhealth.view.InputHelper;
import org.byters.game.agilityhealth.view.InputSettings;
import org.byters.game.agilityhealth.view.Navigator;
import org.byters.game.agilityhealth.view.TextureHelper;
import org.byters.game.agilityhealth.view.presenter.IPresenterScreenMenu;
import org.byters.game.agilityhealth.view.presenter.PresenterScreenGame;
import org.byters.game.agilityhealth.view.presenter.PresenterScreenMenu;
import org.byters.game.agilityhealth.view.ui.ScreenGame;
import org.byters.game.agilityhealth.view.ui.ScreenMenu;

public class Injector {

    private Engine engine;
    private IScreen screenMenu;
    private IScreen screenGame;
    private IPresenterScreenMenu presenterScreenMenu;
    private PresenterScreenGame presenterScreenGame;
    private Navigator navigator;
    private CacheHero cacheHero;
    private InputHelper inputHelper;
    private TextureHelper textureHelper;
    private CacheMeta cacheMeta;
    private InputSettings inputSettings;

    private IScreen getScreenMenu() {
        if (screenMenu == null)
            screenMenu = new ScreenMenu(getPresenterScreenMenu(), engine.getInjector().getControllerResources().getSpriteBatch(), getTextureHelper());
        return screenMenu;
    }

    private IScreen getScreenGame() {
        if (screenGame == null)
            screenGame = new ScreenGame(getPresenterScreenGame(),
                    getInputSetting(),
                    getInputHelper(),
                    engine.getInjector().getControllerResources().getSpriteBatch(),
                    getTextureHelper());
        return screenGame;
    }

    private InputSettings getInputSetting() {
        if (inputSettings == null) inputSettings = new InputSettings();
        return inputSettings;
    }

    private TextureHelper getTextureHelper() {
        if (textureHelper == null) textureHelper = new TextureHelper();
        return textureHelper;
    }

    private InputHelper getInputHelper() {
        if (inputHelper == null) inputHelper = new InputHelper();
        return inputHelper;
    }

    private CacheHero getCacheHero() {
        if (cacheHero == null) cacheHero = new CacheHero(getCacheMeta());
        return cacheHero;
    }

    private PresenterScreenGame getPresenterScreenGame() {
        if (presenterScreenGame == null) presenterScreenGame = new PresenterScreenGame(getCacheMeta(), getCacheHero());
        return presenterScreenGame;
    }

    private IPresenterScreenMenu getPresenterScreenMenu() {
        if (presenterScreenMenu == null) presenterScreenMenu = new PresenterScreenMenu(getNavigator(), getScreenGame());
        return presenterScreenMenu;
    }

    private Navigator getNavigator() {
        if (navigator == null) navigator = new Navigator(engine.getInjector().getNavigator());
        return navigator;
    }

    public void init() {
        getEngine().load();
        getNavigator().navigateScreen(getScreenMenu());
    }

    public Engine getEngine() {
        if (engine == null) engine = new Engine();
        return engine;
    }

    public CacheMeta getCacheMeta() {
        if (cacheMeta == null) cacheMeta = new CacheMeta();
        return cacheMeta;
    }
}
