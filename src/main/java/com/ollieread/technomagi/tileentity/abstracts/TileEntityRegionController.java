package com.ollieread.technomagi.tileentity.abstracts;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.tileentity.ITileEntityRegionController;
import com.ollieread.technomagi.tileentity.component.ComponentRegion;
import com.ollieread.technomagi.world.region.RegionManager;
import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;

public abstract class TileEntityRegionController extends TileEntityMachine implements ITileEntityRegionController
{

    protected ComponentRegion controller = null;

    public TileEntityRegionController(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract);
    }

    protected void setController(ComponentRegion controller)
    {
        this.controller = controller;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        controller.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        controller.writeToNBT(compound);
    }

    @Override
    public int getNetworkId()
    {
        return controller.getNetworkId();
    }

    @Override
    public RegionControllerType getType()
    {
        return controller.getType();
    }

    @Override
    public boolean affectsEntity(EntityLivingBase entity)
    {
        return controller.affectsEntity(entity);
    }

    @Override
    public boolean enabled()
    {
        return controller.enabled();
    }

    @Override
    public int[] getCoords()
    {
        return new int[] { xCoord, yCoord, zCoord };
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (getNetworkId() == -1) {
                RegionManager manager = RegionManager.getInstance(worldObj.provider.dimensionId);
                int id = manager.getNetworkForCoords(xCoord, zCoord);

                if (!manager.hasController(id, getType())) {
                    controller.setNetwork(id);
                    manager.addController(this);
                }
            }
        }
    }

}
