package com.ollieread.technomagi.event.handler;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.event.EnndsEvent.KnowledgeUnlockedEvent;
import com.ollieread.ennds.event.EnndsEvent.ResearchProgressEvent;
import com.ollieread.ennds.event.EnndsEvent.SpecialisationChosenEvent;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemStaff;
import com.ollieread.technomagi.util.EntityHelper;
import com.ollieread.technomagi.util.InventoryHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EnndsEventHandler
{

    @SubscribeEvent
    public void onSpecialisationChosen(SpecialisationChosenEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(event.entityPlayer);
            EntityHelper.addTranslatedChatMessage(event.entityPlayer, "message.specialisation.chosen");
            EntityHelper.addTranslatedChatMessage(event.entityPlayer, "message.specialisation.chosen." + event.specialisation.getName());

            if (InventoryHelper.consumeInventoryItem(event.entityPlayer.inventory, new ItemStack(Items.itemTechnomageStaff, 1, 0))) {
                ItemStack staff = ItemStaff.resetNBT(new ItemStack(Items.itemTechnomageStaff, 1, 1));
                ItemStaff.setPlayer(staff, event.entityPlayer.getCommandSenderName());

                event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, staff);
            }

            AbilityRegistry.passiveAbilityEvent("specialisation", event, charon);
        }
    }

    @SubscribeEvent
    public void onKnowledgeUnlocked(KnowledgeUnlockedEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            EntityHelper.addTranslatedChatMessage(event.entityPlayer, "message.knowledge.unlocked");
            EntityHelper.addTranslatedChatMessage(event.entityPlayer, "message.knowledge.unlocked." + event.knowledge.getName());
        }
    }

    @SubscribeEvent
    public void onResearchProgress(ResearchProgressEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            AbilityRegistry.passiveAbilityEvent("researchProgress", event, event.charon);
        }
    }

}
