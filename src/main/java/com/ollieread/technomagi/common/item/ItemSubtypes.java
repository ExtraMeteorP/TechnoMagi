package com.ollieread.technomagi.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ollieread.technomagi.util.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSubtypes extends ItemBase
{

    protected String texturePrefix;
    protected String[] names;
    @SideOnly(Side.CLIENT)
    protected IIcon[] itemIcons;

    public ItemSubtypes(String name, String[] names)
    {
        super(name);

        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.names = names;
        this.texturePrefix = name;
    }

    public String getName(int metadata)
    {
        if (this.names != null) {
            return this.names[metadata];
        }

        return null;
    }

    public int getDamageFromName(String name)
    {
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(name)) {
                    return i;
                }
            }
        }

        return 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName(stack) + "." + getName(stack.getItemDamage());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.itemIcons = new IIcon[this.names.length];

        for (int i = 0; i < this.itemIcons.length; i++) {
            this.itemIcons[i] = register.registerIcon(getTexturePath(texturePrefix + "/" + this.names[i]));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
        return this.itemIcons[damage];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        return getIconFromDamage(stack.getItemDamage());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < this.names.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        ItemNBTHelper.resetNBT(stack);
    }

}
