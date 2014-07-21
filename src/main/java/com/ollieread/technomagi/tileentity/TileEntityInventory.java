package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityInventory extends TileEntityTM implements IInventory
{

    protected ItemStack[] inventory;
    protected String name;
    protected int limit;

    public TileEntityInventory(int size)
    {
        this(size, 64);
    }

    public TileEntityInventory(int size, int max)
    {
        if (inventory == null) {
            inventory = new ItemStack[size];
            limit = max;
        }
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int q)
    {
        if (inventory[i] != null) {
            if (inventory[i].stackSize <= q) {
                ItemStack stack = inventory[i];
                inventory[i] = null;

                return stack;
            }

            ItemStack split = inventory[i].splitStack(q);
            if (inventory[i].stackSize == 0) {
                inventory[i] = null;
            }

            return split;
        }

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack)
    {
        inventory[i] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }

    }

    @Override
    public int getInventoryStackLimit()
    {
        return limit;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1)
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
    public boolean isItemValidForSlot(int i, ItemStack stack)
    {
        if (i < getSizeInventory()) {
            if (inventory[i] == null || stack.stackSize + inventory[i].stackSize <= getInventoryStackLimit()) {
                return true;
            }
        }

        return false;
    }

    public void setInventoryName(String name)
    {
        this.name = name;
    }

    public boolean hasCustomInventoryName()
    {
        return name != null;
    }

    @Override
    public String getInventoryName()
    {
        return name;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagList list = new NBTTagList();

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                inventory[i].writeToNBT(tag);
                list.appendTag(tag);
            }
        }

        compound.setTag("Items", list);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        NBTTagList list = compound.getTagList("Items", 10);
        inventory = new ItemStack[getSizeInventory()];

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            int slot = tag.getByte("Slot") & 255;

            if (slot >= 0 && slot < inventory.length) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }

}
