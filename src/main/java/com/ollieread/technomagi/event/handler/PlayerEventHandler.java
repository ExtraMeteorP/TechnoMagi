package com.ollieread.technomagi.event.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.api.research.ResearchEvents;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Potions;
import com.ollieread.technomagi.extended.ExtendedNanites;
import com.ollieread.technomagi.extended.ExtendedPlayerAbilities;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageEntityInteractEvent;
import com.ollieread.technomagi.network.message.MessagePlayerInteractEvent;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;
import com.ollieread.technomagi.util.PlayerHelper;
import com.ollieread.technomagi.util.TeleportHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class PlayerEventHandler
{

    protected List<Class> allowedEntities = new ArrayList<Class>(Arrays.asList(EntityCow.class, EntitySheep.class, EntityPig.class, EntityChicken.class, EntityCreeper.class, EntityZombie.class, EntityPigZombie.class, EntityVillager.class, EntityEnderman.class));

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayer) {
            if (ExtendedPlayerKnowledge.get((EntityPlayer) event.entity) == null) {
                ExtendedPlayerKnowledge.register((EntityPlayer) event.entity);
            }
            if (ExtendedPlayerAbilities.get((EntityPlayer) event.entity) == null) {
                ExtendedPlayerAbilities.register((EntityPlayer) event.entity);
            }
            if (ExtendedNanites.get((EntityPlayer) event.entity) == null) {
                ExtendedNanites.register((EntityPlayer) event.entity);
            }
        } else {
            if (allowedEntities.contains(event.entity.getClass())) {
                ExtendedNanites.register(event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            ExtendedPlayerKnowledge.saveProxyData((EntityPlayer) event.entity);
            ExtendedPlayerAbilities.saveProxyData((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onClone(Clone event)
    {
        if (!event.entity.worldObj.isRemote && event.original instanceof EntityPlayer && !event.wasDeath) {
            ExtendedPlayerKnowledge.saveProxyData((EntityPlayer) event.original);
            ExtendedPlayerAbilities.saveProxyData((EntityPlayer) event.original);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            ExtendedPlayerKnowledge.loadProxyData((EntityPlayer) event.entity);
            ExtendedPlayerAbilities.loadProxyData((EntityPlayer) event.entity);

            World world = event.entity.worldObj;

            if (world.provider.dimensionId != 0) {
                if (world.provider.dimensionId == -1) {
                    TMRegistry.researchEvent(ResearchEvents.EVENT_TO_NETHER, event, ExtendedPlayerKnowledge.get((EntityPlayer) event.entity));
                } else if (world.provider.dimensionId == 1) {
                    TMRegistry.researchEvent(ResearchEvents.EVENT_TO_END, event, ExtendedPlayerKnowledge.get((EntityPlayer) event.entity));
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.entityPlayer.getHeldItem() == null) {
            ExtendedPlayerAbilities abilities = ExtendedPlayerAbilities.get((EntityPlayer) event.entity);

            if (abilities.useAbility(event)) {
                if (event.entityPlayer.worldObj.isRemote && (event.isCanceled() || event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_AIR))) {
                    PacketHandler.INSTANCE.sendToServer(new MessagePlayerInteractEvent(event));
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        if (event.entityPlayer.getHeldItem() == null) {
            ExtendedPlayerAbilities abilities = ExtendedPlayerAbilities.get((EntityPlayer) event.entity);

            if (abilities.useAbility(event)) {
                if (event.entityPlayer.worldObj.isRemote && event.isCanceled()) {
                    PacketHandler.INSTANCE.sendToServer(new MessageEntityInteractEvent(event));
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;

            if (event.source instanceof EntityDamageSource || event.source.equals(DamageSource.generic) || event.source.equals(DamageSource.anvil) || event.source.equals(DamageSource.cactus) || event.source.equals(DamageSource.fall) || event.source.equals(DamageSource.fallingBlock)) {
                if (player.isPotionActive(Potions.potionHardness)) {
                    event.ammount = event.ammount / 2;
                }
            }

            if (event.source.equals(DamageSource.inFire)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_IN_FIRE, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_IN_FIRE, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.onFire)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_ON_FIRE, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_ON_FIRE, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.lava)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_IN_LAVA, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_IN_LAVA, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.inWall)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_IN_WALL, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_IN_WALL, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.starve)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_STARVE, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_STARVE, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.cactus)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_CACTUS, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_CACTUS, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.outOfWorld)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_VOID, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_VOID, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.magic)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_MAGIC, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_MAGIC, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.wither)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_WITHER, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_WITHER, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.anvil)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_ANVIL, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_ANVIL, event, ExtendedPlayerKnowledge.get(player));
            } else if (event.source.equals(DamageSource.fallingBlock)) {
                TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_FALLING_BLOCK, event, ExtendedPlayerKnowledge.get(player));
                TMRegistry.researchEvent(ResearchEvents.EVENT_FALLING_BLOCK, event, ExtendedPlayerKnowledge.get(player));
            }
        }
    }

    @SubscribeEvent
    public void onFallEvent(LivingFallEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            TMRegistry.passiveAbilityEvent(ResearchEvents.EVENT_FALL, event, ExtendedPlayerKnowledge.get((EntityPlayer) event.entity));
            TMRegistry.researchEvent(ResearchEvents.EVENT_FALL, event, ExtendedPlayerKnowledge.get((EntityPlayer) event.entity));
        }
    }

    @SubscribeEvent
    public void onStartTrackingEvent(StartTracking event)
    {
        if (!event.entityPlayer.worldObj.isRemote && event.target instanceof EntityPlayer) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get((EntityPlayer) event.target);

            charon.syncTo(event.entityPlayer);
        }
    }

    @SubscribeEvent
    public void onLivingJumpEvent(LivingJumpEvent event)
    {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (PlayerHelper.isStoodOn(player, Blocks.blockTeleporter)) {
                TileEntityTeleporter teleporter = (TileEntityTeleporter) PlayerHelper.getTileEntityStoodOn(player);

                if (teleporter != null && teleporter.canUse()) {
                    TileEntityTeleporter destination = TeleportHelper.findTeleporterAbove(teleporter);

                    if (destination != null) {
                        TeleportHelper.teleportPlayerToTeleporter(player, teleporter, destination);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCrafting(ItemCraftedEvent event)
    {
        if (!event.player.worldObj.isRemote) {
            TMRegistry.researchCrafting(event, ExtendedPlayerKnowledge.get(event.player));
        }
    }

    @SubscribeEvent
    public void onPlayerHarvest(HarvestDropsEvent event)
    {
        if (!event.world.isRemote && event.harvester != null) {
            TMRegistry.researchMining(event, ExtendedPlayerKnowledge.get(event.harvester));
        }
    }
}
