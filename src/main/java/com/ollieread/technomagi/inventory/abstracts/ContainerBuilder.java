package com.ollieread.technomagi.inventory.abstracts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBuilder extends Container
{

    public int width;
    public int height;

    public ContainerBuilder(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    public void setLink(String link)
    {
    }

    public String getLink()
    {
        return null;
    }

    public void addPlayerSlots(InventoryPlayer playerInventory, int x, int y)
    {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

        y += 58;
        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, x + i * 18, y));
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

}
