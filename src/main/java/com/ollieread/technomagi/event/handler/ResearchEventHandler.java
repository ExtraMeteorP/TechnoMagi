package com.ollieread.technomagi.event.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.extended.ExtendedNanites;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.init.Research;
import com.ollieread.technomagi.util.EventHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

public class ResearchEventHandler
{

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(event.entityPlayer);

        if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
            ItemStack heldItem = event.entityPlayer.getHeldItem();

            if (heldItem != null && heldItem.getItem() != null) {
                if (Block.getBlockFromItem(heldItem.getItem()) == null) {
                    if (event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
                        if (heldItem.getItem() instanceof ItemFlintAndSteel) {
                            Block block = event.world.getBlock(event.x, event.y, event.z);

                            if (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.getOrientation(event.face))) {
                                ResearchRegistry.researchEvent(Research.itemToResearchMapping.get(heldItem), event, playerKnowledge, true);
                            }
                        } else {
                            for (Iterator<ItemStack> i = Research.itemToResearchMapping.keySet().iterator(); i.hasNext();) {
                                ItemStack key = i.next();

                                if (key.isItemEqual(heldItem)) {
                                    ResearchRegistry.researchEvent(Research.itemToResearchMapping.get(key), event, playerKnowledge, true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteractWithEntity(EntityInteractEvent event)
    {
        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(event.entityPlayer);

        if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
            ItemStack heldItem = event.entityPlayer.getHeldItem();

            if (heldItem != null && heldItem.getItem() != null) {
                String eventName = null;
                Class entityClass = null;

                if (event.target instanceof EntityAnimal) {
                    if (((EntityAnimal) event.target).isBreedingItem(heldItem)) {
                        entityClass = event.target.getClass();
                        eventName = EventHelper.entityBreed(entityClass);

                    } else if (event.target instanceof EntitySheep) {
                        if (((EntitySheep) event.target).isShearable(heldItem, event.target.worldObj, (int) event.target.posX, (int) event.target.posY, (int) event.target.posZ)) {
                            eventName = "shearedSheep";
                        }
                    }
                }

                if (eventName != null) {
                    ResearchRegistry.researchEvent(eventName, event, playerKnowledge, true);
                    ExtendedNanites nanites = ExtendedNanites.get(event.entityLiving);

                    if (nanites != null && nanites.getOwner() != null && nanites.getOwner().equals(event.entityPlayer.getCommandSenderName())) {
                        ResearchRegistry.researchMonitoring(entityClass, eventName, playerKnowledge, nanites);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerAttackedEntity(AttackEntityEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            EntityPlayer player = event.entityPlayer;
            EntityLivingBase entity = event.entityLiving;

            String eventName = EventHelper.entityAttacked(event.target.getClass());

            if (eventName != null) {
                ResearchRegistry.researchEvent(eventName, event, ExtendedPlayerKnowledge.get(player), true);
            }
        }
    }

    @SubscribeEvent
    public void onLivingSetTarget(LivingSetAttackTargetEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {

            if (event.target instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.target;
                ExtendedPlayerKnowledge knowledge = ExtendedPlayerKnowledge.get(player);
                ExtendedNanites nanites = ExtendedNanites.get(event.entityLiving);

                if (knowledge != null && !knowledge.canSpecialise()) {
                    String eventName = EventHelper.entityTargeted(event.entityLiving.getClass());

                    if (eventName != null) {
                        ResearchRegistry.researchEvent(eventName, event, knowledge, true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDestroyItem(PlayerDestroyItemEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            String eventName = EventHelper.itemBroke(event.original);

            ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(event.entityPlayer);

            if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
                ResearchRegistry.researchEvent("broke" + eventName, event, playerKnowledge, true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerSleep(PlayerSleepInBedEvent event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            ResearchRegistry.researchEvent("sleep", event, ExtendedPlayerKnowledge.get(event.entityPlayer), true);
        }
    }

    @SubscribeEvent
    public void onEnderTeleport(EnderTeleportEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            if (event.entityLiving instanceof EntityPlayer) {
                ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get((EntityPlayer) event.entityLiving);

                if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
                    ResearchRegistry.researchEvent("enderTeleport", event, playerKnowledge, true);
                }
            } else if (event.entityLiving instanceof EntityEnderman) {
                EntityEnderman enderman = (EntityEnderman) event.entityLiving;
                Set<EntityPlayer> players = ((WorldServer) enderman.worldObj).getEntityTracker().getTrackingPlayers(enderman);

                for (Iterator<EntityPlayer> i = players.iterator(); i.hasNext();) {
                    EntityPlayer player = i.next();
                    ExtendedPlayerKnowledge knowledge = ExtendedPlayerKnowledge.get(player);

                    if (knowledge != null && !knowledge.canSpecialise()) {
                        if (player.canEntityBeSeen(event.entityLiving)) {
                            ResearchRegistry.researchEvent("enderTeleportEnderman", event, knowledge, true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event)
    {

        if (event.entityLiving instanceof EntityAnimal) {
            EntityAnimal animal = (EntityAnimal) event.entityLiving;

            if (animal.getGrowingAge() == 6000) {
                double d = 5.0D;
                List entities = animal.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(animal.posX - d, animal.posY - d, animal.posZ - d, animal.posX + d, animal.posY + d, animal.posZ + d));

                for (Iterator i = entities.iterator(); i.hasNext();) {
                    EntityPlayer player = (EntityPlayer) i.next();

                    if (player != null) {
                        Class entityClass = animal.getClass();
                        String eventName = EventHelper.entityBirth(entityClass);

                        if (eventName != null && !eventName.isEmpty()) {
                            ExtendedPlayerKnowledge knowledge = ExtendedPlayerKnowledge.get(player);

                            if (knowledge != null && !knowledge.canSpecialise()) {
                                if (player.canEntityBeSeen(animal)) {
                                    ResearchRegistry.researchEvent(eventName, event, knowledge, true);
                                }

                                ExtendedNanites nanites = ExtendedNanites.get(event.entityLiving);

                                if (nanites != null && nanites.getOwner() != null && nanites.getOwner().equals(player.getCommandSenderName())) {
                                    ResearchRegistry.researchMonitoring(entityClass, "birth", ExtendedPlayerKnowledge.get(player), nanites);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!(event.entityLiving instanceof EntityPlayer)) {
            String monitoringName = null;
            String eventName = null;
            ExtendedNanites nanites = null;
            EntityPlayer player = null;
            Class entityClass = event.entityLiving.getClass();
            World world = event.entityLiving.worldObj;
            Set<Class> entities = ResearchRegistry.getMonitorableEntities();

            if (entityClass != null && entities.contains(entityClass)) {
                nanites = ExtendedNanites.get(event.entityLiving);

                if (nanites != null) {
                    player = nanites.getOwnerPlayer();

                    if (player != null) {

                        if (event.entityLiving instanceof EntityZombie || event.entityLiving instanceof EntitySkeleton) {
                            if (world.isDaytime() && event.entityLiving.isBurning() && world.canBlockSeeTheSky(MathHelper.floor_double(event.entityLiving.posX), MathHelper.floor_double(event.entityLiving.posY), MathHelper.floor_double(event.entityLiving.posZ))) {
                                monitoringName = "burningInSunlight";
                                eventName = EventHelper.entitySunlight(entityClass);
                            }
                        }
                    }
                }

                if (monitoringName != null) {
                    ResearchRegistry.researchMonitoring(entityClass, eventName, ExtendedPlayerKnowledge.get(player), nanites);
                }

                if (eventName != null) {
                    ResearchRegistry.researchEvent(eventName, event, ExtendedPlayerKnowledge.get(player), false);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCrafting(ItemCraftedEvent event)
    {
        if (!event.player.worldObj.isRemote) {
            ResearchRegistry.researchCrafting(event, ExtendedPlayerKnowledge.get(event.player));
            ResearchRegistry.researchEvent("crafting", event, ExtendedPlayerKnowledge.get(event.player), true);
        }
    }

    @SubscribeEvent
    public void onPlayerSmelting(ItemSmeltedEvent event)
    {
        if (!event.player.worldObj.isRemote) {
            ResearchRegistry.researchCrafting(event, ExtendedPlayerKnowledge.get(event.player));
            ResearchRegistry.researchEvent("smelting", event, ExtendedPlayerKnowledge.get(event.player), true);
        }
    }

    @SubscribeEvent
    public void onPlayerHarvest(HarvestDropsEvent event)
    {
        if (!event.world.isRemote && event.harvester != null) {
            ResearchRegistry.researchMining(event, ExtendedPlayerKnowledge.get(event.harvester));
        }
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerChangedDimensionEvent event)
    {
        if (event.player != null && !event.player.worldObj.isRemote) {
            ExtendedPlayerKnowledge knowledge = ExtendedPlayerKnowledge.get(event.player);

            if (knowledge != null && !knowledge.canSpecialise()) {
                ResearchRegistry.researchEvent("playerChangedDimension", event, knowledge, true);

                if (event.toDim == -1) {
                    ResearchRegistry.researchEvent("toNether", event, knowledge, true);
                } else if (event.toDim == 0) {
                    ResearchRegistry.researchEvent("toOverworld", event, knowledge, true);
                } else if (event.toDim == 1) {
                    ResearchRegistry.researchEvent("toEnd", event, knowledge, true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event)
    {
        if (!event.entity.worldObj.isRemote) {
            if (event.charge == 72000) {
                ResearchRegistry.researchEvent("fireArrow", event, ExtendedPlayerKnowledge.get((EntityPlayer) event.entityPlayer), true);
            }
        }
    }

    @SubscribeEvent
    public void onFallEvent(LivingFallEvent event)
    {
        if (!event.entity.worldObj.isRemote) {
            if (event.entity instanceof EntityPlayer) {
                AbilityRegistry.passiveAbilityEvent("fall", event, ExtendedPlayerKnowledge.get((EntityPlayer) event.entity));
                ResearchRegistry.researchEvent("fall", event, ExtendedPlayerKnowledge.get((EntityPlayer) event.entity), true);
            } else {
                Class entityClass = event.entityLiving.getClass();
                World world = event.entityLiving.worldObj;
                Set<Class> entities = ResearchRegistry.getMonitorableEntities();

                if (entityClass != null && entities.contains(entityClass)) {
                    ExtendedNanites nanites = ExtendedNanites.get(event.entityLiving);

                    if (nanites != null) {
                        EntityPlayer player = nanites.getOwnerPlayer();

                        if (player != null) {
                            ResearchRegistry.researchMonitoring(entityClass, "fall", ExtendedPlayerKnowledge.get(player), nanites);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            if (event.entityLiving instanceof EntityPlayer) {
                ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get((EntityPlayer) event.entityLiving);
                String eventName = EventHelper.damage(event.source, false);

                if (eventName != null) {
                    ResearchRegistry.researchEvent(eventName, event, charon, true);
                    AbilityRegistry.passiveAbilityEvent(eventName, event, charon);
                }
            } else {
                Class entityClass = event.entityLiving.getClass();

                if (entityClass != null && ResearchRegistry.getMonitorableEntities().contains(entityClass)) {
                    ExtendedNanites nanites = ExtendedNanites.get(event.entityLiving);

                    if (nanites != null) {
                        EntityPlayer player = nanites.getOwnerPlayer();

                        if (player != null) {
                            String eventName = EventHelper.damage(event.source, false);

                            if (eventName != null) {
                                ResearchRegistry.researchMonitoring(entityClass, eventName, ExtendedPlayerKnowledge.get(player), nanites);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            if (event.source instanceof EntityDamageSourceIndirect || event.source instanceof EntityDamageSource) {
                if (event.source.getEntity() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) event.source.getEntity();
                    EntityLivingBase entity = (EntityLivingBase) event.entityLiving;

                    if (player != null && entity != null) {
                        String eventName = EventHelper.entityKilled(entity.getClass());

                        if (eventName != null) {
                            ResearchRegistry.researchEvent(eventName, event, ExtendedPlayerKnowledge.get(player), true);
                        }
                    }
                }
            }
        }
    }

}
