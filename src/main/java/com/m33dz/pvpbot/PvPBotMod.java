package com.m33dz.pvpbot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = PvPBotMod.MODID, version = PvPBotMod.VERSION)
public class PvPBotMod
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    PvPHandler pvpHandler = new PvPHandler();
    Target target = new Target();

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event){
        String message = event.message.getUnformattedText();
        if(message.equals("<" + mc.thePlayer.getName() + ">" + " !pvp")){
            pvpHandler.toggle();
            if(pvpHandler.isOn){
                mc.thePlayer.sendChatMessage("PvpBot: activated");
            }
            else {
                mc.thePlayer.sendChatMessage("PvpBot: deactivated");
            }

        }
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(mc.thePlayer != null){
            if(pvpHandler.isOn){
                mc.thePlayer.setSprinting(true);
                checkTarget();
                checkHealth();
                //mc.thePlayer.rotationYaw = 0;
                //mc.thePlayer.rotationPitch = 0;
            }
        }
    }

    public void proccessHit(Entity entity) {
        mc.playerController.attackEntity(mc.thePlayer, entity);
    }
    public void checkTarget(){
        if(!target.hasATarget){
            for (Entity e : mc.theWorld.loadedEntityList) {
                if (e instanceof EntityOtherPlayerMP) {
                    if (mc.thePlayer.getDistanceSqToEntity(e) <= 3.8f && e.isEntityAlive()) {
                        proccessHit(e);
                    }
                }
            }
        }
    }
    public void checkHealth(){
        if(mc.thePlayer.getHealth() <=  4.5){
            System.out.println("Health low");
            mc.thePlayer.inventory.currentItem = 1;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
        }
        if(mc.thePlayer.getHealth() >= 5.0){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            mc.thePlayer.inventory.currentItem = 0;
        }
    }
}
