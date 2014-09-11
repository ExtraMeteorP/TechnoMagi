package com.ollieread.technomagi.event.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
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

            if (charon != null) {
                ItemStack heldItem = player.getHeldItem();

                if (heldItem == null || (heldItem.getItem() != null && heldItem.getItem() instanceof IStaff)) {
                    charon.syncStaff();
                }
            }
        }
    }

}
