package com.ollieread.technomagi.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.common.init.Items;

public class SlotNanites extends Slot
{

    protected boolean empty = false;

    public SlotNanites(IInventory inventory, int i, int x, int y, boolean empty)
    {
        super(inventory, i, x, y);

        this.empty = empty;
    }

    public boolean isItemValid(ItemStack stack)
    {
        return stack.isItemEqual(new ItemStack(Items.itemNaniteContainer)) && (empty ? stack.getItemDamage() == 0 : true);
    }

}
