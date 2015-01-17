package com.ollieread.technomagi.tileentity.abstracts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.tileentity.component.ComponentInventory;

public abstract class TileEntityMachineInventory extends TileEntityMachine implements IInventory
{

    protected ComponentInventory inventory = null;

    public TileEntityMachineInventory(int capacity, int maxReceive, int maxExtract, IInventory inventory)
    {
        super(capacity, maxReceive, maxExtract);

        this.inventory = (ComponentInventory) inventory;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Inventory")) {
            inventory.readFromNBT(compound.getCompoundTag("Inventory"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound craftingCompound = new NBTTagCompound();
        NBTTagCompound resultCompound = new NBTTagCompound();

        inventory.writeToNBT(resultCompound);

        compound.setTag("Inventory", resultCompound);
    }

    protected void reduceStacks(int i)
    {
        for (int x = 0; x < getSizeInventory(); x++) {
            decrStackSize(x, i);
        }
    }

    public ComponentInventory getInventory()
    {
        return inventory;
    }

    /* Everything below is just a proxy for the interfaces */

    /* INVENTORY */

    @Override
    public int getSizeInventory()
    {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int i, int q)
    {
        return inventory.decrStackSize(i, q);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return inventory.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack)
    {
        inventory.setInventorySlotContents(i, stack);
    }

    @Override
    public String getInventoryName()
    {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return inventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return owner.isOwner(player.getCommandSenderName());
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
        return inventory.isItemValidForSlot(i, stack);
    }

}
