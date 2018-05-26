package com.tann.jamgame.screen.gameScreen.map.entities.weapons;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.screen.gameScreen.map.entities.Drone;

public abstract class Weapon {
    private final float cooldown;
    private float currentCooldown;
    Drone source;
    public Weapon(Drone source, float cooldown) {
        this.source = source;
        this.cooldown = cooldown;
    }

    public void tick(float delta){
        currentCooldown -= delta;
    }

    public boolean attemptAttack(Drone target){
        if(currentCooldown < 0){
            currentCooldown = cooldown;
            attack(target);
            return true;
        }
        return false;
    }

    abstract void attack(Drone target);
}
