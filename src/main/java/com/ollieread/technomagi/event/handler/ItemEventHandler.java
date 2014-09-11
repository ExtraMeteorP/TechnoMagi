package com.ollieread.technomagi.event.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.technomagi.item.ItemStaff;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemEventHandler
{

    @SubscribeEvent
    public void onItemToss(ItemTossEvent event)
    {
        if (event.player != null && !event.player.worldObj.isRemote) {
            ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(event.player);

            if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
                ItemStack stack = event.entityItem.getEntityItem();

                if (stack != null && stack.getItem() != null && stack.getItem() instanceof IStaff) {
                    if (stack.getItemDamage() == 1) {
                        String player = ItemStaff.getPlayer(stack);

                        if (player != null && player.equals(event.player.getCommandSenderName())) {
                            playerKnowledge.setStaff(stack);
                            event.setCanceled(true);
                            event.entityItem.setDead();
                        }
                    }
                }
            }
        }
    }
}
