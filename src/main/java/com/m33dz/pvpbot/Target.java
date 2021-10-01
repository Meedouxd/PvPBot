package com.m33dz.pvpbot;

import net.minecraft.entity.Entity;

public class Target {
    public Entity target;
    public boolean hasATarget = false;
    public void setTarget(Entity entity){
            entity = target;
            hasATarget = true;
    }
    public Entity getTarget(){
        return target;
    }
    public void checkTarget(){
        if(target == null){
            hasATarget = false;
        }
    }
}
