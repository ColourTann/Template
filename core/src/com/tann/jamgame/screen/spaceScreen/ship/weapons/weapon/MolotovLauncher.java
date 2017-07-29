package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.MolotovBullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.SpikeBullet;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Sounds;

public class MolotovLauncher extends Weapon {
    public MolotovLauncher() {
        super(700, 1);
    }

    @Override
    protected void internalFire() {
        Ship ship = getShip();
        Bullet b = Pools.obtain(MolotovBullet.class);
        b.init();
        b.type = friend? Bullet.BulletType.Friendly: Bullet.BulletType.Enemy;
        b.setup(ship.getNoseX(), ship.getNoseY(), ship.dx, ship.dy, ship.getRotation(), 15+ship.getSpeed());
        SpaceScreen.get().addBullet(b);
        Sounds.playSound(Sounds.molotov_launch, Maths.v.set(b.x,b.y));
    }
}
