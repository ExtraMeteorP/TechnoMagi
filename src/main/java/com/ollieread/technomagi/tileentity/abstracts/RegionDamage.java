package com.ollieread.technomagi.tileentity.abstracts;

import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;

public abstract class RegionDamage extends Region
{

    @Override
    public RegionControllerType getType()
    {
        return RegionControllerType.DAMAGE;
    }

}
