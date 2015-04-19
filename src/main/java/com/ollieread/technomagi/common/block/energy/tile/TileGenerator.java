package com.ollieread.technomagi.common.block.energy.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.technomagi.api.tile.ISideFacing;
import com.ollieread.technomagi.api.tile.ITileGui;
import com.ollieread.technomagi.api.tile.ITileProcessor;
import com.ollieread.technomagi.common.component.Inventory;
import com.ollieread.technomagi.common.component.Progress;

public abstract class TileGenerator extends TileEnergy implements IInventory, ISideFacing, ITileGui, ITileProcessor
{

    protected Inventory inventory;
    protected int perTick;
    protected ForgeDirection direction;
    protected Progress progress = new Progress();
    protected boolean isProcessing = false;

    public TileGenerator(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract);

        createInventory();
    }

    public abstract void createInventory();

    @Override
    public ForgeDirection[] getValidDirections()
    {
        if (direction != null) {
            return new ForgeDirection[] { direction.getOpposite() };
        }

        return new ForgeDirection[] {};
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return 0;
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (canProcess()) {
                isProcessing = true;
                process();
            } else {
                isProcessing = false;
            }

            ItemStack charge = this.getStackInSlot(1);

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

        if (direction != null) {
            compound.setInteger("Direction", direction.ordinal());
        }

        NBTTagCompound inventoryCompound = new NBTTagCompound();
        inventory.writeToNBT(inventoryCompound);
        compound.setTag("Inventory", inventoryCompound);

        NBTTagCompound progressCompound = new NBTTagCompound();
        progress.writeToNBT(progressCompound);
        compound.setTag("Progress", progressCompound);

        compound.setBoolean("Processing", isProcessing);
        compound.setInteger("PerTick", perTick);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Direction")) {
            direction = ForgeDirection.values()[compound.getInteger("Direction")];
        }
        progress.readFromNBT(compound.getCompoundTag("Progress"));

        isProcessing = compound.getBoolean("Processing");
        perTick = compound.getInteger("PerTick");
    }

    @Override
    public boolean isProcessing()
    {
        return this.isProcessing;
    }

    @Override
    public int getProgress()
    {
        return progress.getProgress();
    }

    public int getMaxProgress()
    {
        return progress.getMaxProgress();
    }

    public void setMaxProgress(int maxProgress)
    {
        this.progress.setMaxProgress(maxProgress);
    }

    public void setProgress(int progress)
    {
        this.progress.setProgress(progress);
    }

    @Override
    public int getProgressScaled(int scale)
    {
        return progress.getMaxProgress() > 0 ? progress.getProgress() * scale / progress.getMaxProgress() : 0;
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
    public void setDirection(ForgeDirection direction)
    {
        this.direction = direction;
    }

    @Override
    public ForgeDirection getDirection()
    {
        return direction;
    }

    @Override
    public void rotate()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;

        for (ForgeDirection direction : directions) {
            if (direction.equals(getDirection()) || direction.equals(ForgeDirection.UP) || direction.equals(ForgeDirection.DOWN)) {
                continue;
            }

            setDirection(direction);
            markDirty();
            break;
        }
    }

}
