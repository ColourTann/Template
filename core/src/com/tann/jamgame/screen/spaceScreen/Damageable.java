package com.tann.jamgame.screen.spaceScreen;

import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;

/**
 * Created by tann on 09/07/2017.
 */
public interface Damageable {
    public void damage(int amount);
    public boolean affectedBy(Bullet.BulletType type);
}
