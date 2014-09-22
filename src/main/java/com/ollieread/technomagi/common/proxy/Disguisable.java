package com.ollieread.technomagi.common.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.tileentity.IDisguisableTile;

public class Disguisable implements IDisguisableTile
{

    protected BasicInventory inventory = new BasicInventory(1);

    @Override
    public boolean isDisguised()
    {
        return inventory.getStackInSlot(0) != null;
    }

    @Override
    public boolean setDisguise(ItemStack stack)
    {
        if (stack == null) {
            inventory.setInventorySlotContents(0, null);
            return true;
        }

        if (stack.getItem() != null) {
            Block block = Block.getBlockFromItem(stack.getItem());

            if (block != null && block.isNormalCube() && block.renderAsNormalBlock()) {
                inventory.setInventorySlotContents(0, stack);

                return true;
            }
        }

        return false;
    }

    @Override
    public ItemStack getDisguise()
    {
        return inventory.getStackInSlot(0);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagCompound disguise = (NBTTagCompound) compound.getTag("Disguise");

        inventory.readFromNBT(disguise);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound disguise = new NBTTagCompound();

        inventory.writeToNBT(disguise);

        compound.setTag("Disguise", disguise);
    }

}
