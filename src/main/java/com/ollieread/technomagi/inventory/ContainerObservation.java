package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.tileentity.TileEntityObservationChamber;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerObservation extends Container
{

    private TileEntityObservationChamber chamber;
    private int lastProgress;
    private int lastData;

    public ContainerObservation(InventoryPlayer playerInventory, TileEntityObservationChamber tile)
    {
        chamber = tile;

        addSlotToContainer(new SlotObservation(chamber, 0, 69, 28));
        addSlotToContainer(new SlotResult(chamber, 1, 150, 28));

        addPlayerSlots(playerInventory);
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, chamber.getProgress());
        par1ICrafting.sendProgressBarUpdate(this, 1, chamber.getData());
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastProgress != chamber.getProgress()) {
                icrafting.sendProgressBarUpdate(this, 0, chamber.getProgress());
            }

            if (lastData != chamber.getData()) {
                icrafting.sendProgressBarUpdate(this, 1, chamber.getData());
            }
        }

        lastProgress = chamber.getProgress();
        lastData = chamber.getData();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        if (i == 0) {
            chamber.setProgress(v);
        }

        if (i == 1) {
            chamber.setData(v);
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
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 110 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 168));
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
                if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
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
