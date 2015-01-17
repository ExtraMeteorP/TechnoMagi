package com.ollieread.technomagi.tileentity.component;

import com.ollieread.technomagi.tileentity.ITileEntityStorage;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class ComponentStorage implements ITileEntityStorage
{

    protected ItemStack stack;
    protected int amount = 0;
    protected int capacity = 0;
    protected NBTTagCompound tag;
    protected TileEntity tile;

    public ComponentStorage(int max)
    {
        this(max, 0, null);
    }

    public ComponentStorage(int max, int total)
    {
        this(max, total, null);
    }

    public ComponentStorage(int max, int total, NBTTagCompound nbt)
    {
        capacity = max;
        amount = total;

        if (nbt != null) {
            tag = nbt;
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        if (stack != null) {
            NBTTagCompound item = new NBTTagCompound();
            stack.writeToNBT(item);

            nbt.setTag("ItemStack", item);
            nbt.setInteger("Amount", amount);
            nbt.setInteger("Capacity", capacity);

            if (tag != null) {
                nbt.setTag("Tag", tag);
            }
        } else {
            nbt.setString("Empty", "");
        }

        return nbt;
    }

    public ComponentStorage readFromNBT(NBTTagCompound nbt)
    {
        if (!nbt.hasKey("Empty")) {
            stack = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("ItemStack"));
            amount = nbt.getInteger("Amount");
            capacity = nbt.getInteger("Capacity");

            if (nbt.hasKey("Tag")) {
                tag = (NBTTagCompound) nbt.getTag("Tag");
            }
        }

        return this;
    }

    public void setItem(ItemStack item)
    {
        stack = item;
        setAmount(item.stackSize);
    }

    public void setAmount(int c)
    {
        amount = c;
    }

    public void setCapacity(int c)
    {
        capacity = c;
    }

    public final ItemStack getItem()
    {
        return stack;
    }

    public final int getAmount()
    {
        return amount;
    }

    public final int getCapacity()
    {
        return capacity;
    }

    public int deposit(ItemStack item, boolean doDeposit)
    {
        return deposit(item, item.stackSize, doDeposit);
    }

    public int deposit(ItemStack item, int count, boolean doDeposit)
    {
        if (item == null) {
            return 0;
        }

        if (!doDeposit) {
            if (stack == null) {
                return Math.min(capacity, count);
            }

            if (!isStackEqual(item)) {
                return 0;
            }

            return Math.min(capacity - amount, count);
        }

        if (stack == null) {
            stack = item.copy();
            stack.stackSize = 1;
            setAmount(count);

            return getAmount();
        }

        if (!stack.isItemEqual(item)) {
            return 0;
        }

        int deposited = capacity - amount;

        if (count < deposited) {
            amount += count;
            deposited = count;
        } else {
            amount = capacity;
        }

        if (tile != null) {
            // FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluid,
            // tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, this,
            // filled));
        }

        return deposited;
    }

    public ItemStack withdraw(boolean doWithdrawal)
    {
        return withdraw(doWithdrawal, stack.getMaxStackSize());
    }

    public ItemStack withdraw(boolean doWithdrawal, int count)
    {
        if (stack == null) {
            return null;
        }

        int withdrawn = count;
        if (amount < withdrawn) {
            withdrawn = amount;
        }

        ItemStack withdraw = stack.copy();
        withdraw.stackSize = withdrawn;

        if (doWithdrawal) {
            amount -= withdrawn;

            if (amount <= 0) {
                stack = null;
            }

            if (tile != null) {
                // FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(fluid,
                // tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord,
                // this, drained));
            }
        }

        return withdraw;
    }

    public String getLocalizedName()
    {
        if (stack != null) {
            return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
        }

        return null;
    }

    public String getUnlocalizedName()
    {
        return stack.getUnlocalizedName();
    }

    public boolean isStackEqual(ItemStack compare)
    {
        return stack != null ? stack.isItemEqual(compare) : true;
    }
}
