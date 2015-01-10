package com.ollieread.technomagi.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;

import com.ollieread.ennds.extended.ExtendedNanites;
import com.ollieread.ennds.extended.ExtendedPlayerAbilities;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

public class PlayerHelper
{

    public static boolean canSeeBlock(EntityPlayer player, int x, int y, int z)
    {
        return player.worldObj.rayTraceBlocks(Vec3.createVectorHelper(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ), Vec3.createVectorHelper(x, y, z)) == null;
    }

    public static ExtendedPlayerKnowledge getPlayerKnowledge(EntityPlayer player)
    {
        return ExtendedPlayerKnowledge.get(player);
    }

    public static ExtendedPlayerAbilities getPlayerAbilities(EntityPlayer player)
    {
        ExtendedPlayerKnowledge knowledge = getPlayerKnowledge(player);

        if (knowledge != null) {
            return knowledge.abilities;
        }

        return null;
    }

    public static ExtendedNanites getPlayerNanites(EntityPlayer player)
    {
        ExtendedPlayerKnowledge knowledge = getPlayerKnowledge(player);

        if (knowledge != null) {
            return knowledge.nanites;
        }

        return null;
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
