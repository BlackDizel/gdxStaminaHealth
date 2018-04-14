package org.byters.game.agilityhealth.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.byters.engine.view.IScreen;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheResources;
import org.byters.game.agilityhealth.view.PresenterScreenGameOver;

import java.lang.ref.WeakReference;

public class ScreenWin implements IScreen {
    private WeakReference<PresenterScreenGameOver> refPresenterScreenDeath;
    private WeakReference<CacheResources> refCacheResources;
    private WeakReference<SpriteBatch> refSpriteBatch;
    private Texture tBg;

    public ScreenWin(
            PresenterScreenGameOver presenterScreenDeath,
            SpriteBatch spriteBatch,
            CacheResources cacheResources) {

        this.refPresenterScreenDeath = new WeakReference<>(presenterScreenDeath);
        this.refSpriteBatch = new WeakReference<>(spriteBatch);
        this.refCacheResources = new WeakReference<>(cacheResources);
    }

    @Override
    public void draw() {
        refSpriteBatch.get().draw(tBg, 0, 0);
    }

    @Override
    public void load() {
        refPresenterScreenDeath.get().onLoad();
        tBg = new Texture(Gdx.files.internal(refCacheResources.get().TEXTURE_BG_WINSCREEN));
    }

    @Override
    public void update(float v) {

    }

    @Override
    public void input() {

    }

    @Override
    public void dispose() {
        tBg.dispose();
    }
}
