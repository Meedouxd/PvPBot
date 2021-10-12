package com.m33dz.pvpbot;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds
{
    public static KeyBinding pvp;

    public static void register() {
        pvp = new KeyBinding("Toggle Bot", Keyboard.KEY_F, "PvPBot");
        ClientRegistry.registerKeyBinding(pvp);
    }
}
