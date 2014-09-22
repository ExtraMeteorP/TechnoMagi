package com.ollieread.technomagi.tileentity;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyStorage;
import cofh.lib.util.helpers.EnergyHelper;

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

    public int field_145926_a;
    public float field_145933_i;
    public float field_145931_j;
    public float field_145932_k;
    public float field_145929_l;
    public float field_145930_m;
    public float field_145927_n;
    public float field_145928_o;
    public float field_145925_p;
    public float field_145924_q;
    private static Random field_145923_r = new Random();

    protected Random rand = new Random();

    protected int powerUsage = 5;

    public TileEntityAnalysis()
    {
        locked = new PlayerLocked();
        inventory = new BasicInventory(9);
        storage = new BasicEnergy(3200, 2, 0);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        locked.readFromNBT(compound);
        inventory.readFromNBT(compound);
        storage.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        locked.writeToNBT(compound);
        inventory.writeToNBT(compound);
        storage.writeToNBT(compound);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        this.field_145927_n = this.field_145930_m;
        this.field_145925_p = this.field_145928_o;
        this.field_145924_q += 0.02F;
        this.field_145930_m -= 0.1F;

        while (this.field_145928_o >= (float) Math.PI) {
            this.field_145928_o -= ((float) Math.PI * 2F);
        }

        while (this.field_145928_o < -(float) Math.PI) {
            this.field_145928_o += ((float) Math.PI * 2F);
        }

        while (this.field_145924_q >= (float) Math.PI) {
            this.field_145924_q -= ((float) Math.PI * 2F);
        }

        while (this.field_145924_q < -(float) Math.PI) {
            this.field_145924_q += ((float) Math.PI * 2F);
        }

        float f2;

        for (f2 = this.field_145924_q - this.field_145928_o; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
            ;
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        this.field_145928_o += f2 * 0.4F;

        if (this.field_145930_m < 0.0F) {
            this.field_145930_m = 0.0F;
        }

        if (this.field_145930_m > 1.0F) {
            this.field_145930_m = 1.0F;
        }

        ++this.field_145926_a;
        this.field_145931_j = this.field_145933_i;
        float f = (this.field_145932_k - this.field_145933_i) * 0.4F;
        float f3 = 0.2F;

        if (f < -f3) {
            f = -f3;
        }

        if (f > f3) {
            f = f3;
        }

        this.field_145929_l += (f - this.field_145929_l) * 0.9F;
        this.field_145933_i += this.field_145929_l;

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

            if (EnergyHelper.isAdjacentEnergyHandlerFromSide(this, ForgeDirection.DOWN.ordinal())) {
                int input = storage.getMaxReceive();
                int receive = storage.receiveEnergy(input, true);
                int extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, true);

                if (receive > 0 && extract > 0) {

                    if (receive == extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, false);
                    } else if (receive > extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), extract, false);
                    } else if (receive < extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, false);
                    }
                    receiveEnergy(extract, false);

                    sync();
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

            return getEnergyStored() > powerUsage;
        }

        return false;
    }

    public void analyse()
    {
        if (storage.modifyEnergyStored(powerUsage)) {
            ticks++;

            if (ticks >= 20) {
                ticks = 0;
                progress++;
            }

            if (progress >= 100) {
                IResearchAnalysis analysis = getResearchAnalysis();
                int c = rand.nextInt(((IResearch) analysis).getChance()) + 1;

                IResearch research = (IResearch) analysis;
                EntityPlayer player = worldObj.getPlayerEntityByName(getPlayer());

                ResearchRegistry.researchAnalysis(Arrays.asList(inventory.getInventory()), ExtendedPlayerKnowledge.get(player), this, c);

                data += research.getProgress();

                reduceStacks(1);
                setInProgress(false);
                progress = 0;
            }

            sync();
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
        return from.equals(ForgeDirection.DOWN);
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
