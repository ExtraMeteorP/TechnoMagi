package com.ollieread.technomagi.tileentity;

import com.ollieread.technomagi.tileentity.abstracts.Basic;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityFiller extends Basic
{

    public int parentX;
    public int parentY;
    public int parentZ;

    public void setParent(int x, int y, int z)
    {
        if (!worldObj.isRemote) {
            parentX = x;
            parentY = y;
            parentZ = z;

            sync();
        }
    }

    public Block getParent()
    {
        return worldObj.getBlock(parentX, parentY, parentZ);
    }

    @Override
    public boolean canUpdate()
    {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        parentX = compound.getInteger("ParentX");
        parentY = compound.getInteger("ParentY");
        parentZ = compound.getInteger("ParentZ");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("ParentX", parentX);
        compound.setInteger("ParentY", parentY);
        compound.setInteger("ParentZ", parentZ);
    }

}
