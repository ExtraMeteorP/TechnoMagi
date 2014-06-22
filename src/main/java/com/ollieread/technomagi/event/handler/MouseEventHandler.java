package com.ollieread.technomagi.event.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;

import com.ollieread.technomagi.client.gui.GuiTMOverlay;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageAbility;
import com.ollieread.technomagi.player.PlayerKnowledge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MouseEventHandler
{

    @SubscribeEvent
    public void onMouseEvent(MouseEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (player.isSneaking() && event.dwheel != 0) {
            PlayerKnowledge charon = PlayerKnowledge.get(player);

            if (event.dwheel < 0) {
                charon.abilities.setNextAbility();
                PacketHandler.INSTANCE.sendToServer(new MessageAbility(1));
            } else {
                charon.abilities.setPreviousAbility();
                PacketHandler.INSTANCE.sendToServer(new MessageAbility(2));
            }

            GuiTMOverlay.highlightTicks = 256;
            GuiTMOverlay.shouldDisplay = true;
            event.setCanceled(true);
        }
    }

}
