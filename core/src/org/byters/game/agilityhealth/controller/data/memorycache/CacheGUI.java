package org.byters.game.agilityhealth.controller.data.memorycache;

import com.badlogic.gdx.graphics.Color;
import org.byters.game.agilityhealth.model.RectGUI;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CacheGUI implements ICacheGUI {

    private WeakReference<CacheMeta> refCacheMeta;
    private ArrayList<RectGUI> data;

    public CacheGUI(CacheMeta cacheMeta) {
        this.refCacheMeta = new WeakReference<>(cacheMeta);
        resetData();
    }

    @Override
    public ArrayList<RectGUI> getItems() {
        return data;
    }

    @Override
    public boolean isEmpty() {
        return data == null || data.size() == 0;
    }

    public void resetData() {
        data = null;
    }

    public void setStamina(float staminaPercent, Color color, float x, float y) {
        if (data == null || data.size() == 0)
            initData();

        data.get(0).setWidth(staminaPercent * refCacheMeta.get().uiStaminaBarWidth);

        if (!data.get(0).getColor().equals(color))
            updateColor(color);

        if (!(data.get(0).getX() == x && data.get(0).getY() == y))
            setPosition(x, y);
    }

    private void updateColor(Color color) {
        if (data == null) return;
        for (RectGUI item : data)
            item.setColor(color);
    }

    private void setPosition(float x, float y) {
        if (data == null || data.size() != 5) return;

        data.get(0).setPosition(
                x + refCacheMeta.get().uiStaminaBarBorderWidth + refCacheMeta.get().uiStaminaBarBorderDistance,
                y + refCacheMeta.get().uiStaminaBarBorderWidth + refCacheMeta.get().uiStaminaBarBorderDistance);

        data.get(1).setPosition(
                x + refCacheMeta.get().uiStaminaBarBorderWidth,
                y);

        data.get(2).setPosition(
                x + refCacheMeta.get().uiStaminaBarBorderWidth,
                y + refCacheMeta.get().uiStaminaBarHeight + refCacheMeta.get().uiStaminaBarBorderWidth + 2 * refCacheMeta.get().uiStaminaBarBorderDistance);

        data.get(3).setPosition(
                x,
                y);

        data.get(4).setPosition(
                x + refCacheMeta.get().uiStaminaBarWidth + refCacheMeta.get().uiStaminaBarBorderWidth + 2 * refCacheMeta.get().uiStaminaBarBorderDistance,
                y);
    }

    private void initData() {
        data = new ArrayList<>();

        data.add(new RectGUI(
                refCacheMeta.get().uiStaminaBarWidth,
                refCacheMeta.get().uiStaminaBarHeight));  //bar

        data.add(new RectGUI(
                refCacheMeta.get().uiStaminaBarWidth + 2 * refCacheMeta.get().uiStaminaBarBorderDistance,
                refCacheMeta.get().uiStaminaBarBorderWidth));

        data.add(new RectGUI(
                refCacheMeta.get().uiStaminaBarWidth + 2 * refCacheMeta.get().uiStaminaBarBorderDistance,
                refCacheMeta.get().uiStaminaBarBorderWidth));

        data.add(new RectGUI(
                refCacheMeta.get().uiStaminaBarBorderWidth,
                refCacheMeta.get().uiStaminaBarHeight + 2 * (refCacheMeta.get().uiStaminaBarBorderWidth + refCacheMeta.get().uiStaminaBarBorderDistance)));

        data.add(new RectGUI(
                refCacheMeta.get().uiStaminaBarBorderWidth,
                refCacheMeta.get().uiStaminaBarHeight + 2 * (refCacheMeta.get().uiStaminaBarBorderWidth + refCacheMeta.get().uiStaminaBarBorderDistance)));
    }
}
