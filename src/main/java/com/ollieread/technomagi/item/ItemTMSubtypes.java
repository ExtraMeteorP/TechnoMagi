package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemTMSubtypes extends ItemTM
{

    protected IIcon[] itemIcons;
    public String[] itemNames;

    public ItemTMSubtypes(String name, int subtypes)
    {
        this(name, subtypes, new String[subtypes]);
    }

    public ItemTMSubtypes(String name, String[] names)
    {
        this(name, names.length, names);
    }

    public ItemTMSubtypes(String name, int subtypes, String[] names)
    {
        super(name);
        setHasSubtypes(true);
        itemIcons = new IIcon[subtypes];
        itemNames = names;
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + "." + itemNames[stack.getItemDamage()] + ".name")).trim();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());

        for (int i = 0; i < itemIcons.length; i++) {
            itemIcons[i] = register.registerIcon(Reference.MODID.toLowerCase() + ":" + itemNames[i]);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
        return itemIcons[damage];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < itemIcons.length; i++) {
            list.add(new ItemStack(this, 1, i));
        }

    }

    public static ItemStack getStack(ItemTMSubtypes item, String name, int q)
    {
        for (int i = 0; i < item.itemNames.length; i++) {
            if (item.itemNames[i].equals(name)) {
                return new ItemStack(item, q, i);
            }
        }

        return null;
    }

}
