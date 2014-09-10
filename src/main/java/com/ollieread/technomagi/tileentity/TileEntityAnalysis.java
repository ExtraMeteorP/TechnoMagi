package com.ollieread.technomagi.tileentity;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyStorage;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.proxy.BasicEnergy;
import com.ollieread.technomagi.common.proxy.BasicInventory;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public class TileEntityAnalysis extends TileEntityResearch implements IPlayerLocked, IInventory, IEnergyStorage, IEnergyConnection
{
    protected BasicEnergy storage = null;
    protected PlayerLocked locked = null;
    protected BasicInventory inventory = null;

    protected Random rand = new Random();

    public TileEntityAnalysis()
    {
        locked = new PlayerLocked();
        inventory = new BasicInventory(9);
        storage = new BasicEnergy(3200);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (canAnalyse()) {
                if (inProgress()) {
                    analyse();
                }
            } else {
                waiting++;

                if (waiting == 30) {
                    waiting = 0;
                    setInProgress(false);
                }
            }
        }
    }

    public boolean canAnalyse()
    {
        if (data == 100) {
            return false;
        }
        IResearchAnalysis analysis = getResearchAnalysis();
        if (analysis != null) {
            IResearch research = (IResearch) analysis;
            EntityPlayer player = worldObj.getPlayerEntityByName(getPlayer());

            if (player != null) {
                ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

                if (charon.hasResearched(research.getName())) {
                    return false;
                }

                if (player != null && research.getProgress() + data > 100 && research.canPerform(charon)) {
                    return false;
                }
            } else if (!inProgress) {
                return false;
            }

            return true;
        }

        return false;
    }

    public void analyse()
    {
        ticks++;

        if (ticks >= 20) {
            ticks = 0;
            progress++;
        }

        if (progress >= 100) {
            IResearchAnalysis analysis = getResearchAnalysis();
            int c = rand.nextInt(analysis.getChance()) + 1;

            IResearch research = (IResearch) analysis;
            EntityPlayer player = worldObj.getPlayerEntityByName(getPlayer());

            ResearchRegistry.researchAnalysis(Arrays.asList(inventory), ExtendedPlayerKnowledge.get(player), this, c);

            data += research.getProgress();

            reduceStacks(1);
            setInProgress(false);
            progress = 0;
        }
    }

    private IResearchAnalysis getResearchAnalysis()
    {
        return ResearchRegistry.findMatchingAnalysis(Arrays.asList(inventory.getInventory()));
    }

    protected void reduceStacks(int i)
    {
        for (int x = 0; x < getSizeInventory(); x++) {
            decrStackSize(x, i);
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
