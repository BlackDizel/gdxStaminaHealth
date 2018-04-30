package org.byters.game.agilityhealth.view.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.byters.engine.controller.ControllerResources;
import org.byters.engine.view.IScreen;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheResources;
import org.byters.game.agilityhealth.view.InputHelper;
import org.byters.game.agilityhealth.view.InputSettings;
import org.byters.game.agilityhealth.view.presenter.PresenterScreenGame;
import org.byters.game.agilityhealth.view.ui.util.HelperParticles;
import org.byters.game.agilityhealth.view.ui.util.HeroAnimationHelper;
import org.byters.game.agilityhealth.view.ui.util.MonstersAnimationHelper;

import java.lang.ref.WeakReference;

public class ScreenGame implements IScreen {

    private WeakReference<FrameBufferDrawer> refFrameBufferDrawer;
    private WeakReference<CacheMeta> refCacheMeta;
    private WeakReference<InputSettings> refInputSettings;
    private WeakReference<CacheResources> refTextureHelper;
    private WeakReference<InputHelper> refInputHelper;
    private WeakReference<PresenterScreenGame> refPresenterScreenGame;
    private WeakReference<SpriteBatch> refSpriteBatch;
    private WeakReference<MonstersAnimationHelper> refMonstersAnimationHelper;

    private Texture tBg, tBgForest;

    private HelperParticles bonefireParticles, dustParticles;
    private WeakReference<HeroAnimationHelper> refHeroAnimationHelper;

    public ScreenGame(PresenterScreenGame presenterScreenGame,
                      InputSettings inputSettings,
                      InputHelper inputHelper,
                      CacheResources textureHelper,
                      CacheMeta cacheMeta,
                      HeroAnimationHelper heroAnimationHelper,
                      FrameBufferDrawer frameBufferDrawer,
                      ControllerResources controllerResources,
                      MonstersAnimationHelper monstersAnimationHelper) {

        this.refPresenterScreenGame = new WeakReference<>(presenterScreenGame);
        this.refSpriteBatch = new WeakReference<>(controllerResources.getSpriteBatch());
        this.refInputHelper = new WeakReference<>(inputHelper);
        this.refTextureHelper = new WeakReference<>(textureHelper);
        this.refInputSettings = new WeakReference<>(inputSettings);
        this.refCacheMeta = new WeakReference<>(cacheMeta);
        this.refFrameBufferDrawer = new WeakReference<>(frameBufferDrawer);
        this.refMonstersAnimationHelper = new WeakReference<>(monstersAnimationHelper);

        bonefireParticles = new HelperParticles();
        dustParticles = new HelperParticles();
        refHeroAnimationHelper = new WeakReference<>(heroAnimationHelper);
    }

    @Override
    public void draw() {
        refSpriteBatch.get().draw(tBg,
                refCacheMeta.get().screenGameBackgroundX,
                refCacheMeta.get().screenGameBackgroundY);

        refFrameBufferDrawer.get().draw();

        dustParticles.draw(refSpriteBatch.get(), refPresenterScreenGame.get().getHeroPosX(), refPresenterScreenGame.get().getHeroPosY());

        bonefireParticles.draw(refSpriteBatch.get(),
                refPresenterScreenGame.get().getBonefirePosX(),
                refPresenterScreenGame.get().getBonefirePosY());

        drawMonsters();

        refHeroAnimationHelper.get().draw(refSpriteBatch.get(),
                refPresenterScreenGame.get().getHeroPosX(),
                refPresenterScreenGame.get().getHeroPosY(),
                refPresenterScreenGame.get().isHeroMoveRight(),
                refPresenterScreenGame.get().isHeroAttacking(),
                refPresenterScreenGame.get().isHeroMoving());

        refSpriteBatch.get().draw(tBgForest,
                refCacheMeta.get().screenGameBackgroundX
                , refCacheMeta.get().screenGameBackgroundY);
    }

    private void drawMonsters() {
        if (!refPresenterScreenGame.get().isMonstersExist()) return;
        refMonstersAnimationHelper.get().draw(refSpriteBatch.get(), refPresenterScreenGame.get());
    }

    @Override
    public void load() {
        tBg = new Texture(refTextureHelper.get().TEXTURE_GAME_BG);
        tBgForest = new Texture(refTextureHelper.get().TEXTURE_GAME_BG_FOREST);
        bonefireParticles.load(refTextureHelper.get().PARTICLES_FILE_BONEFIRE,
                refTextureHelper.get().FOLDER_PARTICLES_SPRITE);
        dustParticles.load(refTextureHelper.get().PARTICLES_FILE_DUST,
                refTextureHelper.get().FOLDER_PARTICLES_SPRITE);

        refPresenterScreenGame.get().onLoad();
        refHeroAnimationHelper.get().load();
        refMonstersAnimationHelper.get().load();
        refFrameBufferDrawer.get().load();
    }

    @Override
    public void update(float delta) {
        refPresenterScreenGame.get().onUpdate(delta, refMonstersAnimationHelper.get());

        dustParticles.play(refPresenterScreenGame.get().isDrawDust());

        dustParticles.update(delta);
        bonefireParticles.update(delta);
        refHeroAnimationHelper.get().update(delta);
    }

    @Override
    public void input() {
        checkMove();
        checkRun();
        checkAttack();
    }

    private void checkAttack() {
        if (refInputHelper.get().isButtonPressed(refInputSettings.get().KEYS_ATTACK))
            refPresenterScreenGame.get().onPressedAttack();
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
        bonefireParticles.dispose();
        dustParticles.dispose();
        refHeroAnimationHelper.get().dispose();
        refMonstersAnimationHelper.get().dispose();
        refFrameBufferDrawer.get().dispose();
    }
}
