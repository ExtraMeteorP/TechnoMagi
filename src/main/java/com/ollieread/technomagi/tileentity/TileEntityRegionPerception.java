package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.proxy.RegionPerception;
import com.ollieread.technomagi.world.region.IRegionController;
import com.ollieread.technomagi.world.region.RegionManager;
import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;
import com.ollieread.technomagi.world.region.RegionPayload;

public class TileEntityRegionPerception extends TileEntityTM implements IRegionController
{

    protected RegionPerception controller = null;
    protected boolean synced = true;

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
                int id = RegionManager.getNetworkForCoords(xCoord, zCoord);

                if (!RegionManager.hasController(id, getType())) {
                    controller.setNetwork(id);
                    RegionManager.addController(this);
                    synced = true;
                }
            }
        }
    }

    @Override
    public void perform(RegionPayload payload)
    {

    }

}
