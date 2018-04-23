package org.byters.game.agilityhealth.view.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import org.byters.game.agilityhealth.controller.data.memorycache.CacheMeta;

import java.lang.ref.WeakReference;

public class FrameBufferDrawer {

    private WeakReference<CacheMeta> refCacheMeta;
    private WeakReference<SpriteBatch> refSpriteBatch;
    private FrameBuffer frameBuffer;
    private TextureRegion textureRegion;
    private Matrix4 matrix;

    public FrameBufferDrawer(SpriteBatch spriteBatch, CacheMeta cacheMeta) {
        this.refSpriteBatch = new WeakReference<>(spriteBatch);
        this.refCacheMeta = new WeakReference<>(cacheMeta);
    }

    public void load() {

        int w = refCacheMeta.get().screenWidth;
        int h = refCacheMeta.get().screenHeight;

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA4444, w, h, false);
        frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        textureRegion = new TextureRegion(frameBuffer.getColorBufferTexture());
        textureRegion.flip(false, true);

        matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, w, h);
    }

    public void addTexture(TextureRegion region, float x, float y) {
        frameBuffer.begin();

        refSpriteBatch.get().setProjectionMatrix(matrix);
        refSpriteBatch.get().begin();
        refSpriteBatch.get().draw(region, (int) x, (int) y);
        refSpriteBatch.get().end();

        frameBuffer.end();
    }

    public void draw() {
        refSpriteBatch.get().draw(textureRegion, 0, 0);
    }

    public void dispose() {
        frameBuffer.dispose();
    }
}
