package com.ollieread.technomagi.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.item.IItemPlayerLocked;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.util.ItemNBTHelper;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInterface extends ItemBase implements IItemPlayerLocked
{

    public ItemInterface(String name)
    {
        super(name);

        this.setMaxStackSize(1);
    }

    @Override
    public String getPlayer(ItemStack stack)
    {
        return ItemNBTHelper.getString(stack, "Player");
    }

    @Override
    public boolean isPlayer(ItemStack stack, EntityPlayer player)
    {
        return this.isPlayer(stack, player.getCommandSenderName());
    }

    @Override
    public boolean isPlayer(ItemStack stack, String player)
    {
        return ItemNBTHelper.getString(stack, "Player").equals(player);
    }

    @Override
    public void setPlayer(ItemStack stack, EntityPlayer player)
    {
        this.setPlayer(stack, player.getCommandSenderName());
    }

    @Override
    public void setPlayer(ItemStack stack, String player)
    {
        ItemNBTHelper.setString(stack, "Player", player);
    }

    @Override
    public boolean isPlayerLocked(ItemStack stack)
    {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        String playerName = getPlayer(stack);

        if (playerName == null || playerName.isEmpty()) {
            setPlayer(stack, player);
        }

        if (isPlayer(stack, player)) {
            if (player.isSneaking()) {
                Technomagi.debug("Starting sync");
                PlayerHelper.getKnowledge(player).toggleSyncing();
            } else {
                player.openGui(Technomagi.instance, CommonProxy.GUI_TECHNOMAGE, world, (int) player.posX, (int) player.posY, (int) player.posZ);
            }
        }

        return stack;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
    {
        super.addInformation(stack, player, info, advanced);

        String playerName = getPlayer(stack);

        if (playerName != null && !playerName.isEmpty()) {
            info.add("Owned by " + playerName);

            if (!playerName.equals(player.getCommandSenderName())) {
                info.add(EnumChatFormatting.RED + "Locked");
            } else {
                if (PlayerHelper.getKnowledge(player).isSyncing()) {
                    info.add(EnumChatFormatting.AQUA + "Syncing");
                }
            }
        }
    }

}
