package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.init.Blocks;

public class TileEntityAreaLight extends TileEntityInventory
{

    protected boolean status = true;

    public TileEntityAreaLight()
    {
        super(1, 1);

        setInventoryName("areaLight");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        compound.setBoolean("Status", status);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        status = compound.getBoolean("Status");
    }

    public boolean isOn()
    {
        return status;
    }

    public void toggleStatus()
    {
        if (status) {
            status = false;
        } else {
            status = true;

            if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
                worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockLightAir);
                worldObj.notifyBlockChange(xCoord, yCoord + 1, zCoord, Blocks.blockLightAir);
                TileEntityLightAir light = (TileEntityLightAir) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);

                if (light != null) {
                    light.setMaster(xCoord, yCoord, zCoord);
                    light.spreadBlocks();
                }
            }
        }
    }

}
