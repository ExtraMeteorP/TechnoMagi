package com.ollieread.technomagi.tileentity;

import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyStorage;

import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemSampleVile;
import com.ollieread.technomagi.tileentity.proxy.BasicEnergy;
import com.ollieread.technomagi.tileentity.proxy.BasicInventory;
import com.ollieread.technomagi.tileentity.proxy.PlayerLocked;

public class TileEntityNaniteReplicator extends TileEntityTM implements IPlayerLocked, ISidedInventory, IEnergyStorage, IEnergyConnection
{

    protected BasicEnergy storage = null;
    protected PlayerLocked locked = null;
    protected BasicInventory inventory = null;

    protected int nanites = 0;
    protected int sample = 0;
    protected String naniteType = "none";
    protected String sampleType = "none";
    protected int progress = 0;
    protected int working = 0;
    protected Random rand = new Random();

    public TileEntityNaniteReplicator()
    {
        locked = new PlayerLocked();
        inventory = new BasicInventory(3);
        storage = new BasicEnergy(3200);
    }

    public int getNanites()
    {
        return nanites;
    }

    public int getSample()
    {
        return sample;
    }

    public String getNaniteType()
    {
        return naniteType;
    }

    public String getSampleType()
    {
        return sampleType;
    }

    public int getProgress()
    {
        return progress;
    }

    public void setNanites(int nanites)
    {
        this.nanites = nanites;
    }

    public void setSample(int sample)
    {
        this.sample = sample;
    }

    public void setNaniteType(String naniteType)
    {
        this.naniteType = naniteType;
    }

    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }

    public void setProgress(int progress)
    {
        this.progress = progress;
    }

    @Override
    public void updateEntity()
    {
        populate();

        if (canReplicate() && !worldObj.isRemote) {
            replicate();
        }
    }

    protected void replicate()
    {
        boolean flag = false;
        if (sampleType.equals(naniteType)) {
            if (storage.extractEnergy(2, true) == 2) {
                storage.extractEnergy(2, false);
                working++;

                if (working >= 60) {
                    working = 0;
                    sample--;
                    nanites--;
                    progress++;
                    flag = true;
                }
            }
        } else {
            if (naniteType.equals("player")) {

                if (storage.extractEnergy(5, true) == 5) {
                    storage.extractEnergy(5, false);
                    working++;

                    if (working >= 100) {
                        working = 0;
                        sample--;
                        nanites--;
                        flag = true;

                        if (rand.nextInt(5) == 0) {
                            progress++;
                        }
                    }
                }
            }
        }

        if (flag) {
            if (progress == 100) {
                ItemStack result = getStackInSlot(1);
                ItemStack input = getStackInSlot(2);

                if (input != null && input.getItem() instanceof ItemNaniteContainer && ItemNaniteContainer.getEntity(input) == null) {
                    if (result == null) {
                        ItemStack output = new ItemStack(Items.itemNaniteContainer, 1);
                        ItemNaniteContainer.setEntity(output, (Class) EntityList.stringToClassMapping.get(sampleType));
                        setInventorySlotContents(1, output);
                        decrStackSize(1, 1);
                        progress = 0;
                    } else {
                        if (result.getItem() instanceof ItemNaniteContainer && ItemNaniteContainer.getEntity(result).equals(sampleType)) {
                            if (result.stackSize < result.getMaxStackSize()) {
                                result.stackSize++;
                                decrStackSize(1, 1);
                                progress = 0;
                            }
                        }
                    }
                }
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    protected boolean canReplicate()
    {
        /*
         * System.out.println(sample > 0); System.out.println(nanites > 0);
         * System.out.println(progress < 100); System.out.println(sampleType ==
         * naniteType); System.out.println(naniteType == 1);
         */
        return sample > 0 && nanites > 0 && progress < 100 && (sampleType == naniteType || naniteType.equals("player"));
    }

    protected void populate()
    {
        boolean update = false;

        if (!worldObj.isRemote && getStackInSlot(0) != null) {
            ItemStack input = getStackInSlot(0);

            if (input.getItem() instanceof ItemNaniteContainer && ItemNaniteContainer.getEntity(input) != null && nanites < 91) {
                String entity = ItemNaniteContainer.getEntity(input);

                if (!naniteType.equals("none") && entity != null && naniteType.equals(entity)) {
                    if (nanites <= 90) {
                        nanites += 10;
                    } else {
                        nanites = 100;
                    }

                    getStackInSlot(0).stackSize--;
                    update = true;

                    if (getStackInSlot(0).stackSize == 0) {
                        setInventorySlotContents(0, null);
                    }

                } else if (naniteType.equals("none")) {
                    naniteType = ItemNaniteContainer.getEntity(input);
                    nanites = 10;

                    getStackInSlot(0).stackSize--;
                    update = true;

                    if (getStackInSlot(0).stackSize == 0) {
                        setInventorySlotContents(0, null);
                    }
                }
            } else if (input.getItem() instanceof ItemSampleVile && ItemSampleVile.getEntity(input) != null && sample < 91) {
                String entity = ItemNaniteContainer.getEntity(input);

                if (!sampleType.equals("none") && sampleType.equals(entity)) {
                    if (sample <= 90) {
                        sample += 10;
                    } else {
                        sample = 100;
                    }

                    getStackInSlot(0).stackSize--;
                    update = true;

                    if (getStackInSlot(0).stackSize == 0) {
                        setInventorySlotContents(0, null);
                    }

                } else if (sampleType.equals("none")) {
                    sampleType = entity;
                    sample = 10;

                    getStackInSlot(0).stackSize--;
                    update = true;

                    if (getStackInSlot(0).stackSize == 0) {
                        setInventorySlotContents(0, null);
                    }
                }
            }
        }

        if (update) {
            markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        nanites = compound.getInteger("Nanites");
        sample = compound.getInteger("Sample");
        naniteType = compound.getString("NaniteType");
        sampleType = compound.getString("SampleType");
        progress = compound.getInteger("Progress");
        working = compound.getInteger("Working");

        locked.readFromNBT(compound);
        inventory.readFromNBT(compound);
        storage.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Nanites", nanites);
        compound.setInteger("Sample", sample);
        compound.setString("NaniteType", naniteType);
        compound.setString("SampleType", sampleType);
        compound.setInteger("Progress", progress);
        compound.setInteger("Working", working);

        locked.writeToNBT(compound);
        inventory.writeToNBT(compound);
        storage.writeToNBT(compound);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return var1 == 1 ? new int[] { 2 } : new int[] { 0, 2 };
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
        return storage.canConnectEnergy(from);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored()
    {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored()
    {
        return storage.getMaxEnergyStored();
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
