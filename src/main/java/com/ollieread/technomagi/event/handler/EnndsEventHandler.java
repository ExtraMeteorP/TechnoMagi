package com.ollieread.technomagi.event.handler;

import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.StringUtils;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.event.EnndsEvent.KnowledgeProgressEvent;
import com.ollieread.ennds.event.EnndsEvent.KnowledgeUnlockedEvent;
import com.ollieread.ennds.event.EnndsEvent.SpecialisationChosenEvent;
import com.ollieread.ennds.research.ResearchRegistry;
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
            EntityHelper.addTranslatedChatMessage(event.entityPlayer, "message.specialisation.chosen");
            EntityHelper.addTranslatedChatMessage(event.entityPlayer, "message.specialisation.chosen." + event.specialisation.getName());

            if (InventoryHelper.consumeInventoryItem(event.entityPlayer.inventory, new ItemStack(Items.itemTechnomageStaff, 1, 0))) {
                ItemStack staff = ItemStaff.resetNBT(new ItemStack(Items.itemTechnomageStaff, 1, 1));
                ItemStaff.setPlayer(staff, event.entityPlayer.getCommandSenderName());

                event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, staff);
            }

            AbilityRegistry.passiveAbilityEvent("specialisation", event, event.charon);
            ResearchRegistry.researchEvent("specialisation", event, event.charon, false);
        }
    }

    @SubscribeEvent
    public void onKnowledgeProgress(KnowledgeProgressEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            AbilityRegistry.passiveAbilityEvent("knowledgeProgress", event, event.charon);
            ResearchRegistry.researchEvent("knowledgeProgress", event, event.charon, false);
        }
    }

    @SubscribeEvent
    public void onKnowledgeUnlocked(KnowledgeUnlockedEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            EntityHelper.addTranslatedChatMessage(event.entityPlayer, "message.knowledge.unlocked");
            EntityHelper.addTranslatedChatMessage(event.entityPlayer, "message.knowledge.unlocked." + event.knowledge.getName());
            ResearchRegistry.researchEvent("knowledge" + StringUtils.capitalize(event.knowledge.getName()), event, event.charon, false);
        }
    }

}
