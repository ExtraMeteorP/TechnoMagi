package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class PlayerHelper
{

    public static boolean isStoodOn(EntityPlayer player, Block block)
    {
        if (getBlockStoodOn(player).equals(block)) {
            return true;
        }

        return false;
    }

    public static Block getBlockStoodOn(EntityPlayer player)
    {
        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY) - 1;
        int z = MathHelper.floor_double(player.posZ);

        return player.worldObj.getBlock(x, y, z);
    }

    public static boolean isStoodOnMeta(EntityPlayer player, Block block, int meta)
    {
        if (getBlockStoodOnMeta(player).isItemEqual(new ItemStack(block, 1, meta))) {
            return true;
        }

        return false;
    }

    public static ItemStack getBlockStoodOnMeta(EntityPlayer player)
    {
        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY) - 1;
        int z = MathHelper.floor_double(player.posZ);

        return new ItemStack(player.worldObj.getBlock(x, y, z), 1, player.worldObj.getBlockMetadata(x, y, z));
    }

    public static TileEntity getTileEntityStoodOn(EntityPlayer player)
    {
        int x = MathHelper.floor_double(player.posX);
        int y = MathHelper.floor_double(player.posY) - 1;
        int z = MathHelper.floor_double(player.posZ);

        return player.worldObj.getTileEntity(x, y, z);
    }

    public static Vec3 getLookVector(EntityPlayer player)
    {
        Vec3 look = player.getLookVec();

        return look;
    }

    public static Vec3 getEyeVector(EntityPlayer player)
    {
        Vec3 vec = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);

        if (!player.worldObj.isRemote) {
            vec.yCoord += player.getEyeHeight();

            if (player instanceof EntityPlayerMP && player.isSneaking()) {
                vec.yCoord -= 0.08;
            }
        }

        return vec;
    }

    public static boolean consumeInventoryItem(EntityPlayer player, ItemStack stack)
    {
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

        return false;
    }

    public static boolean hasInventoryItem(EntityPlayer player, ItemStack stack)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack slot = player.inventory.getStackInSlot(i);

            if (slot != null && slot.isItemEqual(stack)) {
                return true;
            }
        }

        return false;
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
}
