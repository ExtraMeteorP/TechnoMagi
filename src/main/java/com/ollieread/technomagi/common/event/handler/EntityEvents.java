package com.ollieread.technomagi.common.event.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import thaumcraft.codechicken.lib.math.MathHelper;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.event.TechnomagiEvent;
import com.ollieread.technomagi.common.block.teleporter.tile.TileElevator;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.common.knowledge.Arcane;
import com.ollieread.technomagi.common.knowledge.Existence;
import com.ollieread.technomagi.common.knowledge.Nanites;
import com.ollieread.technomagi.common.knowledge.Organics;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.EntityHelper;
import com.ollieread.technomagi.util.TeleportHelper;

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

            if (EntityHelper.isStoodOn(event.player, Blocks.elevator) && event.player.isSneaking()) {
                int x = MathHelper.floor_double(event.player.posX);
                int y = MathHelper.floor_double(event.player.posY);
                int z = MathHelper.floor_double(event.player.posZ);

                TileElevator elevator = (TileElevator) event.player.worldObj.getTileEntity(x, y, z);

                if (!elevator.isOnCooldown()) {
                    TeleportHelper.teleportToElevatorBelow(event.player, x, y, z, true);
                }
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

    @SubscribeEvent
    public void onJump(LivingJumpEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            if (EntityHelper.isStoodOn(event.entityLiving, Blocks.elevator)) {
                int x = MathHelper.floor_double(event.entityLiving.posX);
                int y = MathHelper.floor_double(event.entityLiving.posY);
                int z = MathHelper.floor_double(event.entityLiving.posZ);

                TileElevator elevator = (TileElevator) event.entityLiving.worldObj.getTileEntity(x, y, z);

                if (!elevator.isOnCooldown()) {
                    TeleportHelper.teleportToElevatorAbove(event.entityLiving, x, y, z, true);
                }
            }
        }
    }
}
