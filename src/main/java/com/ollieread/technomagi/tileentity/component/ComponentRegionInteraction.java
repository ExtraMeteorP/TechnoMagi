package com.ollieread.technomagi.tileentity.component;

import net.minecraft.tileentity.TileEntity;

import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;
import com.ollieread.technomagi.world.region.RegionPayload;

public class ComponentRegionInteraction extends ComponentRegion
{

    public ComponentRegionInteraction(TileEntity tileEntity)
    {
        super(tileEntity);
    }

    @Override
    public RegionControllerType getType()
    {
        return RegionControllerType.INTERACTION;
    }

    @Override
    public void perform(RegionPayload payload)
    {

    }

}
