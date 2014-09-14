package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.tileentity.TileEntityConstruct;

public class ContainerConstruct extends Container
{

    private TileEntityConstruct construct;
    private EntityPlayer player;

    public ContainerConstruct(InventoryPlayer inventory, TileEntityConstruct construct)
    {
        this.construct = construct;

        addSlotToContainer(new Slot(construct, 0, 15, 26));
        addSlotToContainer(new Slot(construct, 1, 64, 26));
        addSlotToContainer(new Slot(construct, 2, 94, 26));
        addSlotToContainer(new Slot(construct, 3, 124, 26));
        addSlotToContainer(new Slot(construct, 4, 154, 26));

        addPlayerSlots(inventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    public void addPlayerSlots(InventoryPlayer playerInventory)
    {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 13 + j * 18, 80 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, 13 + i * 18, 138));
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (i > 8) {
                if (!this.mergeItemStack(itemstack1, 0, 8, false)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }

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
