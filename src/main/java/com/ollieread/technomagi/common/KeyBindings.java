package com.ollieread.technomagi.common;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.ClientRegistry;

public class KeyBindings
{

    public static KeyBinding tmGui;

    public static void init()
    {
        tmGui = new KeyBinding("key.tmGui", Keyboard.KEY_I, "key.categories.technomagi");

        ClientRegistry.registerKeyBinding(tmGui);
    }

}
