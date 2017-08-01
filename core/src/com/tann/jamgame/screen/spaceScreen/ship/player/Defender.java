package com.tann.jamgame.screen.spaceScreen.ship.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Blaster;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.MolotovLauncher;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.TimeWarp;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Weapon;
import com.tann.jamgame.screen.spaceScreen.shipUpgrade.ShipUpgradeGroup;
import com.tann.jamgame.screen.spaceScreen.shipUpgrade.Upgrade;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Sounds;

public class Defender extends PlayerShip {


    public static TextureRegion defender = Main.atlas.findRegion("ship/playerFighter");

    public Defender() {
        super(defender, .6f,200, .08f);
        setPosition(500,500);
        float sizeMult = 1.2f;
        setSize(tr.getRegionWidth()*sizeMult, tr.getRegionHeight()*sizeMult);
        addWeapon(new Blaster(),0);
        setHp(9999130);
    }

    @Override
    protected void internalAct(float delta) {
        super.internalAct(delta);
    }

    public float getBaseZoom() {
        return 1.6f;
    }

    @Override
    public void drawMinimap(Batch batch, float x, float y) {
        batch.setColor(Colours.withAlpha(Colours.light, batch.getColor().a));
        float size = 4;
        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);
    }

    public void setUpgrades(ShipUpgradeGroup sug) {
        setUpgrades(sug.getUpgrades());
    }

    private void setUpgrades(Array<Upgrade> upgrades) {
        clearUpgrades();
        weapons[1]=null;
        weapons[2]=null;
        for(Upgrade u:upgrades){
            if(u.weapon!=null){
                addWeapon(u.weapon, u.slot);
            }
            else{
                weapons[u.slot].addUpgrade(u);
            }
        }
        for(int i=0;i<weapons.length;i++){
            if(weapons[i]!=null){
                weapons[i].reset();
            }
        }
    }

    @Override
    protected void onDeath() {
        super.onDeath();
        Sounds.playSound(Sounds.explodePlayer, Maths.v.set(getX(), getY()));
        SpaceScreen.get().defeat();
    }

    private void clearUpgrades() {
        for(Weapon w:weapons){
            if(w!=null){
                w.clearUpgrades();
            }
        }
    }
}
