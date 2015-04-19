package com.ollieread.technomagi.common.component;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.api.tile.ITileDisguisable;

public class Disguise implements ITileDisguisable
{

    protected ItemStack disguiseStack;

    @Override
    public boolean isDisguised()
    {
        return getDisguise() != null;
    }

    @Override
    public ItemStack getDisguise()
    {
        return disguiseStack;
    }

    @Override
    public boolean setDisguiseBlock(ItemStack stack)
    {
        if (!isDisguised() && stack.getItem() != null && stack.getItem() instanceof ItemBlock) {
            Block block = Block.getBlockFromItem(stack.getItem());

            if (block != null && block.isNormalCube() && !block.hasTileEntity(stack.getItemDamage())) {
                disguiseStack = stack;
                return true;
            }
        }

        return false;
    }

    @Override
    public void removeDisguise()
    {
        disguiseStack = null;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        if (disguiseStack != null) {
            disguiseStack.writeToNBT(compound);
        }
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("id")) {
            ItemStack.loadItemStackFromNBT(compound);
        }
    }

}
