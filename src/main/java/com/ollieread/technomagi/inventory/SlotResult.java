package com.ollieread.technomagi.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotResult extends Slot
{

    public SlotResult(IInventory inventory, int i, int x, int y)
    {
        super(inventory, i, x, y);
    }

    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

}
