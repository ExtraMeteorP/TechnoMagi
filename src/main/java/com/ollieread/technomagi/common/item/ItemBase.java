package com.ollieread.technomagi.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBase extends Item
{

    protected String name;

    public ItemBase(String name)
    {
        this.name = name;
        this.setUnlocalizedName(name);
        this.setTextureName(name);
        this.setCreativeTab(TechnomagiTabs.items);
    }

    public String getTexturePath(String name)
    {
        return Technomagi.MODID.toLowerCase() + ":" + name.replace('.', '/');
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(getTexturePath(this.getIconString()));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return "item.technomagi." + this.name;
    }

}
