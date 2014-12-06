package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.init.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMalleableCircuit extends ItemTMNBT
{

    public ItemMalleableCircuit(String name)
    {
        super(name);

        setCreativeTab(TechnoMagi.tabTMCircuit);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass != 0) {
            int unit = getUnit(stack);

            if (unit > -1) {
                return Items.itemUnit.getIconFromDamage(unit);
            }
        }

        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    public static int getUnit(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound != null && compound.hasKey("Unit")) {
            return compound.getInteger("Unit");
        }

        return -1;
    }

    public static void setUnit(ItemStack stack, int unit)
    {
        NBTTagCompound compound = getNBT(stack);

        if (compound == null) {
            stack.stackTagCompound = new NBTTagCompound();
            compound = stack.stackTagCompound;
        }

        compound.setInteger("Unit", unit);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        String info = "";

        int unit = getUnit(stack);

        if (unit >= 0) {
            EnumChatFormatting formatting = null;

            switch ((unit % 3) + 1) {
                case 2:
                    formatting = EnumChatFormatting.GOLD;
                    break;
                case 3:
                    formatting = EnumChatFormatting.AQUA;
                    break;
                default:
                    formatting = EnumChatFormatting.WHITE;
                    break;
            }

            list.add(formatting + StatCollector.translateToLocal("item.unit." + Items.itemUnit.itemNames[unit] + ".name"));
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack initial = new ItemStack(item, 1);
        initial.stackTagCompound = new NBTTagCompound();
        setUnit(initial, -1);
        list.add(initial);

        String[] itemNames = Items.itemUnit.itemNames;

        for (int i = 0; i < itemNames.length; i++) {
            ItemStack stack = new ItemStack(item);
            initial.stackTagCompound = new NBTTagCompound();
            setUnit(stack, i);
            list.add(stack);
        }
    }

}