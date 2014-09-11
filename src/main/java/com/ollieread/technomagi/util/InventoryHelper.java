package com.ollieread.technomagi.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryHelper
{

    public static boolean consumeInventoryItem(IInventory inventory, ItemStack stack)
    {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);

            if (slot != null && slot.isItemEqual(stack)) {
                slot.stackSize--;

                if (slot.stackSize <= 0) {
                    slot = null;
                }

                inventory.setInventorySlotContents(i, slot);

                return true;
            }
        }

        return false;
    }

    public static boolean hasInventoryItem(IInventory inventory, ItemStack stack)
    {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);

            if (slot != null && slot.isItemEqual(stack)) {
                if (stack.stackSize > 1) {
                    return slot.stackSize >= stack.stackSize;
                }

                return true;
            }
        }

        return false;
    }

}
