package com.tann.jamgame.screen.spaceScreen.shipUpgrade;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Reversinator;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Speedinator;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Weapon;

public class Upgrade {

    public static final Upgrade p1Reversinator = new Upgrade(
            1, new Reversinator(),
            new Reversinator().getImage(),
            "Instantly boost in the opposite direction\ncooldown: 0.8 seconds\ncharges: 1"
    );
    public static final Upgrade p1Speedinator = new Upgrade(
            1, new Speedinator(),
            new Speedinator().getImage(),
            "Launch a rocket backwards, propelling yourself fowards\ncooldown: 1.5 seconds\ncharges: 2"
    );

    public static final Upgrade p2Speed = new Upgrade(
            0, UpgradeType.BulletSpeed, .2f,
            Main.atlas.findRegion("upgrade/doubleshot"),
            "+20% shotgun distance"
    );

    public static final Upgrade p2Shots = new Upgrade(
            0, UpgradeType.Shots, 5,
            Main.atlas.findRegion("upgrade/laser-blast"),
            "+5 bullets per shotgun shot"
    );

    public static final Upgrade p3Speed = new Upgrade(
            0, UpgradeType.BulletSpeed, .2f,
            Main.atlas.findRegion("addUpgrade/doubleshot"),
            "+20% shotgun distance"
    );

    public static final Upgrade p3Shots = new Upgrade(
            0, UpgradeType.Shots, 5,
            Main.atlas.findRegion("upgrade/laser-blast"),
            "+5 bullets per shotgun shot"
    );

    public static final Upgrade p4Speed = new Upgrade(
            0, UpgradeType.BulletSpeed, .2f,
            Main.atlas.findRegion("upgrade/doubleshot"),
            "+20% shotgun distance"
    );

    public static final Upgrade p4Shots = new Upgrade(
            0, UpgradeType.Shots, 5,
            Main.atlas.findRegion("upgrade/laser-blast"),
            "+5 bullets per shotgun shot"
    );

    public static final Upgrade p5Speed = new Upgrade(
            0, UpgradeType.BulletSpeed, .2f,
            Main.atlas.findRegion("upgrade/doubleshot"),
            "+20% shotgun distance"
    );

    public static final Upgrade p5Shots = new Upgrade(
            0, UpgradeType.Shots, 5,
            Main.atlas.findRegion("upgrade/laser-blast"),
            "+5 bullets per shotgun shot"
    );

    public enum UpgradeType{BulletSpeed, Shots};

    protected TextureRegion tr;
    protected String text;
    public Weapon weapon;
    public int slot;
    public UpgradeType type;
    public float upgradeAmount;


    public Upgrade(int slot, Weapon weapon, TextureRegion tr, String text) {
        setup(tr, text, slot);
        this.weapon = weapon;
    }

    public Upgrade(int slot, UpgradeType type, float amount, TextureRegion tr, String text) {
        setup(tr, text, slot);
        this.type=type;
        this.upgradeAmount =amount;
    }

    private void setup(TextureRegion tr, String text, int slot){
        this.tr=tr; this.text=text; this.slot=slot;
    }

    private UpgradeChoicePanel ucp;
    public UpgradeChoicePanel getPanel() {
        if(ucp==null)ucp = new UpgradeChoicePanel(this);
        return ucp;
    }

}
