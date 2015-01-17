package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.abstracts.MachineStorage;
import com.ollieread.technomagi.tileentity.component.IHasOwner;
import com.ollieread.technomagi.tileentity.component.IStorage;
import com.ollieread.technomagi.tileentity.component.Owner;

public class TileEntityStorageItems extends MachineStorage implements IHasOwner, IStorage
{

    protected Owner owner;

    public TileEntityStorageItems()
    {
        owner = new Owner();
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
