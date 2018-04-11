package org.byters.game.agilityhealth.view.ui.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HelperParticles {

    private ParticleEffect effect;
    private boolean isEmit;

    public void load(String particleFile, String folderParicleSprite) {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal(particleFile), Gdx.files.internal(folderParicleSprite));
        play();
    }

    public void draw(SpriteBatch batch, float x, float y) {
        effect.setPosition(x, y);
        effect.draw(batch);
    }

    public void update(float delta) {
        effect.update(delta);
    }

    public void pause() {
        if (!isEmit) return;
        isEmit = false;
        effect.allowCompletion();
    }

    public void play() {
        if (isEmit) return;
        isEmit = true;
        effect.start();
    }

    public void dispose() {
        effect.dispose();
    }

    public void play(boolean isPlay) {
        if (isPlay) play();
        else pause();
    }
}
