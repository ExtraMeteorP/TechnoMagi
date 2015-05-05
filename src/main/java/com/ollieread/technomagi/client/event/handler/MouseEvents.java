package com.ollieread.technomagi.client.event.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.ability.IAbilityItem;
import com.ollieread.technomagi.api.ability.PlayerAbilities;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.GuiTechnomagi;
import com.ollieread.technomagi.common.network.PacketHandler;
import com.ollieread.technomagi.common.network.packets.MessageReleaseCasting;
import com.ollieread.technomagi.util.PacketHelper;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MouseEvents
{

    @SubscribeEvent
    public void onMouseEvent(MouseEvent event)
    {
        EntityPlayer player = Technomagi.proxy.getClientPlayer();

        int dwheel = event.dwheel;

        if (GuiBuilder.instance.mc.currentScreen == null) {
            if (player.isSneaking() && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IAbilityItem && dwheel != 0) {
                PlayerTechnomagi technomage = PlayerHelper.getTechnomagi(player);

                if (dwheel < 0) {
                    technomage.abilities().setNextAbility();
                    GuiTechnomagi.highlightTicks = 256;
                    PacketHelper.changeAbility(1);
                } else {
                    technomage.abilities().setPreviousAbility();
                    GuiTechnomagi.highlightTicks = 256;
                    PacketHelper.changeAbility(2);
                }

                // GuiTMOverlay.highlightTicks = 256;
                // GuiTMOverlay.shouldDisplay = true;
                Technomagi.proxy.updateOverlay();
                event.setCanceled(true);
            }
        }

        if (event.button == 1 && !event.buttonstate) {
            PlayerAbilities abilities = PlayerHelper.getAbilities(player);

            if (abilities != null && abilities.isCasting()) {
                abilities.releaseCasting();
                PacketHandler.INSTANCE.sendToServer(new MessageReleaseCasting());
            }
        }
    }

}
