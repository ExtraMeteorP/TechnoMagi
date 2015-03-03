package com.ollieread.technomagi.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.util.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCircuitPatterns extends ItemSubtypes
{

    public ItemCircuitPatterns(String name)
    {
        super(name, new String[] { "etherium", "void", "noughtite", "relux", "cimmerium", "calidite", "glacite", "verux", "oblerite" });

        this.setMaxDamage(101);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(Technomagi.MODID.toLowerCase() + ":circuit_pattern");

        super.registerIcons(register);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        if (isBlank(stack)) {
            return this.itemIcon;
        }

        return this.itemIcons[this.getType(stack)];
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        return pass == 0 ? this.itemIcon : getIconIndex(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return metadata == 0 ? 1 : 2;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return isBlank(stack) ? false : true;
    }

    public boolean isBlank(ItemStack stack)
    {
        return !ItemNBTHelper.has(stack, "Type");
    }

    public void setType(ItemStack stack, int type)
    {
        ItemNBTHelper.setInteger(stack, "Type", type);
    }

    public int getType(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "Type");
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
        return !isBlank(stack);
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        if (!isBlank(stack)) {
            ItemStack copied = stack.copy();
            copied.setItemDamage(copied.getItemDamage() - 1);

            if (copied.getItemDamage() > 0) {
                return copied;
            }
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1));

        for (int i = 0; i < this.names.length; i++) {
            ItemStack stack = new ItemStack(item, 1, 1);
            setType(stack, i);
            list.add(stack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (isBlank(stack)) {
            return "item.technomagi." + this.name;
        } else {
            return "item.technomagi." + this.name + "." + getName(getType(stack));
        }
    }

}
