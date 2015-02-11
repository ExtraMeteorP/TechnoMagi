package com.ollieread.technomagi.common.block.conduit.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.ollieread.technomagi.common.block.tile.TileBase;

public class TileConduitFluid extends TileBase implements IFluidHandler
{

    protected FluidTank tank;

    public TileConduitFluid()
    {
        tank = new FluidTank(0);
    }

    public TileConduitFluid(int capacity)
    {
        tank = new FluidTank(capacity);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        tank = new FluidTank(tag.getInteger("Capacity"));
        tank.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tank.writeToNBT(tag);
        tag.setInteger("Capacity", tank.getCapacity());
    }

    /* IFluidHandler */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
            return null;
        }
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { tank.getInfo() };
    }

    public FluidStack getFluid()
    {
        return tank.getFluid();
    }

    public int getFluidAmount()
    {
        return tank.getFluidAmount();
    }

    public int getCapacity()
    {
        return tank.getCapacity();
    }

    public void setCapacity(int capacity)
    {
        tank.setCapacity(capacity);
    }

}
