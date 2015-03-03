package com.ollieread.technomagi.common.event.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.event.TechnomagiEvent;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.common.knowledge.Arcane;
import com.ollieread.technomagi.common.knowledge.Existence;
import com.ollieread.technomagi.common.knowledge.Nanites;
import com.ollieread.technomagi.common.knowledge.Organics;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.common.knowledge.Technomancy;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EntityEvents
{

    @SubscribeEvent
    public void playerTick(PlayerTickEvent event)
    {
        if (event.phase.equals(TickEvent.Phase.END)) {
            PlayerTechnomagi technomage = PlayerTechnomagi.get(event.player);

            if (technomage != null) {
                technomage.update(event.side);
            }
        }
    }

    @SubscribeEvent
    public void stoppedUsingItem(PlayerUseItemEvent.Stop event)
    {
        // TODO play sound
    }

    @SubscribeEvent
    public void finishedUsingItem(PlayerUseItemEvent.Finish event)
    {

    }

    @SubscribeEvent
    public void onSpecialise(TechnomagiEvent.Specialise event)
    {
        EntityPlayer player = event.entityPlayer;

        if (!player.worldObj.isRemote) {
            event.playerTechnomagi.knowledge().addKnowledge(Technomancy.basicTechnomancy.getName());
            event.playerTechnomagi.knowledge().addKnowledge(Resources.basicResources.getName());

            if (Config.multiplayerMode) {
                if (event.specialisation.equals(Specialisations.cleric)) {
                    event.playerTechnomagi.knowledge().addKnowledge(Organics.basicOrganics.getName());
                } else if (event.specialisation.equals(Specialisations.engineer)) {
                    event.playerTechnomagi.knowledge().addKnowledge(Technomancy.basicMachines.getName());
                } else if (event.specialisation.equals(Specialisations.guardian)) {
                    event.playerTechnomagi.knowledge().addKnowledge(Existence.basicExistence.getName());
                } else if (event.specialisation.equals(Specialisations.scholar)) {
                    event.playerTechnomagi.knowledge().addKnowledge(Nanites.basicNanites.getName());
                } else if (event.specialisation.equals(Specialisations.shadow)) {
                    event.playerTechnomagi.knowledge().addKnowledge(Arcane.basicArcane.getName());
                }
            } else {

            }
        }
    }
}
