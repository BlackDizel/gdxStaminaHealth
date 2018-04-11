package org.byters.game.agilityhealth.view.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.byters.engine.controller.ControllerCamera;
import org.byters.game.agilityhealth.controller.data.memorycache.ICacheGUI;
import org.byters.game.agilityhealth.model.RectGUI;

import java.lang.ref.WeakReference;

public class ViewGUI {

    private WeakReference<ICacheGUI> refCacheGUI;
    private WeakReference<ControllerCamera> refCamera;

    private ShapeRenderer rendererRectangles;

    public ViewGUI(ICacheGUI cacheGUI, ControllerCamera camera) {
        this.refCamera = new WeakReference<>(camera);
        this.refCacheGUI = new WeakReference<>(cacheGUI);
    }

    public void load() {
        this.rendererRectangles = new ShapeRenderer();
    }

    public void dispose() {
        rendererRectangles.dispose();
    }

    public void draw() {
        if (refCacheGUI.get().isEmpty()) return;

        rendererRectangles.setProjectionMatrix(refCamera.get().getCameraProjection());
        rendererRectangles.begin(ShapeRenderer.ShapeType.Filled);

        for (RectGUI item : refCacheGUI.get().getItems()) {
            if (item.getColor().a <= 0) continue;

            rendererRectangles.setColor(item.getColor());
            rendererRectangles.rect(item.getX(),
                    item.getY(),
                    item.getWidth(),
                    item.getHeight());
        }

        rendererRectangles.end();
    }
}
