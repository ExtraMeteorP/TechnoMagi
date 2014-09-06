package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerNaniteReplicator extends Container
{

    private TileEntityNaniteReplicator replicator;
    private int lastNanites;
    private int lastSample;
    private String lastNaniteType;
    private String lastSampleType;
    private int lastProgress;

    public ContainerNaniteReplicator(InventoryPlayer playerInventory, TileEntityNaniteReplicator tile)
    {
        replicator = tile;

        addSlotToContainer(new Slot(replicator, 0, 5, 26));
        addSlotToContainer(new SlotResult(replicator, 1, 164, 26));
        addSlotToContainer(new SlotNanites(replicator, 2, 164, 61, true));

        addPlayerSlots(playerInventory);
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, replicator.getNanites());
        par1ICrafting.sendProgressBarUpdate(this, 1, replicator.getSample());
        par1ICrafting.sendProgressBarUpdate(this, 2, replicator.getProgress());
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastNanites != replicator.getNanites()) {
                icrafting.sendProgressBarUpdate(this, 0, replicator.getNanites());
            }

            if (lastSample != replicator.getSample()) {
                icrafting.sendProgressBarUpdate(this, 1, replicator.getSample());
            }

            if (lastProgress != replicator.getProgress()) {
                icrafting.sendProgressBarUpdate(this, 2, replicator.getProgress());
            }
        }

        lastNanites = replicator.getNanites();
        lastSample = replicator.getSample();
        lastNaniteType = replicator.getNaniteType();
        lastSampleType = replicator.getSampleType();
        lastProgress = replicator.getProgress();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        if (i == 0) {
            replicator.setNanites(v);
        }

        if (i == 1) {
            replicator.setSample(v);
        }

        if (i == 2) {
            replicator.setProgress(v);
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
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 13 + j * 18, 95 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, 13 + i * 18, 153));
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
                if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                    if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
                        return null;
                    }
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (i < 3) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
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
