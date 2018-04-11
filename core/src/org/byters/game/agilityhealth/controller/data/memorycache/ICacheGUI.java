package org.byters.game.agilityhealth.controller.data.memorycache;

import org.byters.game.agilityhealth.model.RectGUI;

import java.util.ArrayList;

public interface ICacheGUI {
    boolean isEmpty();

    ArrayList<RectGUI> getItems();
}
