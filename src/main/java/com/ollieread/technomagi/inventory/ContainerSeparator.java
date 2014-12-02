package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.tileentity.TileEntitySeparator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSeparator extends Container
{

    private TileEntitySeparator crafter;
    private int lastProgress;

    public ContainerSeparator(InventoryPlayer playerInventory, TileEntitySeparator tile)
    {
        crafter = tile;

        addSlotToContainer(new Slot(crafter, 0, 5, 29));
        addSlotToContainer(new SlotResult(crafter, 1, 133, 29));
        addSlotToContainer(new SlotResult(crafter, 2, 155, 29));

        addPlayerSlots(playerInventory);
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, crafter.getProgress());
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastProgress != crafter.getProgress()) {
                icrafting.sendProgressBarUpdate(this, 0, crafter.getProgress());
            }
        }

        lastProgress = crafter.getProgress();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        if (i == 0) {
            crafter.setProgress(v);
        }
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
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 64 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 122));
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (i > 2) {
                if (!this.mergeItemStack(itemstack1, 0, 0, false)) {
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
