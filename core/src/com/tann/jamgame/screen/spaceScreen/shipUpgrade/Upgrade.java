package com.tann.jamgame.screen.spaceScreen.shipUpgrade;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Blaster;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Reversinator;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Speedinator;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Weapon;

public class Upgrade {

    public static final Upgrade p1Reversinator = new Upgrade(
            1, new Reversinator(),
            new Reversinator().getImage(),
            "Reversinator",
            "Instantly boost in the opposite direction and reload blaster once!\ncooldown: 0.8 seconds\ncharges: 1"
    );
    public static final Upgrade p1Speedinator = new Upgrade(
            1, new Speedinator(),
            new Speedinator().getImage(),
            "Speedinator",
            "Launch a rocket backwards, propelling yourself fowards\ncooldown: 1.5 seconds\ncharges: 2"
    );

    public static final Upgrade p2Speed = new Upgrade(
            0, UpgradeType.BulletSpeed, .25f,
            Main.atlas.findRegion("upgrade/doubleshot"),
            "Extra Range",
            "+40% blaster distance"
    );

    public static final Upgrade p2Shots = new Upgrade(
            0, UpgradeType.Max_Shots, 3,
            Main.atlas.findRegion("upgrade/laser-blast"),
            "More stored charges",
            "+3 max blaster shots"
    );

    public static final Upgrade p3Speed = new Upgrade(
            0, UpgradeType.Bullets, (int)(Blaster.NUM_SHOTS*.4f),
            Main.atlas.findRegion("upgrade/doubleshot"),
            "More bullets",
            "+40% bullets per blaster shot"
    );

    public static final Upgrade p3Shots = new Upgrade(
            0, UpgradeType.Tanker_Shoots, 5,
            Main.atlas.findRegion("upgrade/laser-blast"),
            "Turret",
            "Tanker shoots when you do!"
    );

    public static final Upgrade p4Speed = new Upgrade(
            0, UpgradeType.BulletSpeed, .2f,
            Main.atlas.findRegion("upgrade/doubleshot"),
            "Turret",
            "+20% shotgun distance"
    );

    public static final Upgrade p4Shots = new Upgrade(
            0, UpgradeType.Bullets, 5,
            Main.atlas.findRegion("upgrade/laser-blast"),
            "Turret",
            "+5 bullets per shotgun shot"
    );

    public static final Upgrade p5Speed = new Upgrade(
            0, UpgradeType.BulletSpeed, .2f,
            Main.atlas.findRegion("upgrade/doubleshot"),
            "Turret",
            "+20% shotgun distance"
    );

    public static final Upgrade p5Shots = new Upgrade(
            0, UpgradeType.Bullets, 5,
            Main.atlas.findRegion("upgrade/laser-blast"),
            "Turret",
            "+5 bullets per shotgun shot"
    );

    public enum UpgradeType{BulletSpeed, Bullets, Max_Shots, Tanker_Shoots};

    protected TextureRegion tr;
    protected String text;
    protected String title;
    public Weapon weapon;
    public int slot;
    public UpgradeType type;
    public float upgradeAmount;


    public Upgrade(int slot, Weapon weapon, TextureRegion tr, String title, String text) {
        setup(tr, text, title, slot);
        this.weapon = weapon;
    }

    public Upgrade(int slot, UpgradeType type, float amount, TextureRegion tr, String title, String text) {
        setup(tr, text, title, slot);
        this.type=type;
        this.upgradeAmount =amount;
    }

    private void setup(TextureRegion tr, String text, String title, int slot){
        this.tr=tr; this.text=text; this.slot=slot; this.title=title;
    }

    private UpgradeChoicePanel ucp;
    public UpgradeChoicePanel getPanel() {
        if(ucp==null)ucp = new UpgradeChoicePanel(this);
        return ucp;
    }

}
