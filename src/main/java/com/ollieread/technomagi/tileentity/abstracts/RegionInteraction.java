package com.ollieread.technomagi.tileentity.abstracts;

import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;

public abstract class RegionInteraction extends Region
{

    @Override
    public RegionControllerType getType()
    {
        return RegionControllerType.INTERACTION;
    }

}
