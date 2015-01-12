package com.ollieread.technomagi.event.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.ollieread.ennds.ability.AbilityPayload.AbilityUseType;
import com.ollieread.ennds.event.PlayerCastingEvent.Start;
import com.ollieread.ennds.extended.ExtendedNanites;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.block.IDigitalToolable;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemMobBrain;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;
import com.ollieread.technomagi.util.EntityHelper;
import com.ollieread.technomagi.util.TeleportHelper;
import com.ollieread.technomagi.util.VersionChecker;
import com.ollieread.technomagi.world.region.IRegionController;
import com.ollieread.technomagi.world.region.RegionManager;
import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;
import com.ollieread.technomagi.world.region.RegionPayload;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EntityEventHandler
{

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event)
    {
        if (!event.player.worldObj.isRemote && Config.versionCheck && !((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))) {
            new Thread(new VersionChecker((EntityPlayer) event.player)).start();
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(event.entityPlayer);

        if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
            ItemStack heldItem = event.entityPlayer.getHeldItem();

            if (heldItem != null && heldItem.getItem() != null && event.entityPlayer.getHeldItem().isItemEqual(new ItemStack(Items.itemDigitalTool))) {
                if (event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
                    Block block = event.world.getBlock(event.x, event.y, event.z);

                    if (block instanceof IDigitalToolable) {
                        if (((IDigitalToolable) block).onTooled(event.entityPlayer, event.world, event.x, event.y, event.z, event.entityPlayer.getHeldItem())) {
                            event.setCanceled(true);
                            event.entityPlayer.swingItem();
                        }
                    }
                }
            }
        }

        if (!event.entityPlayer.worldObj.isRemote) {
            int network = RegionManager.getNetworkForCoords((int) event.entityPlayer.posX, (int) event.entityPlayer.posZ);

            if (network > -1) {
                List<IRegionController> controllers = RegionManager.getControllers(RegionControllerType.INTERACTION, network);
                RegionPayload payload = new RegionPayload<PlayerInteractEvent>(event.entityPlayer, null, event);

                for (IRegionController controller : controllers) {
                    if (!event.isCanceled()) {
                        controller.perform(payload);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onCheckSpawn(CheckSpawn event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            int network = RegionManager.getNetworkForCoords((int) event.x, (int) event.z);

            if (network > -1) {
                List<IRegionController> controllers = RegionManager.getControllers(RegionControllerType.PRESENCE, network);
                RegionPayload payload = new RegionPayload<CheckSpawn>(event.entityLiving, null, event);

                for (IRegionController controller : controllers) {
                    if (!event.hasResult() || !event.getResult().equals(Result.DENY)) {
                        controller.perform(payload);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerStartCasting(Start event)
    {
        if (event.abilityCast.type.equals(AbilityUseType.FOCUS)) {

        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            int network = RegionManager.getNetworkForCoords((int) event.entityLiving.posX, (int) event.entityLiving.posZ);

            if (network > -1) {
                List<IRegionController> controllers = RegionManager.getControllers(RegionControllerType.DAMAGE, network);
                EntityLivingBase entity = null;
                EntityLivingBase target = event.entityLiving;

                if (event.source instanceof EntityDamageSource) {
                    Entity first = ((EntityDamageSource) event.source).getEntity();

                    if (first instanceof EntityLivingBase) {
                        entity = (EntityLivingBase) first;
                    }
                } else if (event.source instanceof EntityDamageSourceIndirect) {
                    Entity first = ((EntityDamageSourceIndirect) event.source).getEntity();

                    if (first instanceof EntityLivingBase) {
                        entity = (EntityLivingBase) first;
                    }
                }

                RegionPayload payload = new RegionPayload<LivingHurtEvent>(entity, target, event);

                for (IRegionController controller : controllers) {
                    if (!event.isCanceled()) {
                        controller.perform(payload);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            int network = RegionManager.getNetworkForCoords((int) event.entityLiving.posX, (int) event.entityLiving.posZ);

            if (network > -1) {
                List<IRegionController> controllers = RegionManager.getControllers(RegionControllerType.DAMAGE, network);
                EntityLivingBase entity = null;
                EntityLivingBase target = event.entityLiving;

                if (event.source instanceof EntityDamageSource) {
                    Entity first = ((EntityDamageSource) event.source).getEntity();

                    if (first instanceof EntityLivingBase) {
                        entity = (EntityLivingBase) first;
                    }
                } else if (event.source instanceof EntityDamageSourceIndirect) {
                    Entity first = ((EntityDamageSourceIndirect) event.source).getEntity();

                    if (first instanceof EntityLivingBase) {
                        entity = (EntityLivingBase) first;
                    }
                }

                RegionPayload payload = new RegionPayload<LivingHurtEvent>(entity, target, event);

                for (IRegionController controller : controllers) {
                    if (!event.isCanceled()) {
                        controller.perform(payload);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingSetTarget(LivingSetAttackTargetEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {

            if (event.target instanceof EntityPlayer) {
                ExtendedNanites nanites = ExtendedNanites.get(event.entityLiving);

                if (nanites != null && nanites.getOwner() != null) {
                    if (event.target instanceof EntityPlayer && event.target.getCommandSenderName().equals(nanites.getOwner())) {
                        if (event.entityLiving instanceof EntityLiving) {
                            ((EntityLiving) event.entityLiving).setAttackTarget(null);
                        }

                        if (event.entityLiving instanceof EntityEnderman) {
                            ((EntityEnderman) event.entityLiving).setScreaming(false);
                        }

                        event.entityLiving.setRevengeTarget(null);
                    }
                }
            }

            int network = RegionManager.getNetworkForCoords((int) event.entityLiving.posX, (int) event.entityLiving.posZ);

            if (network > -1) {
                List<IRegionController> controllers = RegionManager.getControllers(RegionControllerType.DAMAGE, network);
                RegionPayload payload = new RegionPayload<LivingHurtEvent>(event.entityLiving, event.target, event);

                for (IRegionController controller : controllers) {
                    if (!event.isCanceled()) {
                        controller.perform(payload);
                    } else {
                        break;
                    }
                }
            }
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
        if (!event.entityLiving.worldObj.isRemote) {
            if (EntityHelper.isStoodOnMeta(event.entityLiving, Blocks.blockTeleporter, 0)) {
                TileEntityTeleporter teleporter = (TileEntityTeleporter) EntityHelper.getTileEntityStoodOn(event.entityLiving);

                if (teleporter != null && teleporter.canUse(event.entityLiving)) {
                    TileEntityTeleporter destination = TeleportHelper.findTeleporterAbove(teleporter, event.entityLiving);

                    if (destination != null) {
                        TeleportHelper.teleportEntityToTeleporter(event.entityLiving, teleporter, destination);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event)
    {
        if (event.entity instanceof EntityLiving) {
            Set<Class> allowedEntities = ResearchRegistry.getMonitorableEntities();

            for (Iterator<Class> i = allowedEntities.iterator(); i.hasNext();) {
                Class c = i.next();
                if (c.isInstance(event.entity) && ExtendedNanites.get(event.entity) == null) {
                    ExtendedNanites.register(event.entity);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEnderTeleport(EnderTeleportEvent event)
    {
        World world = event.entityLiving.worldObj;

        int startX = (int) (event.targetX - 7);
        int endX = (int) (event.targetX + 7);
        int startY = (int) (event.targetY - 7);
        int endY = (int) (event.targetY + 7);
        int startZ = (int) (event.targetZ - 7);
        int endZ = (int) (event.targetZ + 7);

        for (int i = startX; i <= endX; i++) {
            for (int j = startZ; j <= endZ; j++) {
                for (int k = startY; k <= endY; k++) {
                    if (world.getBlock(i, k, j).equals(Blocks.blockTeleporter) && world.getBlockMetadata(i, k, j) == 2) {
                        TileEntityTeleporter teleporter = (TileEntityTeleporter) world.getTileEntity(i, k, j);

                        if (teleporter.canUse(event.entityLiving)) {
                            event.targetX = i;
                            event.targetY = k + 1;
                            event.targetZ = j;

                            return;
                        }
                    } else if (world.getBlock(i, k, j).equals(Blocks.blockTeleporter) && world.getBlockMetadata(i, k, j) == 3) {
                        TileEntityTeleporter teleporter = (TileEntityTeleporter) world.getTileEntity(i, k, j);

                        if (teleporter.canUse(event.entityLiving)) {
                            event.setCanceled(true);

                            return;
                        }
                    }
                }
            }
        }

        int startX1 = (int) (event.entityLiving.posX - 7);
        int endX1 = (int) (event.entityLiving.posX + 7);
        int startY1 = (int) (event.entityLiving.posY - 7);
        int endY1 = (int) (event.entityLiving.posY + 7);
        int startZ1 = (int) (event.entityLiving.posZ - 7);
        int endZ1 = (int) (event.entityLiving.posZ + 7);

        for (int i = startX1; i <= endX1; i++) {
            for (int j = startZ1; j <= endZ1; j++) {
                for (int k = startY1; k <= endY1; k++) {
                    if (world.getBlock(i, k, j).equals(Blocks.blockTeleporter) && world.getBlockMetadata(i, k, j) == 3) {
                        TileEntityTeleporter teleporter = (TileEntityTeleporter) world.getTileEntity(i, k, j);

                        if (teleporter.canUse(event.entityLiving)) {
                            event.setCanceled(true);

                            return;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            Class entityClass = event.entityLiving.getClass();

            if (ResearchRegistry.getBrainableEntities().contains(entityClass)) {
                if (event.source instanceof EntityDamageSource && ((EntityDamageSource) event.source).getEntity() instanceof EntityPlayer) {
                    if (event.entityLiving.worldObj.rand.nextInt(Config.brainDropChance) == 0) {
                        ItemStack stack = new ItemStack(Items.itemMobBrain, 1);
                        ((ItemMobBrain) Items.itemMobBrain).setEntity(stack, entityClass);

                        EntityItem entity = new EntityItem(event.entityLiving.worldObj);
                        entity.setEntityItemStack(stack);
                        event.drops.add(entity);
                    }
                }
            }
        }
    }

}
