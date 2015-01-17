package com.ollieread.technomagi.tileentity.abstracts;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.tileentity.component.IStorage;
import com.ollieread.technomagi.tileentity.component.Storage;

public class MachineStorage extends Basic implements IStorage
{
    protected Storage storage = new Storage(4096);
    protected int waiting = 0;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        waiting = compound.getInteger("Waiting");

        storage.readFromNBT(compound.getCompoundTag("Storage"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Waiting", waiting);

        NBTTagCompound storageCompound = new NBTTagCompound();

        storage.writeToNBT(storageCompound);

        compound.setTag("Storage", storageCompound);
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

    public boolean shouldRenderInPass(int pass)
    {
        return pass == 0 || pass == 1;
    }

    /* STORAGE */

    @Override
    public void setItem(ItemStack item)
    {
        storage.setItem(item);
    }

    @Override
    public void setAmount(int c)
    {
        storage.setAmount(c);
    }

    @Override
    public int deposit(ItemStack item, boolean doDeposit)
    {
        return storage.deposit(item, doDeposit);
    }

    @Override
    public int deposit(ItemStack item, int count, boolean doDeposit)
    {
        return storage.deposit(item, count, doDeposit);
    }

    @Override
    public ItemStack withdraw(boolean doWithdrawal)
    {
        return storage.withdraw(doWithdrawal);
    }

    @Override
    public ItemStack withdraw(boolean doWithdrawal, int count)
    {
        return storage.withdraw(doWithdrawal, count);
    }

    @Override
    public String getLocalizedName()
    {
        return storage.getLocalizedName();
    }

    @Override
    public String getUnlocalizedName()
    {
        return storage.getUnlocalizedName();
    }

    @Override
    public boolean isStackEqual(ItemStack compare)
    {
        return storage.isStackEqual(compare);
    }

    @Override
    public void setCapacity(int c)
    {
        storage.setCapacity(c);
    }

    @Override
    public ItemStack getItem()
    {
        return storage.getItem();
    }

    @Override
    public int getAmount()
    {
        return storage.getAmount();
    }

    @Override
    public int getCapacity()
    {
        return storage.getCapacity();
    }
}
