package com.ollieread.technomagi.event.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.KeyBindings;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler
{

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeyBindings.tmGui.isPressed()) {
            Minecraft client = FMLClientHandler.instance().getClient();
            EntityPlayer player = client.thePlayer;
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            int id = CommonProxy.GUI_TECHNOMAGI;

            player.openGui(TechnoMagi.instance, id, client.theWorld, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
    }

}
