package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.tann.jamgame.util.Sounds;

public class TimeWarp extends Weapon{
    public TimeWarp() {
        super(1000, 1);
    }

    @Override
    protected void internalFire() {
        getShip().speed(200);
        Sounds.playSound(Sounds.speed, null);
    }
}
