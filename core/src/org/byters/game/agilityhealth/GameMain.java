package org.byters.game.agilityhealth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import org.byters.game.agilityhealth.controller.Injector;

public class GameMain extends ApplicationAdapter {

    private Injector injector;

    public Injector getInjector() {
        return injector;
    }

    public GameMain(){
        injector = new Injector();
    }

    @Override
    public void create() {
        injector.init();
        setColorClear();
    }

    private void setColorClear() {
        Color colorClear = new Color();
        colorClear.r = 0.1f;
        colorClear.g = 0.1f;
        colorClear.b = 0.1f;
        colorClear.a = 1f;
        injector.getEngine().setColorClear(colorClear);
    }

    @Override
    public void render() {
        injector.getEngine().render();
        injector.getViewGUI().draw();
    }

    @Override
    public void dispose() {
        injector.getEngine().dispose();
        injector.getViewGUI().dispose();
    }

    public void resize(int width, int height) {
        injector.getEngine().resize(width, height);
    }

}
