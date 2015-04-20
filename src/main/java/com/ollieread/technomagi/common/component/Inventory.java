package com.ollieread.technomagi.common.component;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Inventory implements IInventory
{

    protected String title;
    protected ItemStack[] inventoryContents;
    protected int slotCount;

    public Inventory(String title, int slots)
    {
        inventoryContents = new ItemStack[slots];
        slotCount = slots;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); i++) {
            if (getStackInSlot(i) != null) {
                NBTTagCompound tag = new NBTTagCompound();
                getStackInSlot(i).writeToNBT(tag);
                list.appendTag(tag);
            }
        }

        compound.setTag("Items", list);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList list = compound.getTagList("Items", 10);
        inventoryContents = new ItemStack[getSizeInventory()];

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            int slot = tag.getInteger("Slot");

            if (slot >= 0 && slot < getSizeInventory()) {
                this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tag));
            }
        }
    }

    @Override
    public int getSizeInventory()
    {
        return slotCount;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventoryContents[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (inventoryContents[slot] != null) {
            ItemStack itemstack;

            if (inventoryContents[slot].stackSize <= amount) {
                itemstack = inventoryContents[slot];
                inventoryContents[slot] = null;
                markDirty();
                return itemstack;
            } else {
                itemstack = inventoryContents[slot].splitStack(amount);

                if (inventoryContents[slot].stackSize == 0) {
                    inventoryContents[slot] = inventoryContents[slot].getItem().getContainerItem(inventoryContents[slot]);
                }

                markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.inventoryContents[slot] != null) {
            ItemStack itemstack = this.inventoryContents[slot];
            this.inventoryContents[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        this.inventoryContents[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return title;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void markDirty()
    {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void openInventory()
    {
    }

    @Override
    public void closeInventory()
    {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return true;
    }

}
