package com.tann.jamgame.screen.spaceScreen.shipUpgrade;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.*;
import com.tann.jamgame.util.Colours;

public class Upgrade {

    private static VomitDrive vd = new VomitDrive();
    public static final Upgrade p1Reversinator = new Upgrade(
            1, vd,
            vd.getImage(),
            vd.getName(),
            "Instantly boost in the opposite direction and reload one blaster shot.\n\nInadvisable to use at speed, when drunk or at all.",
            Colours.neonBlue
    );

    private static ThirdLaw tl = new ThirdLaw();
    public static final Upgrade p1Speedinator = new Upgrade(
            1, tl,
            tl.getImage(),
            "Third Law",
            "Launch a projectile backwards, propelling yourself fowards.\n\nThe only bit of physics a space pirate needs to know.",
            Colours.neonBlue
    );

    public static final Upgrade p2Range = new Upgrade(
            0, UpgradeType.BulletSpeed, .25f,
            Main.atlas.findRegion("weapon/vodkabullets"),
            "Vodka-coated Bullets",
            "+40% blaster distance\n\nA strange but effective alteration!",
            Colours.orange
    );

    public static final Upgrade p2Shots = new Upgrade(
            0, UpgradeType.Max_Shots, 3,
            Main.atlas.findRegion("weapon/reload"),
            "Reload Extender",
            "+3 max stored blaster shots\n\nWaiting to reload is BORING. Do all your reloading while flying around!",
            Colours.orange
    );

    public static final Upgrade p3Bullets = new Upgrade(
            0, UpgradeType.Bullets, (int)(Blaster.NUM_SHOTS*.4f),
            Main.atlas.findRegion("weapon/moreshots"),
            "More Bullets",
            "+40% bullets per blaster shot\n\nLegendary rum runner Bloody Mary wired her blaster to shoot 50 bullets at a time! Unfortunately one of the blew up in the chamber once.",
            Colours.red
    );

    public static final Upgrade p3Turret = new Upgrade(
            0, UpgradeType.Tanker_Shoots, 5,
            Main.atlas.findRegion("weapon/turret"),
            "Tanker Turret",
            "Every time you fire your blaster, a turret on the tanker also fires.\n\nHarald Bluetooth was the first man to use this ingenious system. He defended a convoy of 50 tankers single-handed!",
            Colours.red
    );

    static TimeWarp tw = new TimeWarp();
    static MolotovLauncher ml = new MolotovLauncher();
    public static final Upgrade p4Molotov = new Upgrade(
            2, ml, ml.getImage(),
            ml.getName(),
            "Huge fiery explosive\n\nBailey’s Comet is the most explosive drink known to man so it wasn't long before it found use by pirates.",
            Colours.yellow
    );

    public static final Upgrade p4TimeWarp = new Upgrade(
            2, tw,tw.getImage(), tw.getName(),
            "Speeds up time for you when drunk\n\nMaximum of one shot per relativistic minute. Chronic usage may induce coma.",
            Colours.yellow
    );

    public static final Upgrade p5Fire = new Upgrade(
            1, UpgradeType.Fire, .2f,
            Main.atlas.findRegion("weapon/fire"),
            "Fireball",
            "Set yourself on fire when boosting\n\nA waste of good rum is you ask me.",
            Colours.blue
    );

    public static final Upgrade p5Rockets = new Upgrade(
            1, UpgradeType.Rockets, 5,
            Main.atlas.findRegion("weapon/drunkrockets"),
            "Intoxicated Rockets",
            "Fire rockets every time you use a movement ability\n\nThese look really impressive but if they hit a target then you're lucky!",
            Colours.blue
    );



    public enum UpgradeType{BulletSpeed, Bullets, Max_Shots, Tanker_Shoots, Rockets, Fire};

    protected TextureRegion tr;
    protected String text;
    protected String title;
    public Weapon weapon;
    public int slot;
    public UpgradeType type;
    public float upgradeAmount;
    public Color colour;

    public Upgrade(int slot, Weapon weapon, TextureRegion tr, String title, String text, Color col) {
        setup(tr, text, title, slot, col);
        this.weapon = weapon;
    }

    public Upgrade(int slot, UpgradeType type, float amount, TextureRegion tr, String title, String text, Color col) {
        setup(tr, text, title, slot, col);
        this.type=type;
        this.upgradeAmount =amount;
    }

    private void setup(TextureRegion tr, String text, String title, int slot, Color col){
        this.tr=tr; this.text=text; this.slot=slot; this.title=title; this.colour = col;
    }

    private UpgradeChoicePanel ucp;
    public UpgradeChoicePanel getPanel() {
        if(ucp==null)ucp = new UpgradeChoicePanel(this);
        return ucp;
    }

}
