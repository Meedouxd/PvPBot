package com.m33dz.pvpbot;

import net.minecraft.entity.Entity;

public class Target {
    public Entity target;
    public boolean hasATarget = false;
    public int targetID = 0;
    public void setTarget(Entity entity){
            entity = target;
            hasATarget = true;
    }
    public Entity getTarget(){
        if(target != null){
            return target;
        }else{
            return null;
        }
    }
    public void checkTarget(){
        if(target == null){
            hasATarget = false;
        }
    }
    public void setTargetID(int id){
        targetID = id;
    }
    public int getTargetID(){
        return targetID;
    }
}
