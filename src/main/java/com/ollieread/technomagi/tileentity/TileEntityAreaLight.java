package com.ollieread.technomagi.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.init.Blocks;

public class TileEntityAreaLight extends TileEntityInventory
{

    protected boolean on = true;

    public TileEntityAreaLight()
    {
        super(1, 1);

        setInventoryName("areaLight");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        compound.setBoolean("On", on);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        on = compound.getBoolean("On");
    }

    public boolean isOn()
    {
        return on;
    }

    public void toggleStatus()
    {
        if (on) {
            on = false;
        } else {
            on = true;

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
