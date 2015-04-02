package com.ollieread.technomagi.common.block.energy.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.common.block.tile.ITileProcessor;
import com.ollieread.technomagi.common.component.Inventory;
import com.ollieread.technomagi.common.component.Progress;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileGeneratorEnhanced extends TileGenerator implements ITileProcessor
{

    protected Inventory inventory;
    protected int perTick = 5;
    protected ForgeDirection direction;
    protected Progress progress = new Progress();
    protected boolean isProcessing = false;

    public TileGeneratorEnhanced()
    {
        super(2048, 10, 10);

        inventory = new Inventory("enhanced_generator", 2);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (progress.getProgress() <= 0) {
                ItemStack fuelStack = getStackInSlot(0);

                if (fuelStack != null) {
                    int value = ItemStackHelper.getFuelValue(fuelStack);
                    progress.setMaxProgress(value);
                    progress.setProgress(value);

                    if (progress.getMaxProgress() > 0) {
                        decrStackSize(0, 1);
                    }
                }
            }

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
    public boolean canProcess()
    {
        return (this.getMaxEnergyStored(null) - (this.getEnergyStored(null) - perTick)) > perTick && progress.getProgress() > 0;
    }

    @Override
    public void process()
    {
        progress.decrementProgress();
        this.modifyEnergyStored(+perTick);
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
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound progressCompound = new NBTTagCompound();
        progress.writeToNBT(progressCompound);
        compound.setTag("Progress", progressCompound);

        compound.setBoolean("Processing", isProcessing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        progress.readFromNBT(compound.getCompoundTag("Progress"));

        isProcessing = compound.getBoolean("Processing");
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return slot == 0 ? ItemStackHelper.fuelSlot.isItemStackApplicable(stack) : ItemStackHelper.energyContainerSlot.isItemStackApplicable(stack);
    }

    @Override
    public Container getContainer(EntityPlayer player)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Window getWindow(EntityPlayer player)
    {
        return null;
    }

}
