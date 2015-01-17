package com.ollieread.technomagi.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;

public class ItemPersonalInterface extends ItemTMNBT
{

    public ItemPersonalInterface(String name)
    {
        super(name);
    }

    public static String getPlayer(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound != null && compound.hasKey("Player")) {
            return compound.getString("Player");
        }

        return null;
    }

    public static void setPlayer(ItemStack stack, EntityPlayer player)
    {
        setPlayer(stack, player.getCommandSenderName());
    }

    public static void setPlayer(ItemStack stack, String player)
    {
        NBTTagCompound compound = getNBT(stack);
        compound.setString("Player", player);
    }

    public static boolean isPlayer(ItemStack stack, EntityPlayer player)
    {
        return isPlayer(stack, player.getCommandSenderName());
    }

    public static boolean isPlayer(ItemStack stack, String player)
    {
        String playerStored = getPlayer(stack);

        if (playerStored != null && playerStored.equals(player)) {
            return true;
        }

        return false;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (getPlayer(stack) == null) {
            setPlayer(stack, player);
        }

        if (isPlayer(stack, player)) {
            player.openGui(TechnoMagi.instance, CommonProxy.GUI_PERSONAL_INTERFACE, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        }

        return stack;
    }

}
