package com.ollieread.technomagi.tileentity.component;

import net.minecraft.item.ItemStack;

public class ComponentResult extends ComponentInventory
{

    public ComponentResult()
    {
        super(1);
    }

    public ItemStack getStackInSlot()
    {
        return super.getStackInSlot(0);
    }

    public ItemStack decrStackSize(int count)
    {
        return super.decrStackSize(0, count);
    }

    public ItemStack getStackInSlotOnClosing()
    {
        return super.getStackInSlotOnClosing(0);
    }

    public void setInventorySlotContents(ItemStack stack)
    {
        super.setInventorySlotContents(0, stack);
    }

}
