package org.byters.game.agilityhealth.view.ui.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawHelper {
    public static void drawCentered(SpriteBatch spriteBatch, Texture texture, float x, float y) {
        spriteBatch.draw(texture, x - texture.getWidth() / 2, y - texture.getHeight() / 2);
    }

    public static void drawCentered(SpriteBatch spriteBatch, TextureRegion texture, float x, float y) {
        spriteBatch.draw(texture, x - texture.getRegionWidth() / 2, y - texture.getRegionHeight() / 2);
    }
}
