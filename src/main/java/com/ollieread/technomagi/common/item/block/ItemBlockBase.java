package com.ollieread.technomagi.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.BlockSubtypes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockBase extends ItemBlock
{
    private Block block;

    public ItemBlockBase(Block block)
    {
        super(block);
        this.block = block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int metadata)
    {
        return metadata;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        return this.block.getIcon(2, par1);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        Block subtype = Block.getBlockFromItem(stack.getItem());
        String suffix = "";

        if (subtype instanceof BlockSubtypes) {
            suffix = ((BlockSubtypes) block).getName(stack.getItemDamage());
        } else if (subtype instanceof BlockContainerSubtypes) {
            suffix = ((BlockContainerSubtypes) block).getName(stack.getItemDamage());
        }

        if (!suffix.isEmpty()) {
            return getUnlocalizedName() + "." + suffix;
        }

        return getUnlocalizedName();
    }

}
