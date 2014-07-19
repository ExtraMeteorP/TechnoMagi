package com.ollieread.technomagi.tileentity;

import java.util.Random;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemSampleVile;

public class TileEntityNaniteReplicator extends TileEntityInventoryLocked implements ISidedInventory
{

    protected String name;
    protected int nanites = 0;
    protected int sample = 0;
    protected int naniteType = -1;
    protected int sampleType = -1;
    protected int progress = 0;
    protected int working = 0;
    protected Random rand = new Random();

    public TileEntityNaniteReplicator()
    {
        super(3);
    }

    public int getNanites()
    {
        return nanites;
    }

    public int getSample()
    {
        return sample;
    }

    public int getNaniteType()
    {
        return naniteType;
    }

    public int getSampleType()
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

    public void setNaniteType(int naniteType)
    {
        this.naniteType = naniteType;
    }

    public void setSampleType(int sampleType)
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
        } else {
            // System.out.println("Can't replicate");
        }
    }

    protected void replicate()
    {
        boolean flag = false;
        if (sampleType == naniteType) {
            working++;

            if (working >= 60) {
                working = 0;
                sample--;
                nanites--;
                progress++;
                flag = true;
            }
        } else {
            if (naniteType == 1) {
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

        if (flag) {
            if (progress == 100) {
                if (inventory[2] != null && inventory[2].isItemEqual(new ItemStack(Items.itemNaniteContainer)) && inventory[2].getItemDamage() == 0) {
                    if (inventory[1] != null) {
                        if (inventory[1].stackSize < inventory[1].getMaxStackSize()) {
                            decrStackSize(2, 1);
                            inventory[1].stackSize++;
                            progress = 0;
                        }
                    } else {
                        setInventorySlotContents(1, new ItemStack(Items.itemNaniteContainer, 1, sampleType - 1));
                        progress = 0;
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
        return sample > 0 && nanites > 0 && progress < 100 && (sampleType == naniteType || naniteType == 1);
    }

    protected void populate()
    {
        boolean update = false;

        if (!worldObj.isRemote && inventory[0] != null) {
            if (inventory[0].getItem() instanceof ItemNaniteContainer && inventory[0].getItemDamage() > 0 && nanites < 91) {
                if (naniteType > -1 && naniteType == inventory[0].getItemDamage()) {
                    if (nanites <= 90) {
                        nanites += 10;
                    } else {
                        nanites = 100;
                    }

                    inventory[0].stackSize--;
                    update = true;

                    if (inventory[0].stackSize == 0) {
                        inventory[0] = null;
                    }

                } else if (naniteType == -1) {
                    naniteType = inventory[0].getItemDamage();
                    nanites = 10;

                    inventory[0].stackSize--;
                    update = true;

                    if (inventory[0].stackSize == 0) {
                        inventory[0] = null;
                    }
                }
            } else if (inventory[0].getItem() instanceof ItemSampleVile && inventory[0].getItemDamage() > 0 && sample < 91) {
                if (sampleType > -1 && sampleType == inventory[0].getItemDamage()) {
                    if (sample <= 90) {
                        sample += 10;
                    } else {
                        sample = 100;
                    }

                    inventory[0].stackSize--;
                    update = true;

                    if (inventory[0].stackSize == 0) {
                        inventory[0] = null;
                    }

                } else if (sampleType == -1) {
                    sampleType = inventory[0].getItemDamage();
                    sample = 10;

                    inventory[0].stackSize--;
                    update = true;

                    if (inventory[0].stackSize == 0) {
                        inventory[0] = null;
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
        naniteType = compound.getInteger("NaniteType");
        sampleType = compound.getInteger("SampleType");
        progress = compound.getInteger("Progress");
        working = compound.getInteger("Working");

        if (compound.hasKey("CustomName", 8)) {
            name = compound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Nanites", nanites);
        compound.setInteger("Sample", sample);
        compound.setInteger("NaniteType", naniteType);
        compound.setInteger("SampleType", sampleType);
        compound.setInteger("Progress", progress);
        compound.setInteger("Working", working);

        if (this.hasCustomInventoryName()) {
            compound.setString("CustomName", name);
        }
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

}
