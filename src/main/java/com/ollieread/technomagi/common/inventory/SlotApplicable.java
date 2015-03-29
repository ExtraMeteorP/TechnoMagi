package com.ollieread.technomagi.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * This is for containers, the idea is that these slots will check their value.
 *
 * @author ollieread
 *
 */
public class SlotApplicable extends Slot
{

    public static interface ISlotApplicable
    {
        public boolean isItemStackApplicable(ItemStack stack);
    }

    protected ISlotApplicable applicable;

    public SlotApplicable(IInventory inventory, int id, int x, int y, ISlotApplicable applicable)
    {
        super(inventory, id, x, y);

        this.applicable = applicable;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return this.applicable.isItemStackApplicable(stack);
    }

}
