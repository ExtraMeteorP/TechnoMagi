package com.ollieread.technomagi.tileentity.component;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class Crafting extends InventoryCrafting
{

    private int width;
    private static final String __OBFID = "CL_00001743";
    private String name;

    public Crafting(int width, int height, String name)
    {
        super(null, width, height);
        this.width = width;
        this.name = name;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack slot = getStackInSlot(i);
            if (slot != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                slot.writeToNBT(tag);
                list.appendTag(tag);
            }
        }

        if (this.hasCustomInventoryName()) {
            compound.setString("CustomName", name);
        }

        compound.setTag("Items", list);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList list = compound.getTagList("Items", 10);

        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            int slot = tag.getByte("Slot") & 255;

            if (slot >= 0 && slot < getSizeInventory()) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tag));
            }
        }

        if (compound.hasKey("CustomName", 8)) {
            name = compound.getString("CustomName");
        }
    }

}