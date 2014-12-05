package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.proxy.BasicEnergy;
import com.ollieread.technomagi.common.proxy.BasicInventory;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public class TileEntityFocuser extends TileEntityMachineTM implements IInventory
{

    protected BasicInventory inventory = null;
    protected int multiplier = 0;

    public TileEntityFocuser()
    {
        locked = new PlayerLocked();
        inventory = new BasicInventory(3);
        storage = new BasicEnergy(Config.focuserPowerMax, Config.focuserPowerRecieve, 0);

        maxProgress = Config.focuserProgressMax;
        usage = Config.focuserPowerUse;
    }

    public int getProgress()
    {
        return progress;
    }

    public void setProgress(int i)
    {
        progress = i;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        inventory.readFromNBT(compound);

        multiplier = compound.getInteger("Multiplier");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        inventory.writeToNBT(compound);

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

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (!worldObj.isRemote) {
            checkForMultiplier();

            if (canFocus()) {
                focus();
            } else {
                ItemStack stack = inventory.getStackInSlot(0);

                if (stack == null) {
                    progress = 0;
                }
            }

            if (EnergyHelper.isAdjacentEnergyHandlerFromSide(this, ForgeDirection.DOWN.ordinal())) {
                int input = storage.getMaxReceive();
                int receive = storage.receiveEnergy(ForgeDirection.DOWN, input, true);
                int extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, true);

                if (receive > 0 && extract > 0) {

                    if (receive == extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, false);
                    } else if (receive > extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), extract, false);
                    } else if (receive < extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, false);
                    }
                    receiveEnergy(ForgeDirection.DOWN, extract, false);
                }
            }
        }
    }

    public boolean canFocus()
    {
        if (getPlayer() != null) {
            EntityPlayer player = worldObj.getPlayerEntityByName(getPlayer());
            ItemStack input = inventory.getStackInSlot(0);
            ItemStack output = inventory.getStackInSlot(1);

            if (this.getEnergyStored(null) > usage) {
                if (input != null && input.isItemEqual(new ItemStack(Items.itemEtherium))) {
                    if (output == null || (output.isItemEqual(new ItemStack(Items.itemRelux)) && output.stackSize < output.getMaxStackSize())) {
                        if (worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && worldObj.isDaytime()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public void focus()
    {
        if (storage.modifyEnergyStored(usage)) {
            progress++;
            progress += multiplier;

            if (progress >= maxProgress) {
                ItemStack input = getStackInSlot(0);
                ItemStack output = getStackInSlot(1);

                input.stackSize--;

                if (input.stackSize == 0) {
                    input = null;
                }

                if (output == null) {
                    output = new ItemStack(Items.itemRelux);
                } else {
                    output.stackSize++;
                }

                setInventorySlotContents(0, input);
                setInventorySlotContents(1, output);

                progress = 0;
            }
        }
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

    /* ENERGY */

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.DOWN);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        int r = storage.receiveEnergy(ForgeDirection.DOWN, maxReceive, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        int r = storage.extractEnergy(ForgeDirection.DOWN, maxExtract, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return storage.getEnergyStored(null);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return storage.getMaxEnergyStored(null);
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

}