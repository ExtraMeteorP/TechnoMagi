package com.ollieread.technomagi.common.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSubtypes extends BlockBase
{

    protected String texturePrefix;
    protected String[] names;
    @SideOnly(Side.CLIENT)
    protected IIcon[] blockIcons;

    protected BlockSubtypes(String name, String[] names, Material material)
    {
        super(name, material);

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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcons = new IIcon[this.names.length];

        for (int i = 0; i < this.blockIcons.length; i++) {
            this.blockIcons[i] = register.registerIcon(getTexturePath(texturePrefix + "/" + this.names[i]));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        return this.blockIcons[metadata];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < names.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
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

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
    {
    }

    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.common;
    }

}
