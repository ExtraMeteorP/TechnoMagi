package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.abstracts.MachineFluid;
import com.ollieread.technomagi.tileentity.component.IHasOwner;
import com.ollieread.technomagi.tileentity.component.Owner;

public class TileEntityStorageFluid extends MachineFluid implements IHasOwner
{

    protected Owner owner;

    public TileEntityStorageFluid()
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
