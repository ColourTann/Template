package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.SpikeBullet;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Sounds;

public class ThirdLaw extends Weapon {
    public ThirdLaw() {
        super(90, 2);
    }

    private static final float SPEED = 22;

    @Override
    protected void internalFire() {
        Ship s = getShip();
        s.dx+= (float) (Math.cos(s.getRotation())*SPEED);
        s.dy+= (float) (Math.sin(s.getRotation())*SPEED);
        Bullet b = new SpikeBullet();
        b.init();
        b.type = friend? Bullet.BulletType.Friendly: Bullet.BulletType.Enemy;
        b.setup(ship.getNoseX(), ship.getNoseY(), 0, 0, ship.getRotation()+Math.PI, 22);
        SpaceScreen.get().addBullet(b);
        Sounds.playSound(Sounds.boost, Maths.v.set(s.getX(), s.getY()));
    }

    @Override
    public TextureRegion getImage() {
        return Main.atlas.findRegion("weapon/thirdlaw");
    }

    @Override
    public String getName() {
        return "Third Law";
    }
}
