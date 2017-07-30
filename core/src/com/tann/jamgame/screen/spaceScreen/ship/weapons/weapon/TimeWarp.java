package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.jamgame.Main;
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

    @Override
    public String getName() {
        return "Martini\nTimespin";
    }

    @Override
    public TextureRegion getImage() {
        return Main.atlas.findRegion("weapon/martini");
    }
}
