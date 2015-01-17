package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTM<TE> extends Container
{

    protected TE tileEntity;
    protected boolean inventory;
    public int width;
    public int height;
    public int xOffset = 0;
    public int yOffset = 0;
    public int invX = 0;
    public int invY = 0;
    protected int invStart = 0;

    public ContainerTM(TE tileEntity, boolean inventory, int width, int height)
    {
        this.tileEntity = tileEntity;
        this.inventory = inventory;
        this.width = width;
        this.height = height;

        if (inventory) {
            this.invY = this.height + 3;
            this.height += 93;

            if (this.width < 176) {
                this.xOffset = (176 - this.width) / 2;
            } else if (this.width > 176) {
                this.invX = (this.width - 176) / 2;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    public void addPlayerSlots(InventoryPlayer playerInventory)
    {
        invStart = this.inventorySlots.size();
        int x = this.invX + 8;
        int y = this.invY + 8;
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

            if (i < invStart) {
                if (slot instanceof SlotTM && ((SlotTM) slot).isShiftable()) {
                    if (!this.mergeItemStack(itemstack1, invStart, invStart + 27, false)) {
                        return null;
                    }
                }
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
