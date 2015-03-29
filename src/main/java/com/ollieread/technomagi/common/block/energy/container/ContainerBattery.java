package com.ollieread.technomagi.common.block.energy.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.common.block.energy.tile.TileBattery;
import com.ollieread.technomagi.common.inventory.ContainerTechnomagi;
import com.ollieread.technomagi.common.inventory.SlotApplicable;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerBattery extends ContainerTechnomagi
{

    public TileBattery tile;
    public int lastEnergy;

    public ContainerBattery(IInventory playerInventory, TileBattery tile)
    {
        this.tile = tile;

        addSlotToContainer(new SlotApplicable(tile, 0, 7, 30, ItemStackHelper.energyContainerSlot));
        addPlayerSlots(playerInventory, 8, 45);
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting)
    {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, tile.getEnergyStored(null));
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
        }

        lastEnergy = tile.getEnergyStored(null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int i, int v)
    {
        if (i == 0) {
            tile.setEnergyStored(v);
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

            if (i > 0) {
                if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
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
