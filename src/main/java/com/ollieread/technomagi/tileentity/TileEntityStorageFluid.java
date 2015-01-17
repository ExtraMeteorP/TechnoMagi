package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachineFluid;
import com.ollieread.technomagi.tileentity.component.ComponentOwner;

public class TileEntityStorageFluid extends TileEntityMachineFluid implements ITileEntityHasOwner
{

    protected ComponentOwner owner;

    public TileEntityStorageFluid()
    {
        owner = new ComponentOwner();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        owner.readFromNBT(compound.getCompoundTag("Owner"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound ownerCompound = new NBTTagCompound();

        owner.writeToNBT(ownerCompound);

        compound.setTag("Owner", ownerCompound);
    }

    public boolean shouldRenderInPass(int pass)
    {
        return pass == 0 || pass == 1;
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
