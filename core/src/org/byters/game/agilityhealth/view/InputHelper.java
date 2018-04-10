package org.byters.game.agilityhealth.view;

import com.badlogic.gdx.Gdx;

public class InputHelper {
    public boolean isPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }
}
