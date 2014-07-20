package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.tileentity.TileEntityAnalysis;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerAnalysis extends Container
{

    private TileEntityAnalysis analysis;
    private int lastProgress;
    private int lastData;

    public ContainerAnalysis(InventoryPlayer playerInventory, TileEntityAnalysis tile)
    {
        analysis = tile;

        addSlotToContainer(new SlotAnalysis(analysis, 0, 5, 26));
        addSlotToContainer(new SlotAnalysis(analysis, 1, 22, 26));
        addSlotToContainer(new SlotAnalysis(analysis, 2, 39, 26));
        addSlotToContainer(new SlotAnalysis(analysis, 3, 5, 43));
        addSlotToContainer(new SlotAnalysis(analysis, 4, 22, 43));
        addSlotToContainer(new SlotAnalysis(analysis, 5, 39, 43));
        addSlotToContainer(new SlotAnalysis(analysis, 6, 5, 60));
        addSlotToContainer(new SlotAnalysis(analysis, 7, 22, 60));
        addSlotToContainer(new SlotAnalysis(analysis, 8, 39, 60));

        addPlayerSlots(playerInventory);
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, analysis.getProgress());
        par1ICrafting.sendProgressBarUpdate(this, 1, analysis.getData());
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastProgress != analysis.getProgress()) {
                icrafting.sendProgressBarUpdate(this, 0, analysis.getProgress());
            }

            if (lastData != analysis.getData()) {
                icrafting.sendProgressBarUpdate(this, 1, analysis.getData());
            }
        }

        lastProgress = analysis.getProgress();
        lastData = analysis.getData();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        if (i == 0) {
            analysis.setProgress(v);
        }

        if (i == 1) {
            analysis.setData(v);
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
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 152));
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
