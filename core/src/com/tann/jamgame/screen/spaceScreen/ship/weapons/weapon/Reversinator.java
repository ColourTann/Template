package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Sounds;

public class Reversinator extends Weapon {
    public Reversinator() {
        super(50, 1);
    }

    static final float SPEED = 22;

    @Override
    protected void internalFire() {
        Ship s = getShip();
        s.weapons[0].reloadCharge();
        float angle = (float) (s.getRotation()-Math.PI);
        s.setRotation((float) (angle));
        s.dx= (float) (Math.cos(angle)*SPEED);
        s.dy= (float) (Math.sin(angle)*SPEED);
        Sounds.playSound(Sounds.reverse, Maths.v.set(s.getX(), s.getY()));
    }

    @Override
    public String getName() {
        return "reversinator";
    }
}
