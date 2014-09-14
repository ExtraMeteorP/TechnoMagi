package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.init.Blocks;

public class TileEntityDisplacer extends TileEntityInventory
{

    protected boolean on;

    public TileEntityDisplacer()
    {
        super(1, 1);

        setInventoryName("displacer");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        on = compound.getBoolean("On");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("On", on);
    }

    public boolean isOn()
    {
        return on;
    }

    public void toggleStatus()
    {
        if (!worldObj.isRemote) {
            if (on) {
                on = false;
            } else {
                on = true;

                if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
                    worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockDisplacedAir);
                    TileEntityDisplacedAir air = (TileEntityDisplacedAir) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);

                    if (air != null) {
                        air.setMaster(xCoord, yCoord, zCoord);
                        air.spreadBlocks();
                    }
                }
            }
        }
    }

}
