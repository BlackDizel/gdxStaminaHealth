package org.byters.game.agilityhealth.view.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.byters.engine.view.IScreen;
import org.byters.game.agilityhealth.view.InputHelper;
import org.byters.game.agilityhealth.view.InputSettings;
import org.byters.game.agilityhealth.view.TextureHelper;
import org.byters.game.agilityhealth.view.presenter.PresenterScreenGame;

import java.lang.ref.WeakReference;

public class ScreenGame implements IScreen {

    private WeakReference<InputSettings> refInputSettings;
    private WeakReference<TextureHelper> refTextureHelper;
    private WeakReference<InputHelper> refInputHelper;
    private WeakReference<PresenterScreenGame> refPresenterScreenGame;
    private WeakReference<SpriteBatch> refSpriteBatch;

    private Texture tHero;
    private Texture tBg;

    public ScreenGame(PresenterScreenGame presenterScreenGame,
                      InputSettings inputSettings,
                      InputHelper inputHelper,
                      SpriteBatch spriteBatch,
                      TextureHelper textureHelper) {
        this.refPresenterScreenGame = new WeakReference<>(presenterScreenGame);
        this.refSpriteBatch = new WeakReference<>(spriteBatch);
        this.refInputHelper = new WeakReference<>(inputHelper);
        this.refTextureHelper = new WeakReference<>(textureHelper);
        this.refInputSettings = new WeakReference<>(inputSettings);
    }

    @Override
    public void draw() {
        //todo draw enemies
        refSpriteBatch.get().draw(tBg, 0, 0);
        refSpriteBatch.get().draw(tHero, refPresenterScreenGame.get().getHeroPosX(), refPresenterScreenGame.get().getHeroPosY());
    }

    @Override
    public void load() {
        tBg = new Texture(refTextureHelper.get().TEXTURE_GAME_BG);
        tHero = new Texture(refTextureHelper.get().TEXTURE_HERO);
    }

    @Override
    public void update(float delta) {
        refPresenterScreenGame.get().onUpdate(delta);
    }

    @Override
    public void input() {
        checkMove();
        checkRun();
        //todo input hero
    }

    private void checkRun() {
        refPresenterScreenGame.get().onMoveMode(
                refInputHelper.get().isPressed(refInputSettings.get().KEYS_RUN));
    }

    private void checkMove() {
        refPresenterScreenGame.get().resetMove();

        if (refInputHelper.get().isPressed(refInputSettings.get().KEYS_MOVE_LEFT))
            refPresenterScreenGame.get().onMove(Math.PI);

        if (refInputHelper.get().isPressed(refInputSettings.get().KEYS_MOVE_RIGHT))
            refPresenterScreenGame.get().onMove(0);

        if (refInputHelper.get().isPressed(refInputSettings.get().KEYS_MOVE_TOP))
            refPresenterScreenGame.get().onMove(Math.PI / 2f);

        if (refInputHelper.get().isPressed(refInputSettings.get().KEYS_MOVE_BOTTOM))
            refPresenterScreenGame.get().onMove(Math.PI * 3 / 2f);

    }

    @Override
    public void dispose() {
        tBg.dispose();
        tHero.dispose();
    }
}
