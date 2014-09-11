package com.ollieread.technomagi.event.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.event.EnndsEvent.KnowledgeUnlockedEvent;
import com.ollieread.ennds.event.EnndsEvent.ResearchProgressEvent;
import com.ollieread.ennds.event.EnndsEvent.SpecialisationChosenEvent;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemStaff;
import com.ollieread.technomagi.util.InventoryHelper;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EnndsEventHandler
{

    @SubscribeEvent
    public void onSpecialisationChosen(SpecialisationChosenEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(event.entityPlayer);
            PlayerHelper.addTranslatedChatMessage(event.entityPlayer, "message.specialisation.chosen");
            PlayerHelper.addTranslatedChatMessage(event.entityPlayer, "message.specialisation.chosen." + event.specialisation.getName());

            if (InventoryHelper.consumeInventoryItem(event.entityPlayer.inventory, new ItemStack(Items.itemTechnomageStaff, 1, 0))) {
                ItemStack staff = ItemStaff.resetNBT(new ItemStack(Items.itemTechnomageStaff, 1, 1));
                ItemStaff.setPlayer(staff, event.entityPlayer.getCommandSenderName());

                event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, staff);
            }

            for (int i = 0; i < 32; i++) {
                EntityFX effect = new EntityPortalFX(event.entityPlayer.worldObj, event.entityPlayer.posX + (event.entityPlayer.worldObj.rand.nextDouble() - 0.5D) * (double) event.entityPlayer.width, event.entityPlayer.posY + event.entityPlayer.worldObj.rand.nextDouble() * (double) event.entityPlayer.height - 0.25D, event.entityPlayer.posZ + (event.entityPlayer.worldObj.rand.nextDouble() - 0.5D) * (double) event.entityPlayer.width, (event.entityPlayer.worldObj.rand.nextDouble() - 0.5D) * 2.0D, -event.entityPlayer.worldObj.rand.nextDouble(), (event.entityPlayer.worldObj.rand.nextDouble() - 0.5D) * 2.0D);
                effect.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

                Minecraft.getMinecraft().effectRenderer.addEffect(effect);
            }

            AbilityRegistry.passiveAbilityEvent("specialisation", event, charon);
        }
    }

    @SubscribeEvent
    public void onKnowledgeUnlocked(KnowledgeUnlockedEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            PlayerHelper.addTranslatedChatMessage(event.entityPlayer, "message.knowledge.unlocked");
            PlayerHelper.addTranslatedChatMessage(event.entityPlayer, "message.knowledge.unlocked." + event.knowledge.getName());
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
