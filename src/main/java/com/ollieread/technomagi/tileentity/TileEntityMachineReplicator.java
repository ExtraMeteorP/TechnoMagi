package com.ollieread.technomagi.tileentity;

import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemSampleVile;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachineInventory;
import com.ollieread.technomagi.tileentity.component.ComponentInventory;

public class TileEntityMachineReplicator extends TileEntityMachineInventory implements ISidedInventory
{

    protected int nanites = 0;
    protected int sample = 0;
    protected String naniteType = "none";
    protected String sampleType = "none";
    protected int working = 0;
    protected Random rand = new Random();
    protected int maxWorkingEntity;
    protected int maxWorkingPlayer;

    public TileEntityMachineReplicator()
    {
        super(Config.replicatorPowerMax, Config.replicatorPowerRecieve, 0, new ComponentInventory(3));

        inventory = new ComponentInventory(3);

        setMaxProgress(Config.replicatorProgressMax);
        setUsage(Config.replicatorPowerUse);
        maxWorkingEntity = Config.replicatorWorkingEntityMax;
        maxWorkingPlayer = Config.replicatorWorkingPlayerMax;
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

    @Override
    public void updateEntity()
    {
        populate();

        if (!worldObj.isRemote) {
            if (canProcess()) {
                process();
            }
        }
    }

    public void process()
    {
        boolean flag = false;

        if (energy.modifyEnergyStored(usage)) {
            if (sampleType.equals(naniteType)) {
                // if (storage.extractEnergy(2, true) == 2) {
                // storage.extractEnergy(2, false);
                working++;

                if (working >= maxWorkingEntity) {
                    working = 0;
                    if (rand.nextInt(8) == 0) {
                        sample--;
                        nanites--;
                    }
                    progress++;
                    flag = true;
                }
                // }
            } else {
                if (naniteType.equals("player")) {
                    working++;

                    if (working >= maxWorkingPlayer) {
                        working = 0;
                        sample--;
                        nanites--;
                        flag = true;

                        progress++;
                    }
                }
            }
        }

        if (flag) {
            if (progress == maxProgress) {
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

            sync();
        }
    }

    public boolean canProcess()
    {
        /*
         * System.out.println(sample > 0); System.out.println(nanites > 0);
         * System.out.println(progress < 100); System.out.println(sampleType ==
         * naniteType); System.out.println(naniteType == 1);
         */
        return getEnergyStored(null) > usage && sample > 0 && nanites > 0 && progress < 100 && (sampleType == naniteType || naniteType.equals("player"));
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
            sync();
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
        working = compound.getInteger("Working");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Nanites", nanites);
        compound.setInteger("Sample", sample);
        compound.setString("NaniteType", naniteType);
        compound.setString("SampleType", sampleType);
        compound.setInteger("Working", working);
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
    public boolean isProcessing()
    {
        return working > 0;
    }

}
