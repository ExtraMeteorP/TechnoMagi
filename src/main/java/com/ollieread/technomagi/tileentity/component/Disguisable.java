package com.ollieread.technomagi.tileentity.component;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Disguisable implements IDisguisable
{

    protected Inventory inventory = new Inventory(1);

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

            if (block != null && block.isNormalCube() && block.renderAsNormalBlock() && !block.hasTileEntity(stack.getItemDamage())) {
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
        inventory.readFromNBT(compound);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        inventory.writeToNBT(compound);
    }

}
