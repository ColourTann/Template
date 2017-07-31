package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.BlasterBullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.shipUpgrade.Upgrade;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Particle;
import com.tann.jamgame.util.Sounds;

public class Blaster extends Weapon {
    public Blaster() {
        super(45, 5);
    }

    public static final int NUM_SHOTS=10;
    private static final float ANGLE_RAND = .48f, MIN_SPEED = 40, MAX_SPEED = 60;
    private static final float KNOCKBACK = 3;

    @Override
    protected void internalFire() {
        Ship ship = getShip();
        Sounds.playSound(Sounds.shot, .4f, 1, Maths.v.set(ship.getX(), ship.getY()));
        for(int i = 0; i<NUM_SHOTS+getBonus(Upgrade.UpgradeType.Bullets); i++){
            Bullet b = Pools.obtain(BlasterBullet.class);
            b.init();
            b.type = friend? Bullet.BulletType.Friendly: Bullet.BulletType.Enemy;
            b.setup(ship.getNoseX(), ship.getNoseY(), ship.dx*(1+getBonus(Upgrade.UpgradeType.BulletSpeed)), ship.dy*(1+getBonus(Upgrade.UpgradeType.BulletSpeed)), ship.getRotation()+ Particle.rand(-ANGLE_RAND, ANGLE_RAND),
                    Particle.rand(MIN_SPEED,MAX_SPEED)*(1+getBonus(Upgrade.UpgradeType.BulletSpeed)));
            b.setDrag(.90f);
            b.setLife(35);
            SpaceScreen.get().addBullet(b);
        }
        ship.dx -= Math.cos(ship.getRotation())*KNOCKBACK;
        ship.dy -= Math.sin(ship.getRotation())*KNOCKBACK;
        if(getBonus(Upgrade.UpgradeType.Tanker_Shoots)!=0){
            for(int i = 0; i<(NUM_SHOTS+getBonus(Upgrade.UpgradeType.Bullets)); i++){
                ship = SpaceScreen.get().map.tanker;
                Bullet b = Pools.obtain(BlasterBullet.class);
                b.init();
                b.type = friend? Bullet.BulletType.Friendly: Bullet.BulletType.Enemy;
                b.setup(ship.getNoseX()-ship.getWidth()/2, ship.getNoseY(), ship.dx, ship.dy, getShip().getRotation()+ Particle.rand(-ANGLE_RAND, ANGLE_RAND),
                        Particle.rand(MIN_SPEED,MAX_SPEED)*(1+getBonus(Upgrade.UpgradeType.BulletSpeed)));
                b.setDrag(.90f);
                b.setLife(35);
                SpaceScreen.get().addBullet(b);
                ((BlasterBullet)b).co= Colours.green;
            }

        }
    }

    @Override
    public String getName() {
        return "Blaster";
    }

}
