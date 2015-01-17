package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachineInventory;
import com.ollieread.technomagi.tileentity.component.ComponentInventory;
import com.ollieread.technomagi.util.ItemHelper;

public class TileEntityMachineInfuser extends TileEntityMachineInventory
{

    protected int multiplier = 0;

    public TileEntityMachineInfuser()
    {
        super(Config.focuserPowerMax, Config.focuserPowerRecieve, 0, new ComponentInventory(2));

        setMaxProgress(Config.focuserProgressMax);
        setUsage(Config.focuserPowerUse);
    }

    @Override
    public Container getContainer(InventoryPlayer playerInventory, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getGui(World world, int x, int y, int z, EntityPlayer player)
    {
        return -1;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        multiplier = compound.getInteger("Multiplier");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Multiplier", multiplier);
    }

    public void checkForMultiplier()
    {
        multiplier = 0;

        for (int x = xCoord - 2; x <= xCoord + 2; ++x) {
            for (int z = zCoord - 2; z <= zCoord + 2; ++z) {
                if (worldObj.getBlock(x, yCoord, z) == Blocks.blockFocusCharger) {
                    multiplier++;
                }

                if (multiplier >= Config.focuserMaxChargers) {
                    multiplier = Config.focuserMaxChargers;
                    break;
                }

            }
        }
    }

    public boolean canProcess()
    {
        EntityPlayer player = getOwner(worldObj);

        if (player != null) {
            ItemStack input = getStackInSlot(0);
            ItemStack output = getStackInSlot(1);

            if (getEnergyStored(null) > getUsage()) {
                if (input != null && input.isItemEqual(ItemHelper.resource("etheriumCyrstal", 1))) {
                    if (output == null || (output.isItemEqual(ItemHelper.resource("reluxCrystal", 1)) && output.stackSize < output.getMaxStackSize())) {
                        if (worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && worldObj.isDaytime()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean isProcessing()
    {
        return progress > 0;
    }

    public void process()
    {
        if (energy.modifyEnergyStored(usage)) {
            setProgress(getProgress() + 1);
            setProgress(getProgress() + multiplier);

            if (getProgress() >= getMaxProgress()) {
                ItemStack input = getStackInSlot(0);
                ItemStack output = getStackInSlot(1);

                input.stackSize--;

                if (input.stackSize == 0) {
                    input = null;
                }

                if (output == null) {
                    output = ItemHelper.resource("reluxCrystal", 1);
                } else {
                    output.stackSize++;
                }

                setInventorySlotContents(0, input);
                setInventorySlotContents(1, output);

                setProgress(0);
            }
        }
    }

}
