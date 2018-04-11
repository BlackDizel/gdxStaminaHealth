package org.byters.game.agilityhealth.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.byters.engine.view.IScreen;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheResources;
import org.byters.game.agilityhealth.view.presenter.IPresenterScreenMenu;

import java.lang.ref.WeakReference;

public class ScreenMenu implements IScreen {

    private WeakReference<SpriteBatch> refSpriteBatch;
    private WeakReference<CacheResources> refTextureHelper;
    private WeakReference<IPresenterScreenMenu> refPresenterScreenMenu;

    private Texture tBg;

    public ScreenMenu(IPresenterScreenMenu presenterScreenMenu,
                      SpriteBatch spriteBatch,
                      CacheResources textureHelper) {
        this.refPresenterScreenMenu = new WeakReference<>(presenterScreenMenu);
        this.refSpriteBatch = new WeakReference<>(spriteBatch);
        this.refTextureHelper = new WeakReference<>(textureHelper);
    }

    @Override
    public void draw() {
        refSpriteBatch.get().draw(tBg, 0, 0);
        //todo draw background
        //todo draw "play" button
    }

    @Override
    public void load() {
        tBg = new Texture(refTextureHelper.get().TEXTURE_BG_MAINMENU);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void input() {
        if (Gdx.input.isTouched()
                || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                || Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            refPresenterScreenMenu.get().onClick();
        //todo handle "play" button click
    }

    @Override
    public void dispose() {

    }
}
