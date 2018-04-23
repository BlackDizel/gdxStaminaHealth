package org.byters.game.agilityhealth.controller;

import org.byters.engine.Engine;
import org.byters.engine.view.IScreen;
import org.byters.game.agilityhealth.controller.data.memorycache.*;
import org.byters.game.agilityhealth.controller.data.memorycache.util.MonsterSpawnHelper;
import org.byters.game.agilityhealth.view.InputHelper;
import org.byters.game.agilityhealth.view.InputSettings;
import org.byters.game.agilityhealth.view.Navigator;
import org.byters.game.agilityhealth.view.PresenterScreenGameOver;
import org.byters.game.agilityhealth.view.presenter.IPresenterScreenMenu;
import org.byters.game.agilityhealth.view.presenter.PresenterScreenGame;
import org.byters.game.agilityhealth.view.presenter.PresenterScreenMenu;
import org.byters.game.agilityhealth.view.ui.*;
import org.byters.game.agilityhealth.view.ui.util.HeroAnimationHelper;
import org.byters.game.agilityhealth.view.ui.util.MonstersAnimationHelper;

public class Injector {

    private Engine engine;
    private IScreen screenMenu;
    private IScreen screenGame;
    private IScreen screenDeath;
    private IScreen screenWin;
    private IPresenterScreenMenu presenterScreenMenu;
    private PresenterScreenGame presenterScreenGame;
    private Navigator navigator;
    private CacheHero cacheHero;
    private InputHelper inputHelper;
    private CacheResources textureHelper;
    private CacheMeta cacheMeta;
    private InputSettings inputSettings;
    private ViewGUI viewGUI;
    private CacheGUI cacheGUI;
    private CacheMonsters cacheMonsters;
    private PresenterScreenGameOver presenterScreenDeath;
    private MonsterSpawnHelper monsterSpawnHelper;
    private HeroAnimationHelper heroAnimationHelper;
    private MonstersAnimationHelper monsterAnimationHelper;
    private FrameBufferDrawer frameBufferDrawer;

    private IScreen getScreenMenu() {
        if (screenMenu == null)
            screenMenu = new ScreenMenu(getPresenterScreenMenu(), engine.getInjector().getControllerResources().getSpriteBatch(), getHelperResources());
        return screenMenu;
    }

    private IScreen getScreenGame() {
        if (screenGame == null)
            screenGame = new ScreenGame(getPresenterScreenGame(),
                    getInputSetting(),
                    getInputHelper(),
                    engine.getInjector().getControllerResources().getSpriteBatch(),
                    getHelperResources(),
                    getCacheMeta(),
                    getHeroAnimationHelper(),
                    getMonsterAnimationHelper(),
                    getFrameBufferDrawer());
        return screenGame;
    }

    private FrameBufferDrawer getFrameBufferDrawer() {
        if (frameBufferDrawer == null) frameBufferDrawer = new FrameBufferDrawer(
                engine.getInjector().getControllerResources().getSpriteBatch(),
                getCacheMeta());
        return frameBufferDrawer;
    }

    private MonstersAnimationHelper getMonsterAnimationHelper() {
        if (monsterAnimationHelper == null)
            monsterAnimationHelper = new MonstersAnimationHelper(engine.getInjector().getControllerResources(), getHelperResources(), getFrameBufferDrawer());
        return monsterAnimationHelper;
    }

    private HeroAnimationHelper getHeroAnimationHelper() {
        if (heroAnimationHelper == null)
            heroAnimationHelper = new HeroAnimationHelper(engine.getInjector().getControllerResources(), getHelperResources());
        return heroAnimationHelper;
    }

    private IScreen getScreenDeath() {
        if (screenDeath == null)
            screenDeath = new ScreenDeath(getPresenterScreenGameOver(),
                    engine.getInjector().getControllerResources().getSpriteBatch(),
                    getHelperResources());
        return screenDeath;
    }

    private IScreen getScreenWin() {
        if (screenWin == null) screenWin = new ScreenWin(
                getPresenterScreenGameOver(),
                engine.getInjector().getControllerResources().getSpriteBatch(),
                getHelperResources()
        );
        return screenWin;
    }

    private InputSettings getInputSetting() {
        if (inputSettings == null) inputSettings = new InputSettings();
        return inputSettings;
    }

    private CacheResources getHelperResources() {
        if (textureHelper == null) textureHelper = new CacheResources();
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
        if (presenterScreenGame == null) presenterScreenGame = new PresenterScreenGame(
                getCacheMonsters(),
                getCacheMeta(),
                getCacheHero(),
                getCacheGUI(),
                engine.getInjector().getControllerCamera(),
                getNavigator(),
                getScreenDeath(),
                getScreenWin());
        return presenterScreenGame;
    }

    private PresenterScreenGameOver getPresenterScreenGameOver() {
        if (presenterScreenDeath == null)
            presenterScreenDeath = new PresenterScreenGameOver(engine.getInjector().getControllerCamera(), getCacheGUI());
        return presenterScreenDeath;
    }

    private CacheMonsters getCacheMonsters() {
        if (cacheMonsters == null) cacheMonsters = new CacheMonsters(getMonstersSpawnHelper());
        return cacheMonsters;
    }

    private CacheGUI getCacheGUI() {
        if (cacheGUI == null) cacheGUI = new CacheGUI(getCacheMeta());
        return cacheGUI;
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
        getViewGUI().load();
        getNavigator().navigateScreen(getScreenMenu());
        getMonstersSpawnHelper().load();
        engine.getInjector().getControllerCamera().setZoom(getCacheMeta().cameraZoom);
    }

    private MonsterSpawnHelper getMonstersSpawnHelper() {
        if (monsterSpawnHelper == null)
            monsterSpawnHelper = new MonsterSpawnHelper(getHelperResources(),
                    getCacheMeta(),
                    engine.getInjector().getControllerJsonBase());
        return monsterSpawnHelper;
    }

    public Engine getEngine() {
        if (engine == null) engine = new Engine();
        return engine;
    }

    public CacheMeta getCacheMeta() {
        if (cacheMeta == null) cacheMeta = new CacheMeta();
        return cacheMeta;
    }

    public ViewGUI getViewGUI() {
        if (viewGUI == null) viewGUI = new ViewGUI(getCacheGUI(), getEngine().getInjector().getControllerCamera());
        return viewGUI;
    }
}
