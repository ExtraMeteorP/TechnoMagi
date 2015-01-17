package com.ollieread.technomagi.event.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.KeyBindings;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageOpenGUI;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler
{

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (KeyBindings.toggleStaff.isPressed()) {
            Minecraft client = FMLClientHandler.instance().getClient();
            EntityPlayer player = client.thePlayer;
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            if (charon != null) {
                ItemStack heldItem = player.getHeldItem();

                if (heldItem == null || (heldItem.getItem() != null && heldItem.getItem() instanceof IStaff)) {
                    charon.syncStaff();
                }
            }
        } else if (KeyBindings.toggleCastMode.isPressed()) {
            Minecraft client = FMLClientHandler.instance().getClient();
            EntityPlayer player = client.thePlayer;
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            if (charon != null) {
                ItemStack heldItem = player.getHeldItem();

                if (heldItem == null || (heldItem.getItem() != null && heldItem.getItem() instanceof IStaff)) {
                    charon.abilities.setMode(charon.abilities.getMode() == 0 ? 1 : 0);
                    charon.abilities.syncCastMode();
                }
            }
        } else if (KeyBindings.diagnosis.isPressed()) {
            Minecraft client = FMLClientHandler.instance().getClient();
            EntityPlayer player = client.thePlayer;
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            if (charon != null && !charon.canSpecialise()) {
                PacketHandler.INSTANCE.sendToServer(new MessageOpenGUI(CommonProxy.GUI_PERSONAL_INTERFACE));
            }
        }
    }

}
