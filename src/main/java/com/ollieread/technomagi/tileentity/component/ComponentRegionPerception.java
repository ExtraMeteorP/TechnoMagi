package com.ollieread.technomagi.tileentity.component;

import net.minecraft.tileentity.TileEntity;

import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;
import com.ollieread.technomagi.world.region.RegionPayload;

public class ComponentRegionPerception extends ComponentRegion
{

    public ComponentRegionPerception(TileEntity tileEntity)
    {
        super(tileEntity);
    }

    @Override
    public RegionControllerType getType()
    {
        return RegionControllerType.PERCEPTION;
    }

    @Override
    public void perform(RegionPayload payload)
    {

    }

}
