package com.ollieread.technomagi.tileentity.abstracts;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.technomagi.tileentity.ITileEntityMachine;
import com.ollieread.technomagi.tileentity.component.ComponentOwner;
import com.ollieread.technomagi.tileentity.component.ComponentPower;

public abstract class TileEntityMachine extends TileEntityBasic implements ITileEntityMachine
{

    protected ComponentPower energy;
    protected ComponentOwner owner;
    public int progress;
    public int maxProgress;
    public int usage;
    protected int facing;

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
    protected static Random rand = new Random();

    public TileEntityMachine(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public TileEntityMachine(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer);
    }

    public TileEntityMachine(int capacity, int maxReceive, int maxExtract)
    {
        energy = new ComponentPower(capacity, maxReceive, maxExtract);
        owner = new ComponentOwner();
    }

    public void setFacing(int side)
    {
        facing = side;
    }

    public int getFacing()
    {
        return facing;
    }

    public int getProgress(int width)
    {
        return progress / (maxProgress / width);
    }

    public int getProgress()
    {
        return progress;
    }

    public void setProgress(int progress)
    {
        this.progress = progress;
    }

    @Override
    public int getMaxProgress()
    {
        return maxProgress;
    }

    @Override
    public void setMaxProgress(int maxProgress)
    {
        this.maxProgress = maxProgress;
    }

    @Override
    public int getUsage()
    {
        return usage;
    }

    @Override
    public void setUsage(int usage)
    {
        this.usage = usage;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        facing = compound.getInteger("Facing");
        progress = compound.getInteger("Progress");

        owner.readFromNBT(compound.getCompoundTag("Owner"));
        energy.readFromNBT(compound.getCompoundTag("Energy"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Progress", progress);
        compound.setInteger("Facing", facing);

        NBTTagCompound ownerCompound = new NBTTagCompound();
        NBTTagCompound energyCompound = new NBTTagCompound();

        owner.writeToNBT(ownerCompound);
        energy.writeToNBT(energyCompound);

        compound.setTag("Owner", ownerCompound);
        compound.setTag("Energy", energyCompound);
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
            if (canProcess() && isProcessing()) {
                process();
            }

            if (EnergyHelper.isAdjacentEnergyHandlerFromSide(this, ForgeDirection.DOWN.ordinal())) {
                int input = energy.getMaxReceive();
                int receive = energy.receiveEnergy(ForgeDirection.DOWN, input, true);
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

    public void placed(EntityLivingBase entity, ItemStack stack)
    {
    }

    public boolean activated(EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        return false;
    }

    /* ENERGY */

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.DOWN) || from.equals(ForgeDirection.getOrientation(getBlockMetadata()).getOpposite());
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        int r = energy.receiveEnergy(from, maxReceive, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        int r = energy.extractEnergy(from, maxExtract, simulate);

        if (r > 0) {
            sync();
            return r;
        }

        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return energy.getEnergyStored(null);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return energy.getMaxEnergyStored(null);
    }

    /* OWNER */

    @Override
    public boolean isOwner(String name)
    {
        return owner.isOwner(name);
    }

    @Override
    public void setOwner(String name)
    {
        owner.setOwner(name);
    }

    @Override
    public EntityPlayer getOwner(World world)
    {
        return owner.getOwner(world);
    }

}
