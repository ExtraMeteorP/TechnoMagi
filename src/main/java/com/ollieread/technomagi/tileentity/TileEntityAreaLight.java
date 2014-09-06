package com.ollieread.technomagi.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.proxy.Disguisable;

public class TileEntityAreaLight extends TileEntityTM implements IDisguisableTile
{

    protected Disguisable disguise = new Disguisable();

    protected boolean on;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        on = compound.getBoolean("On");

        disguise.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("On", on);

        disguise.writeToNBT(compound);
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
                    worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockLightAir);
                    TileEntityLightAir light = (TileEntityLightAir) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);

                    if (light != null) {
                        light.setMaster(xCoord, yCoord, zCoord);
                        light.spreadBlocks();
                    }
                }
            }
        }
    }

    /* Everything below is just a proxy for the interfaces */

    /* DISGUISABLE */

    @Override
    public boolean isDisguised()
    {
        return disguise.isDisguised();
    }

    @Override
    public boolean setDisguise(ItemStack stack)
    {
        return disguise.setDisguise(stack);
    }

    @Override
    public ItemStack getDisguise()
    {
        return disguise.getDisguise();
    }

}
