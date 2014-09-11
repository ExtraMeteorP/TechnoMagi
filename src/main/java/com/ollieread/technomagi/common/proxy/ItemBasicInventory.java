package com.ollieread.technomagi.common.proxy;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.item.ItemTMNBT;

public class ItemBasicInventory extends BasicInventory implements IInventory
{

    protected final ItemStack stack;

    public ItemBasicInventory(ItemStack stack, int size)
    {
        super(size);

        this.stack = stack;

        readFromNBT(getNBT());
    }

    private NBTTagCompound getNBT()
    {
        return ItemTMNBT.getNBT(stack);
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound inventoryCompound = new NBTTagCompound();
        super.writeToNBT(inventoryCompound);
        compound.setTag("Inventory", inventoryCompound);

        stack.stackTagCompound = compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagCompound inventoryCompound = (NBTTagCompound) compound.getTag("Inventory");

        if (inventoryCompound == null) {
            inventoryCompound = new NBTTagCompound();
        }

        super.readFromNBT(inventoryCompound);
    }

    @Override
    public void markDirty()
    {
        writeToNBT(getNBT());
    }

    public static class WithInventory extends ItemBasicInventory
    {

        public final int slot;
        public final IInventory inv;

        public WithInventory(IInventory inv, int slot, int size)
        {
            super(checkStack(inv, slot), size);
            this.slot = slot;
            this.inv = inv;
        }

        private static ItemStack checkStack(IInventory inv, int slot)
        {
            if (inv != null && inv.getStackInSlot(slot) != null) {
                return inv.getStackInSlot(slot);
            }

            return null;
        }

        @Override
        public void markDirty()
        {
            super.markDirty();
            inv.setInventorySlotContents(slot, stack);
        }

    }

}
