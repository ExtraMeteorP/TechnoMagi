package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.common.proxy.BasicInventory;
import com.ollieread.technomagi.common.proxy.ItemStorage;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public class TileEntityStorage extends TileEntityTM implements IPlayerLocked, IInventory
{

    protected PlayerLocked locked = new PlayerLocked();
    protected BasicInventory inventory = new BasicInventory(1);
    protected ItemStorage storage = new ItemStorage(4096);

    protected int waiting = 0;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        inventory.readFromNBT(compound);
        locked.readFromNBT(compound);
        storage.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        inventory.writeToNBT(compound);
        locked.writeToNBT(compound);
        storage.writeToNBT(compound);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (waiting > 0) {
                waiting++;

                if (waiting == 20) {
                    waiting = 0;
                }
            }
        }
    }

    public void setWaiting(int w)
    {
        waiting = w;
    }

    public boolean isWaiting()
    {
        return (waiting > 0 && waiting < 20);
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
        return inventory.isUseableByPlayer(player);
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

    /* LOCKED */

    @Override
    public boolean hasPlayer()
    {
        return locked.hasPlayer();
    }

    @Override
    public void setPlayer(String name)
    {
        locked.setPlayer(name);
    }

    @Override
    public String getPlayer()
    {
        return locked.getPlayer();
    }

    @Override
    public boolean isPlayer(String name)
    {
        return locked.isPlayer(name);
    }

    public boolean isPlayer(EntityPlayer player)
    {
        return isPlayer(player.getCommandSenderName());
    }

    /* Storage */
    public int deposit(ForgeDirection from, ItemStack resource, boolean doDeposit)
    {
        return storage.deposit(resource, doDeposit);
    }

    public int deposit(ForgeDirection from, ItemStack resource, int count, boolean doDeposit)
    {
        return storage.deposit(resource, count, doDeposit);
    }

    public ItemStack withdraw(ForgeDirection from, boolean doWithdraw)
    {
        return storage.withdraw(doWithdraw);
    }

    public boolean canDeposit(ForgeDirection from, ItemStack resource)
    {
        return true;
    }

    public boolean canWithdraw(ForgeDirection from, ItemStack resource)
    {
        return true;
    }

    public ItemStack getItem()
    {
        return storage.getItem();
    }

    public int getAmount()
    {
        return storage.getAmount();
    }

    public int getCapacity()
    {
        return storage.getCapacity();
    }

    public void setCapacity(int capacity)
    {
        storage.setCapacity(capacity);
    }

}
