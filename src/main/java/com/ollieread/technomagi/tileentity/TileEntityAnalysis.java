package com.ollieread.technomagi.tileentity;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.proxy.BasicEnergy;
import com.ollieread.technomagi.common.proxy.InventoryBasic;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public class TileEntityAnalysis extends TileEntityResearch implements IPlayerLocked, IInventory, IEnergyHandler
{
    protected BasicEnergy storage = null;
    protected PlayerLocked locked = null;
    protected InventoryBasic inventory = null;

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

    protected int maxProgress;
    protected int maxWaiting;
    protected int usage;

    public TileEntityAnalysis()
    {
        locked = new PlayerLocked();
        inventory = new InventoryBasic(9);
        storage = new BasicEnergy(Config.analysisPowerMax, Config.analysisPowerRecieve, 0);

        maxProgress = Config.analysisProgressMax;
        maxWaiting = Config.analysisWaitingMax;
        usage = Config.analysisPowerUse;
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

                if (waiting == maxWaiting) {
                    waiting = 0;
                    setInProgress(false);
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
    }

    public int getProgress(int width)
    {
        return progress / (maxProgress / width);
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

                if (!charon.canSpecialise()) {
                    if (charon.hasResearched(research.getName()) || charon.hasKnowledge(research.getKnowledge())) {
                        return false;
                    }

                    if (player != null && research.getProgress() + data > 100 && research.canPerform(charon)) {
                        return false;
                    }
                }
            } else if (!inProgress) {
                return false;
            }

            return getEnergyStored(null) > usage;
        }

        return false;
    }

    public void analyse()
    {
        if (storage.modifyEnergyStored(usage)) {
            progress++;

            if (progress >= maxProgress) {
                IResearchAnalysis analysis = getResearchAnalysis();
                int c = rand.nextInt(((IResearch) analysis).getChance()) + 1;

                IResearch research = (IResearch) analysis;
                EntityPlayer player = worldObj.getPlayerEntityByName(getPlayer());
                ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

                if (rand.nextInt(research.getChance()) == 0 && !charon.hasResearched(research.getName()) && research.canPerform(charon)) {
                    addResearch(research.getKnowledge(), research.getProgress());

                    if (!research.isRepeating()) {
                        charon.addNonRepeatibleResearch(research.getKnowledge());
                    }

                    reduceStacks(1);
                }

                setInProgress(false);
                progress = 0;
            }

            sync();
        }
    }

    private IResearchAnalysis getResearchAnalysis()
    {
        return ResearchRegistry.findMatchingAnalysis(inventory);
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
