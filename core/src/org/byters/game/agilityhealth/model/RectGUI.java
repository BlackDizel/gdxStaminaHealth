package org.byters.game.agilityhealth.model;

import com.badlogic.gdx.graphics.Color;

public class RectGUI {

    private Color color;
    private float x;
    private float y;
    private float width;
    private float height;

    public RectGUI(int w, int h) {
        this.width = w;
        this.height = h;
        this.color = Color.WHITE;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
