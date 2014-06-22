package com.ollieread.technomagi.common;

import net.minecraft.client.settings.KeyBinding;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;

import com.ollieread.technomagi.TechnoMagi;

import cpw.mods.fml.client.registry.ClientRegistry;

public class KeyBindings
{

    public static KeyBinding tmGui;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering key bindings");

        tmGui = new KeyBinding("key.tmGui", Keyboard.KEY_I, "key.categories.technomagi");

        ClientRegistry.registerKeyBinding(tmGui);
    }

}
