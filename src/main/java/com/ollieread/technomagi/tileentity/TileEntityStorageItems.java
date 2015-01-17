package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachineStorage;
import com.ollieread.technomagi.tileentity.component.ComponentOwner;

public class TileEntityStorageItems extends TileEntityMachineStorage implements ITileEntityHasOwner, ITileEntityStorage
{

    protected ComponentOwner owner;

    public TileEntityStorageItems()
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
