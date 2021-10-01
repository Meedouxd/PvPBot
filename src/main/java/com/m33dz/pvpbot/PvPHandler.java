package com.m33dz.pvpbot;

public class PvPHandler{
    public boolean isOn;
    public int playerHealth = 0;
    public boolean hasATarget = false;
    public PvPHandler(){

    }
    public void toggle(){
        if(isOn){
            isOn = false;
        }
        else{
            isOn = true;
        }
    }
}
