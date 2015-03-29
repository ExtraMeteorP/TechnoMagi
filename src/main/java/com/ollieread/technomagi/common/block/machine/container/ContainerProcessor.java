package com.ollieread.technomagi.common.block.machine.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessor;
import com.ollieread.technomagi.common.inventory.ContainerTechnomagi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerProcessor extends ContainerTechnomagi
{

    public TileResourceProcessor tile;
    public int lastMaxProgress;
    public int lastProgress;

    public ContainerProcessor(IInventory playerInventory, TileResourceProcessor tile)
    {
        this.tile = tile;
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, tile.getMaxProgress());
        crafting.sendProgressBarUpdate(this, 1, tile.getProgress());
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastMaxProgress != tile.getMaxProgress()) {
                icrafting.sendProgressBarUpdate(this, 0, tile.getMaxProgress());
            }

            if (lastProgress != tile.getProgress()) {
                icrafting.sendProgressBarUpdate(this, 1, tile.getProgress());
            }
        }

        lastMaxProgress = tile.getMaxProgress();
        lastProgress = tile.getProgress();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        if (i == 0) {
            tile.setMaxProgress(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }

        if (i == 1) {
            tile.setProgress(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (i > tile.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, 0, tile.getSizeInventory(), true)) {
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
