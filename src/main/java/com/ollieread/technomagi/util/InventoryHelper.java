package com.ollieread.technomagi.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryHelper
{

    public static boolean consumeInventoryItem(IInventory inventory, ItemStack stack)
    {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);

            if (slot != null && slot.isItemEqual(stack)) {
                Item item = slot.getItem();

                if (item.hasContainerItem(stack)) {
                    slot = item.getContainerItem(stack);
                } else {
                    slot.stackSize--;

                    if (slot.stackSize <= 0) {
                        slot = null;
                    }
                }

                inventory.setInventorySlotContents(i, slot);

                return true;
            }
        }

        return false;
    }

    public static ItemStack consumeInventoryItem(ItemStack stack)
    {
        if (stack != null) {
            Item item = stack.getItem();

            if (item.hasContainerItem(stack)) {
                stack = item.getContainerItem(stack);
            } else {
                stack.stackSize--;

                if (stack.stackSize <= 0) {
                    stack = null;
                }
            }
        }

        return stack;
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

    public static int countInventoryItem(IInventory inventory, ItemStack stack)
    {
        int total = 0;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);

            if (slot != null && slot.isItemEqual(stack)) {
                total += slot.stackSize;
            }
        }

        return total;
    }

    public static boolean consumeAllInventoryItem(IInventory inventory, ItemStack stack, int max)
    {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack slot = inventory.getStackInSlot(i);

            if (slot != null && slot.isItemEqual(stack)) {

                if (max >= 0) {
                    max -= slot.stackSize;
                    slot.stackSize = 0;
                    slot = null;
                } else {
                    max += slot.stackSize;
                    slot.stackSize -= max;
                    max = 0;
                }

                inventory.setInventorySlotContents(i, slot);
            }

            if (max == 0) {
                return true;
            }
        }

        return false;
    }

}
