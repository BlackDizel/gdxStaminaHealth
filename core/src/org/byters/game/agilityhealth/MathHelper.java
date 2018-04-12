package org.byters.game.agilityhealth;

public class MathHelper {
    public static float distanceSquared(float x1, float y1, float x2, float y2) {
        return (float) (Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
