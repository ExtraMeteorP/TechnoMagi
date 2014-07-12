package com.ollieread.technomagi.event.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;

import com.ollieread.ennds.common.PacketHelper;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.client.gui.GuiTMOverlay;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MouseEventHandler
{

    @SubscribeEvent
    public void onMouseEvent(MouseEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (player.isSneaking() && event.dwheel != 0) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            if (event.dwheel < 0) {
                charon.abilities.setNextAbility();
                PacketHelper.abilityPacket(1);
            } else {
                charon.abilities.setPreviousAbility();
                PacketHelper.abilityPacket(2);
            }

            GuiTMOverlay.highlightTicks = 256;
            GuiTMOverlay.shouldDisplay = true;
            event.setCanceled(true);
        }
    }

}
