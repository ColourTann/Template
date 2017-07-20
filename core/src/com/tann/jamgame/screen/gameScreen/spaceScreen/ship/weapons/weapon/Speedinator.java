package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.BoringBullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.SpikeBullet;

public class Speedinator extends Weapon {
    public Speedinator() {
        super(90, 2);
    }

    private static final float SPEED = 22;

    @Override
    protected void internalFire() {
        Ship s = getShip();
        s.dx+= (float) (Math.cos(s.getRotation())*SPEED);
        s.dy+= (float) (Math.sin(s.getRotation())*SPEED);
        Bullet b = Pools.obtain(SpikeBullet.class);
        b.init();
        b.type = friend? Bullet.BulletType.Friendly: Bullet.BulletType.Enemy;
        b.setup(ship.getNoseX(), ship.getNoseY(), 0, 0, ship.getRotation()+Math.PI, 22);
        SpaceScreen.get().addBullet(b);
    }
}
