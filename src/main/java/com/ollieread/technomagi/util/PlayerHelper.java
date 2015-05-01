package com.ollieread.technomagi.util;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.PlayerAbilities;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.event.TechnomagiHooks;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.PlayerKnowledge;
import com.ollieread.technomagi.api.nanites.PlayerNanites;
import com.ollieread.technomagi.api.specialisation.Specialisation;

public class PlayerHelper
{

    /**
     * Get the Technomagi instance for the player.
     *
     * @param player
     * @return
     */
    public static PlayerTechnomagi getTechnomagi(EntityPlayer player)
    {
        return PlayerTechnomagi.get(player);
    }

    public static PlayerKnowledge getKnowledge(EntityPlayer player)
    {
        return getTechnomagi(player).knowledge();
    }

    public static PlayerNanites getNanites(EntityPlayer player)
    {
        return getTechnomagi(player).nanites();
    }

    public static PlayerAbilities getAbilities(EntityPlayer player)
    {
        return getTechnomagi(player).abilities();
    }

    public static boolean hasSpecialised(EntityPlayer player)
    {
        PlayerTechnomagi technomage = getTechnomagi(player);

        return technomage != null && technomage.hasSpecialised();
    }

    public static boolean hasKnowledge(EntityPlayer player, String knowledge)
    {
        return hasKnowledge(player, TechnomagiApi.getKnowledge(knowledge));
    }

    public static boolean hasKnowledge(EntityPlayer player, Knowledge knowledge)
    {
        PlayerTechnomagi technomage = getTechnomagi(player);

        return technomage != null && technomage.knowledge().hasKnowledge(knowledge.getName());
    }

    public static boolean isSpecialisation(EntityPlayer player, Specialisation spec)
    {
        PlayerTechnomagi technomage = getTechnomagi(player);

        return hasSpecialised(player) && technomage.getSpecialisation().equals(spec);
    }

    public static void specialise(EntityPlayer player, String spec)
    {
        specialise(player, TechnomagiApi.specialisation().getSpecialisation(spec));
    }

    public static void specialise(EntityPlayer player, Specialisation spec)
    {
        PlayerTechnomagi technomage = getTechnomagi(player);
        technomage.setSpecialisation(spec);
        TechnomagiHooks.specialise(player, spec);

        if (player.worldObj.isRemote) {
            PacketHelper.specialise(spec.getName());
        }
    }

    public static boolean canSeeBlock(EntityPlayer player, int x, int y, int z)
    {
        return player.worldObj.rayTraceBlocks(Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ), Vec3.createVectorHelper(x, y, z)) == null;
    }

    public static boolean consumeInventoryItem(EntityPlayer player, ItemStack stack)
    {
        if (!player.capabilities.isCreativeMode) {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slot = player.inventory.getStackInSlot(i);

                if (slot != null && slot.isItemEqual(stack)) {
                    slot.stackSize--;

                    if (slot.stackSize <= 0) {
                        slot = null;
                    }

                    player.inventory.setInventorySlotContents(i, slot);

                    return true;
                }
            }
        } else {
            return true;
        }

        return false;
    }

    public static boolean consumeHeldItem(EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode) {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slot = player.inventory.getCurrentItem();

                if (slot != null) {
                    slot.stackSize--;

                    if (slot.stackSize <= 0) {
                        slot = null;
                    }

                    player.inventory.setInventorySlotContents(i, slot);

                    return true;
                }
            }
        } else {
            return true;
        }

        return false;
    }

    public static void giveInventoryItem(EntityPlayer player, ItemStack stack)
    {
        if (!player.inventory.addItemStackToInventory(stack)) {
            player.dropPlayerItemWithRandomChoice(stack, false);
        }
    }

    public static boolean hasInventoryItem(EntityPlayer player, ItemStack stack)
    {
        return getInventoryItem(player, stack) != null;
    }

    public static ItemStack getInventoryItem(EntityPlayer player, ItemStack stack)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slot = player.inventory.getStackInSlot(i);

            if (slot != null && slot.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(slot, stack)) {
                return slot;
            }
        }

        return null;
    }

    public static void addChatMessage(EntityPlayer player, String message)
    {
        player.addChatComponentMessage(new ChatComponentText(message));
    }

    public static void addLinkedChatMessage(EntityPlayer player, String message, String url)
    {
        ChatComponentText link = new ChatComponentText(url);
        link.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        link.getChatStyle().setUnderlined(Boolean.valueOf(true));

        ChatComponentText chat = new ChatComponentText(message);
        chat.getChatStyle().setColor(EnumChatFormatting.RED);
        link.getChatStyle().setColor(EnumChatFormatting.WHITE);
        chat.appendSibling(link);

        player.addChatComponentMessage(chat);
    }

    public static void addTranslatedChatMessage(EntityPlayer player, String translation)
    {
        player.addChatComponentMessage(new ChatComponentTranslation(translation));
    }

    public static MovingObjectPosition getMovingObjectPosition(World world, EntityPlayer player, boolean b)
    {
        double reachDistance = 5.0D;
        double reachDistance2 = 5.0D;
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * f + (world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight());
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        Vec3 vecPosition = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;

        if (player instanceof EntityPlayerMP) {
            reachDistance2 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        }

        Vec3 vecLook = vecPosition.addVector(f7 * reachDistance, f6 * reachDistance, f8 * reachDistance);
        MovingObjectPosition blockHit = world.func_147447_a(vecPosition, vecLook, b, !b, false);
        Vec3 vecEntity1 = vecPosition.addVector(vecLook.xCoord * reachDistance, vecLook.yCoord * reachDistance, vecLook.zCoord * reachDistance);
        Vec3 vecEntity2 = null;
        Entity pointedEntity = null;
        double reachDistance3 = reachDistance2;

        List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.addCoord(vecLook.xCoord * reachDistance, vecLook.yCoord * reachDistance, vecLook.zCoord * reachDistance).expand(f, f, f));

        for (Entity entity : entityList) {
            float collision = 0.5F;

            AxisAlignedBB boundingBox = entity.boundingBox.expand(collision, collision, collision);
            MovingObjectPosition movingobjectposition = boundingBox.calculateIntercept(vecPosition, vecEntity1);

            if (boundingBox.isVecInside(vecPosition)) {
                if (reachDistance3 >= 0.0D) {
                    pointedEntity = entity;
                    vecEntity2 = movingobjectposition == null ? vecLook : movingobjectposition.hitVec;
                    reachDistance3 = 0.0D;
                }
            } else if (movingobjectposition != null) {
                double reachDistance4 = vecPosition.distanceTo(movingobjectposition.hitVec);

                if (reachDistance4 < reachDistance3 || reachDistance3 == 0.0D) {
                    pointedEntity = entity;
                    vecEntity2 = movingobjectposition.hitVec;
                    reachDistance3 = reachDistance4;
                }
            }
        }

        if (pointedEntity != null) {
            Technomagi.debug("-----");
            Technomagi.debug("Pointed entity: " + pointedEntity);
            return new MovingObjectPosition(pointedEntity, vecEntity2);
        }

        return blockHit;
    }

    public static AbilityPayload getAbilityPayload(World world, EntityPlayer player, int x, int y, int z, int face)
    {
        MovingObjectPosition mouse = PlayerHelper.getMovingObjectPosition(world, player, true);
        AbilityPayload payload = null;

        if (mouse != null) {
            if (mouse.typeOfHit.equals(MovingObjectType.BLOCK)) {
                Block block = world.getBlock(mouse.blockX, mouse.blockY, mouse.blockZ);
                payload = new AbilityPayload(0, block, null, mouse.blockX, mouse.blockY, mouse.blockZ, mouse.sideHit);
            } else if (mouse.typeOfHit.equals(MovingObjectType.ENTITY)) {
                payload = new AbilityPayload(0, null, mouse.entityHit, mouse.blockX, mouse.blockY, mouse.blockZ, mouse.sideHit);
            }
        } else {
            Block block = world.getBlock(x, y, z);
            payload = new AbilityPayload(0, block, null, x, y, z, face);
        }

        return payload;
    }

}
