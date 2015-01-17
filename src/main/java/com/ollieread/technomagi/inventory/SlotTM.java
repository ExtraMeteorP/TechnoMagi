package com.ollieread.technomagi.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTM extends Slot
{

    protected boolean result;
    protected boolean shiftable;

    public SlotTM(IInventory inventory, int i, int x, int y, boolean result, boolean shiftable)
    {
        super(inventory, i, x, y);

        this.result = result;
        this.shiftable = shiftable;
    }

    public boolean isResult()
    {
        return result;
    }

    public boolean isShiftable()
    {
        return shiftable;
    }

    public boolean isItemValid(ItemStack stack)
    {
        return !isResult();
    }

}
