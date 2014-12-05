package com.ollieread.technomagi.event.handler;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent.Finish;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import org.apache.commons.lang3.StringUtils;

import com.ollieread.ennds.ability.AbilityRegistry;
import com.ollieread.ennds.extended.ExtendedNanites;
import com.ollieread.ennds.extended.ExtendedPlayerAbilities;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.block.IDigitalToolable;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.init.Potions;
import com.ollieread.technomagi.common.init.Research;
import com.ollieread.technomagi.item.ItemStaff;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageEntityInteractEvent;
import com.ollieread.technomagi.network.message.MessagePlayerInteractEvent;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;
import com.ollieread.technomagi.util.EntityHelper;
import com.ollieread.technomagi.util.SoundHelper;
import com.ollieread.technomagi.util.TeleportHelper;
import com.ollieread.technomagi.util.VersionChecker;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerEventHandler
{

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event)
    {
        if (!event.player.worldObj.isRemote && Config.versionCheck) {
            new Thread(new VersionChecker((EntityPlayer) event.player)).start();
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(event.entityPlayer);

        if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
            ItemStack heldItem = event.entityPlayer.getHeldItem();

            if (!event.action.equals(PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) && heldItem != null && heldItem.getItem() != null && heldItem.getItem() instanceof IStaff) {
                ExtendedPlayerAbilities abilities = playerKnowledge.abilities;
                event.useBlock = Event.Result.DENY;
                event.useItem = Event.Result.DENY;

                if (abilities.useAbility(event, heldItem)) {
                    event.entityPlayer.swingItem();

                    if (!event.entityPlayer.worldObj.isRemote) {
                        SoundHelper.playSoundEffectAtPlayer(event.entityPlayer, "cast", new Random());
                    } else {
                        if (event.isCanceled()) {
                            PacketHandler.INSTANCE.sendToServer(new MessagePlayerInteractEvent(event));
                        }
                    }
                } else {
                    if (!event.entityPlayer.worldObj.isRemote) {
                        SoundHelper.playSoundEffectAtPlayer(event.entityPlayer, "fail", new Random());
                    }
                }
            } else if (heldItem != null && heldItem.getItem() != null && event.entityPlayer.getHeldItem().isItemEqual(new ItemStack(Items.itemDigitalTool))) {
                if (event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
                    Block block = event.world.getBlock(event.x, event.y, event.z);

                    if (block instanceof IDigitalToolable) {
                        if (((IDigitalToolable) block).onTooled(event.entityPlayer, event.world, event.x, event.y, event.z, event.entityPlayer.getHeldItem())) {
                            event.setCanceled(true);
                            event.entityPlayer.swingItem();
                        }
                    }
                }
            } else if (heldItem != null && heldItem.getItem() != null) {
                ItemStack held = event.entityPlayer.getHeldItem();

                if (Block.getBlockFromItem(held.getItem()) == null) {
                    if (event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
                        if (held.getItem() instanceof ItemFlintAndSteel) {
                            Block block = event.world.getBlock(event.x, event.y, event.z);

                            if (block.isFlammable(event.world, event.x, event.y, event.z, ForgeDirection.getOrientation(event.face))) {
                                ResearchRegistry.researchEvent("useFlintAndSteel", event, ExtendedPlayerKnowledge.get(event.entityPlayer), true);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(event.entityPlayer);

        if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
            ItemStack heldItem = event.entityPlayer.getHeldItem();

            if (heldItem != null && heldItem.getItem() != null) {
                if (heldItem.getItem() instanceof IStaff) {
                    ExtendedPlayerAbilities abilities = playerKnowledge.abilities;

                    if (abilities.useAbility(event, heldItem)) {
                        event.entityPlayer.swingItem();
                        if (event.entityPlayer.worldObj.isRemote && event.isCanceled()) {
                            PacketHandler.INSTANCE.sendToServer(new MessageEntityInteractEvent(event));
                        }
                    } else {
                        if (!event.entityPlayer.worldObj.isRemote) {
                            SoundHelper.playSoundEffectAtPlayer(event.entityPlayer, "fail", new Random());
                        }
                    }
                } else if (event.target instanceof EntityAnimal) {
                    if (((EntityAnimal) event.target).isBreedingItem(heldItem)) {
                        Class entityClass = event.target.getClass();
                        String entityName = (String) EntityList.classToStringMapping.get(entityClass);

                        if (entityName != null && !entityName.isEmpty()) {
                            ResearchRegistry.researchEvent("breeding" + StringUtils.capitalize(entityName), event, playerKnowledge, true);
                        }
                    } else if (event.target instanceof EntitySheep) {
                        if (((EntitySheep) event.target).isShearable(heldItem, event.target.worldObj, (int) event.target.posX, (int) event.target.posY, (int) event.target.posZ)) {
                            ResearchRegistry.researchEvent("shearedSheep", event, playerKnowledge, true);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            EntityPlayer player = event.entityPlayer;
            EntityLivingBase entity = event.entityLiving;

            String entityName = (String) EntityList.classToStringMapping.get(entity.getClass());

            if (entityName != null) {
                ResearchRegistry.researchEvent("attacked" + StringUtils.capitalize(entityName), event, ExtendedPlayerKnowledge.get(player), true);
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            String researchName = null;
            String damageName = null;
            EntityPlayer player = null;

            if (event.entityLiving instanceof EntityPlayer) {
                player = (EntityPlayer) event.entityLiving;
                String entityName = null;

                if (event.source instanceof EntityDamageSourceIndirect || event.source instanceof EntityDamageSource) {
                    if (event.source.getEntity() instanceof EntityLivingBase) {
                        EntityLivingBase entity = (EntityLivingBase) event.source.getEntity();
                        entityName = (String) EntityList.classToStringMapping.get(entity.getClass());
                    }
                }

                damageName = event.source.damageType;

                if (entityName != null && !entityName.isEmpty()) {
                    researchName = "attackedBy" + StringUtils.capitalize(entityName);
                }
            } else {
                EntityLivingBase entity = null;
                if (event.source instanceof EntityDamageSourceIndirect || event.source instanceof EntityDamageSource) {
                    if (event.source.getEntity() instanceof EntityLivingBase) {
                        entity = (EntityLivingBase) event.source.getEntity();
                    }
                }

                damageName = event.source.damageType;

                if (entity != null && entity instanceof EntityPlayer) {
                    EntityLivingBase subject = event.entityLiving;
                    String entityName = (String) EntityList.classToStringMapping.get(entity.getClass());

                    if (entityName != null && !entityName.isEmpty()) {
                        researchName = "attacked" + StringUtils.capitalize(entityName);
                    }
                }
            }

            if (player != null && researchName != null) {
                ResearchRegistry.researchEvent(researchName, event, ExtendedPlayerKnowledge.get(player), true);
                AbilityRegistry.passiveAbilityEvent(researchName, event, ExtendedPlayerKnowledge.get(player));

                if (damageName != null) {
                    ResearchRegistry.researchEvent(researchName + StringUtils.capitalize(damageName), event, ExtendedPlayerKnowledge.get(player), true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            if (event.entityLiving instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;
                ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

                if (event.source instanceof EntityDamageSource || event.source instanceof EntityDamageSourceIndirect) {
                    if (event.source.equals(DamageSource.generic) || event.source.equals(DamageSource.anvil) || event.source.equals(DamageSource.cactus) || event.source.equals(DamageSource.fall) || event.source.equals(DamageSource.fallingBlock)) {
                        if (player.isPotionActive(Potions.potionHardness)) {
                            event.ammount = event.ammount / 2;
                        }
                    }
                } else {
                    if (event.source.isExplosion()) {
                        ResearchRegistry.researchEvent("damageExplosion", event, ExtendedPlayerKnowledge.get(player), true);
                        AbilityRegistry.passiveAbilityEvent("damageExplosion", event, ExtendedPlayerKnowledge.get(player));
                    } else {
                        ResearchRegistry.researchEvent("damage" + StringUtils.capitalize(event.source.damageType), event, ExtendedPlayerKnowledge.get(player), true);
                        AbilityRegistry.passiveAbilityEvent("damage" + StringUtils.capitalize(event.source.damageType), event, ExtendedPlayerKnowledge.get(player));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            String researchName = null;
            EntityLivingBase entity = null;
            EntityPlayer player = null;

            if (event.entityLiving instanceof EntityPlayer) {
                player = (EntityPlayer) event.entityLiving;

                ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(player);

                if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
                    InventoryPlayer inventory = player.inventory;
                    ItemStack staff = null;
                    int slot = -1;

                    for (int i = 0; i < inventory.getSizeInventory(); i++) {
                        ItemStack stack = inventory.getStackInSlot(i);

                        if (stack != null && stack.getItem() != null && stack.getItem() instanceof IStaff) {
                            if (stack.getItemDamage() == 1) {
                                staff = stack;
                                slot = i;
                                break;
                            }
                        }
                    }

                    if (staff != null) {
                        String playerName = ItemStaff.getPlayer(staff);

                        if (player != null && player.equals(player.getCommandSenderName())) {
                            playerKnowledge.setStaff(staff);
                            inventory.setInventorySlotContents(slot, null);
                        }
                    }
                }

                if (event.source instanceof EntityDamageSourceIndirect || event.source instanceof EntityDamageSource) {
                    entity = (EntityLivingBase) event.source.getEntity();

                    if (entity != null) {
                        String entityName = (String) EntityList.classToStringMapping.get(entity.getClass());

                        if (entityName != null && !entityName.isEmpty()) {
                            researchName = "killedBy" + StringUtils.capitalize(entityName);
                        }
                    }
                } else if (event.source instanceof DamageSource && event.source.equals(DamageSource.fall)) {
                    researchName = "killedByGravity";
                }
            } else {
                if (event.source instanceof EntityDamageSourceIndirect || event.source instanceof EntityDamageSource) {
                    if (event.source.getEntity() instanceof EntityPlayer) {
                        player = (EntityPlayer) event.source.getEntity();
                        entity = (EntityLivingBase) event.entityLiving;

                        if (entity != null) {
                            String entityName = (String) EntityList.classToStringMapping.get(entity.getClass());

                            if (entityName != null && !entityName.isEmpty()) {
                                researchName = "killed" + StringUtils.capitalize(entityName);
                            }
                        }
                    }
                }
            }

            if (researchName != null && !researchName.isEmpty() && player != null) {
                ResearchRegistry.researchEvent(researchName, event, ExtendedPlayerKnowledge.get(player), true);
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
                String entityName = (String) EntityList.classToStringMapping.get(entityClass);
                World world = event.entityLiving.worldObj;
                Set<Class> entities = ResearchRegistry.getMonitorableEntities();

                if (entityName != null && entities.contains(entityClass)) {
                    ExtendedNanites nanites = ExtendedNanites.get(event.entityLiving);

                    if (nanites != null) {
                        EntityPlayer player = nanites.getOwnerPlayer();

                        if (player != null) {
                            ResearchRegistry.researchMonitoring("fall" + entityName, event, ExtendedPlayerKnowledge.get(player), nanites);
                        }
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
    public void onPlayerCrafting(ItemCraftedEvent event)
    {
        if (!event.player.worldObj.isRemote) {
            ResearchRegistry.researchCrafting(event, ExtendedPlayerKnowledge.get(event.player));
        }
    }

    @SubscribeEvent
    public void onPlayerSmelting(ItemSmeltedEvent event)
    {
        if (!event.player.worldObj.isRemote) {
            ResearchRegistry.researchCrafting(event, ExtendedPlayerKnowledge.get(event.player));
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
    public void onEnderTeleport(EnderTeleportEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote) {
            if (event.entityLiving instanceof EntityPlayer) {
                ResearchRegistry.researchEvent("enderTeleport", event, ExtendedPlayerKnowledge.get((EntityPlayer) event.entityLiving), true);
            } else if (event.entityLiving instanceof EntityEnderman) {
                EntityEnderman enderman = (EntityEnderman) event.entityLiving;
                Set<EntityPlayer> players = ((WorldServer) enderman.worldObj).getEntityTracker().getTrackingPlayers(enderman);

                for (Iterator<EntityPlayer> i = players.iterator(); i.hasNext();) {
                    EntityPlayer player = i.next();
                    ExtendedPlayerKnowledge knowledge = ExtendedPlayerKnowledge.get(player);

                    if (knowledge != null && !knowledge.canSpecialise()) {
                        ResearchRegistry.researchEvent("enderTeleportEnderman", event, knowledge, true);
                    }
                }
            }
        }

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
    public void onPlayerUseItem(Finish event)
    {
        if (!event.entityPlayer.worldObj.isRemote) {
            for (Iterator<ItemStack> i = Research.itemToResearchMapping.keySet().iterator(); i.hasNext();) {
                ItemStack key = i.next();

                if (key.isItemEqual(event.item)) {
                    ResearchRegistry.researchEvent(Research.itemToResearchMapping.get(key), event, ExtendedPlayerKnowledge.get(event.entityPlayer), true);
                }
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

}
