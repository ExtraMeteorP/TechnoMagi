package com.ollieread.technomagi.common.block.energy.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.common.block.energy.tile.TileBasicGenerator;
import com.ollieread.technomagi.common.inventory.ContainerTechnomagi;
import com.ollieread.technomagi.common.inventory.SlotApplicable;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerBasicGenerator extends ContainerTechnomagi
{

    public TileBasicGenerator tile;
    public int lastEnergy;
    public int lastMaxProgress;
    public int lastProgress;

    public ContainerBasicGenerator(IInventory playerInventory, TileBasicGenerator tile)
    {
        this.tile = tile;

        addSlotToContainer(new SlotApplicable(tile, 0, 7, 30, ItemStackHelper.fuelSlot));
        addSlotToContainer(new SlotApplicable(tile, 1, 150, 30, ItemStackHelper.energyContainerSlot));
        addPlayerSlots(playerInventory, 8, 45);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, tile.getEnergyStored(null));
        crafting.sendProgressBarUpdate(this, 1, tile.getMaxProgress());
        crafting.sendProgressBarUpdate(this, 2, tile.getProgress());
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastEnergy != tile.getEnergyStored(null)) {
                icrafting.sendProgressBarUpdate(this, 0, tile.getEnergyStored(null));
            }

            if (lastMaxProgress != tile.getMaxProgress()) {
                icrafting.sendProgressBarUpdate(this, 1, tile.getMaxProgress());
            }

            if (lastProgress != tile.getProgress()) {
                icrafting.sendProgressBarUpdate(this, 2, tile.getProgress());
            }
        }

        lastEnergy = tile.getEnergyStored(null);
        lastMaxProgress = tile.getMaxProgress();
        lastProgress = tile.getProgress();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        if (i == 0) {
            tile.setEnergyStored(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }

        if (i == 1) {
            tile.setMaxProgress(v);
            GuiBuilder.instance.currentWindow.updateContent();
        }

        if (i == 2) {
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

            if (i > 1) {
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
