package com.m33dz.pvpbot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = PvPBotMod.MODID, version = PvPBotMod.VERSION)
public class PvPBotMod
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    PvPHandler pvpHandler = new PvPHandler();
    Target target = new Target();
    Keybinds keybinds = new Keybinds();

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        Keybinds.register();
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
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(mc.thePlayer != null){
            if(pvpHandler.isOn){
                mc.thePlayer.setSprinting(true);
                checkTarget();
                lookAtEntity();
                checkHealth();
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
                    if (mc.thePlayer.getDistanceSqToEntity(e) <= 26.0f && e.isEntityAlive()) {
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
    public void checkPearlThrow(){
        if(mc.thePlayer.getHealth() >= 10.0){
            for (Entity e : mc.theWorld.loadedEntityList) {
                if (e instanceof EntityOtherPlayerMP) {
                    if (mc.thePlayer.getDistanceSqToEntity(e) >= 26.0f && e.isEntityAlive()) {
                        mc.thePlayer.inventory.currentItem = 2;
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    }
                }
            }
        }
    }
    public void lookAtEntity(){
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityOtherPlayerMP) {
                if (mc.thePlayer.getDistanceSqToEntity(e) <= 1000.0f && e.isEntityAlive()) {
                    double deltaX = e.posX - mc.thePlayer.posX;
                    double deltaY = e.posY - mc.thePlayer.posY;
                    double deltaZ = e.posZ - mc.thePlayer.posZ;
                    double dist = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
                    mc.thePlayer.rotationYaw = (float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) - 90.0F;
                    mc.thePlayer.rotationPitch = (float) -(Math.atan2(deltaY, dist) * 180.0D / Math.PI);
                }
            }
        }
    }
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (Keybinds.pvp.isPressed())
        {
            pvpHandler.toggle();
            if(pvpHandler.isOn){
                mc.thePlayer.addChatMessage(new ChatComponentText("[PvPBot] activated"));
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
            }
            else {
                mc.thePlayer.addChatMessage(new ChatComponentText("[PvPBot] deactivated"));
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            }
        }
    }
}
