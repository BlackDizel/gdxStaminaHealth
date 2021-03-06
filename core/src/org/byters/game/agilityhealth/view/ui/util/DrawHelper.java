package org.byters.game.agilityhealth.view.ui.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DrawHelper {
    public static void drawCentered(SpriteBatch spriteBatch, Texture texture, float x, float y) {
        spriteBatch.draw(texture, x - texture.getWidth() / 2, y - texture.getHeight() / 2);
    }

    public static void drawCentered(SpriteBatch spriteBatch, TextureRegion texture, float x, float y) {
        spriteBatch.draw(texture, x - texture.getRegionWidth() / 2, y - texture.getRegionHeight() / 2);
    }

    public static void drawCentered(SpriteBatch spriteBatch, TextureRegion texture, float x, float y, boolean isRight) {
        spriteBatch.draw(texture, x - texture.getRegionWidth() / 2, y - texture.getRegionHeight() / 2, texture.getRegionWidth() * (isRight ? 1 : -1), texture.getRegionHeight());
    }

    public static void draw(SpriteBatch spriteBatch,
                            TextureAtlas.AtlasRegion texture,
                            float x,
                            float xOffset,
                            float y,
                            float yOffset,
                            boolean isRight) {
        float xPos = x + (isRight ? -xOffset : +xOffset);
        spriteBatch.draw(texture, xPos, y - yOffset, texture.getRegionWidth() * (isRight ? 1 : -1), texture.getRegionHeight());
    }
}
