package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.tileentity.TileEntityCrafting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCrafting extends Container
{

    private TileEntityCrafting crafter;
    private int lastProgress;

    public ContainerCrafting(InventoryPlayer playerInventory, TileEntityCrafting tile)
    {
        crafter = tile;

        addSlotToContainer(new Slot(crafter, 0, 36, 26));
        addSlotToContainer(new Slot(crafter, 1, 53, 26));
        addSlotToContainer(new Slot(crafter, 2, 70, 26));
        addSlotToContainer(new Slot(crafter, 3, 36, 43));
        addSlotToContainer(new Slot(crafter, 4, 53, 43));
        addSlotToContainer(new Slot(crafter, 5, 70, 43));
        addSlotToContainer(new Slot(crafter, 6, 36, 60));
        addSlotToContainer(new Slot(crafter, 7, 53, 60));
        addSlotToContainer(new Slot(crafter, 8, 70, 60));

        addSlotToContainer(new Slot(crafter, 9, 123, 27));

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
