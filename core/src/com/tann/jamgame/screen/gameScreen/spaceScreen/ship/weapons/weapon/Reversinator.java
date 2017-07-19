package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon;

import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;

public class Reversinator extends Weapon {
    public Reversinator() {
        super(50, 1);
    }

    static final float SPEED = 22;

    @Override
    protected void internalFire() {
        Ship s = getShip();
        float angle = (float) (s.getRotation()-Math.PI);
        s.setRotation((float) (angle));
        s.dx= (float) (Math.cos(angle)*SPEED);
        s.dy= (float) (Math.sin(angle)*SPEED);
    }

    @Override
    public String getName() {
        return "reversinator";
    }
}
