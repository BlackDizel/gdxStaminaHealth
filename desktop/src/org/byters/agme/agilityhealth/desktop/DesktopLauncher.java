package org.byters.agme.agilityhealth.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.byters.game.agilityhealth.GameMain;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        GameMain game = new GameMain();

        config.resizable = false;
        config.width = game.getInjector().getCacheMeta().screenWidth;
        config.height = game.getInjector().getCacheMeta().screenHeight;
        new LwjglApplication(game, config);
    }
}
