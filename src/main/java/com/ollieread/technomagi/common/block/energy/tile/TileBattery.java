package com.ollieread.technomagi.common.block.energy.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.technomagi.client.gui.window.WindowBattery;
import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.common.block.energy.container.ContainerBattery;
import com.ollieread.technomagi.common.block.tile.ITileGui;
import com.ollieread.technomagi.common.component.Inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileBattery extends TileEnergy implements IInventory, ITileGui
{

    protected Inventory inventory;

    public TileBattery()
    {
        super(0);

        inventory = new Inventory("battery", 1);
    }

    public TileBattery(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract);

        inventory = new Inventory("battery", 1);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        if (from.equals(ForgeDirection.UP)) {
            return super.receiveEnergy(from, maxReceive, simulate);
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        if (from == null || !from.equals(ForgeDirection.UP)) {
            return super.extractEnergy(from, maxExtract, simulate);
        }

        return 0;
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            ItemStack charge = this.getStackInSlot(0);

            if (charge != null) {
                int amount = Math.min(this.getMaxExtract(), this.getEnergyStored(null));

                if (amount > 0) {
                    int actualAmount = this.extractEnergy(null, amount, true);

                    if (actualAmount > 0) {
                        this.extractEnergy(null, EnergyHelper.insertEnergyIntoContainer(charge, actualAmount, false), false);
                    }
                }
            }
        }

        super.updateEntity();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound inventoryCompound = new NBTTagCompound();
        inventory.writeToNBT(inventoryCompound);
        compound.setTag("Inventory", inventoryCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        inventory.readFromNBT(compound.getCompoundTag("Inventory"));
    }

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
    public ItemStack decrStackSize(int slot, int amount)
    {
        return inventory.decrStackSize(slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory.setInventorySlotContents(slot, stack);
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
        inventory.openInventory();
    }

    @Override
    public void closeInventory()
    {
        inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return stack.getItem() instanceof IEnergyContainerItem;
    }

    @Override
    public Container getContainer(EntityPlayer player)
    {
        return new ContainerBattery(player.inventory, this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Window getWindow(EntityPlayer player)
    {
        return new WindowBattery((ContainerBattery) getContainer(player));
    }

}
