package com.ollieread.technomagi.common;

import net.minecraft.client.settings.KeyBinding;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;

import com.ollieread.technomagi.TechnoMagi;

import cpw.mods.fml.client.registry.ClientRegistry;

public class KeyBindings
{

    public static KeyBinding toggleStaff;
    public static KeyBinding toggleCastMode;
    public static KeyBinding diagnosis;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering key bindings");

        toggleStaff = new KeyBinding("key.toggleStaff", Keyboard.KEY_I, "key.categories.technomagi");
        toggleCastMode = new KeyBinding("key.toggleCastMode", Keyboard.KEY_M, "key.categories.technomagi");
        diagnosis = new KeyBinding("key.selfDiagnosisButton", Keyboard.KEY_U, "key.categories.technomagi");

        ClientRegistry.registerKeyBinding(toggleStaff);
        ClientRegistry.registerKeyBinding(toggleCastMode);
        ClientRegistry.registerKeyBinding(diagnosis);
    }

}
