package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.api.block.TileEntityPlayerLocked;
import com.ollieread.technomagi.common.init.Items;

public class TileEntityNaniteReplicator extends TileEntityPlayerLocked implements ISidedInventory
{

    protected ItemStack[] stacks = new ItemStack[3];
    protected String name;
    protected int nanites = 0;
    protected int sample = 0;
    protected int type = -1;

    @Override
    public int getSizeInventory()
    {
        return 3;
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
        return stacks[var1];
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2)
    {
        if (stacks[var1] != null) {
            ItemStack stack;
            if (stacks[var1].stackSize <= var2) {
                stack = stacks[var1];
                stacks[var1] = null;

                return stack;
            } else {
                stack = stacks[var1].splitStack(var2);

                if (stacks[var1].stackSize == 0) {
                    stacks[var1] = null;
                }

                return stack;
            }
        }

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        if (stacks[var1] != null) {
            ItemStack itemstack = stacks[var1];
            stacks[var1] = null;
            return itemstack;
        }

        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        stacks[var1] = var2;

        if (var2 != null && var2.stackSize > getInventoryStackLimit()) {
            var2.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
        return hasCustomInventoryName() ? name : "container.furnace";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return name != null && name.length() > 0;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return isPlayer(player);
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
    public boolean isItemValidForSlot(int var1, ItemStack var2)
    {
        if (var1 == 0) {
            if (var2.isItemEqual(new ItemStack(Items.itemSampleVile))) {
                if (var2.getItemDamage() > 0) {
                    return true;
                }
            }
        } else if (var1 == 1) {

        } else if (var1 == 2) {

        }

        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return var1 == 1 ? new int[] { 1 } : new int[] { 0, 2 };
    }

    @Override
    public boolean canInsertItem(int var1, ItemStack var2, int var3)
    {
        return isItemValidForSlot(var1, var2);
    }

    @Override
    public boolean canExtractItem(int var1, ItemStack var2, int var3)
    {
        return var3 != 0 || var1 != 1;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
    }

}
