package com.ollieread.technomagi.world.region;

import net.minecraft.entity.EntityLivingBase;

import com.ollieread.technomagi.world.region.RegionManager.RegionControllerType;

public interface IRegionController
{

    /**
     * Get the network that the controller is registered too.
     * 
     * If none, return -1
     * 
     * @return
     */
    public int getNetworkId();

    /**
     * Get the type of the controller.
     * 
     * @return
     */
    public RegionControllerType getType();

    /**
     * Whether or not the controller can affect the passed entity.
     * 
     * @param entity
     * @return
     */
    public boolean affectsEntity(EntityLivingBase entity);

    /**
     * Whether or not the controller is enabled.
     * 
     * @return
     */
    public boolean enabled();

    public int[] getCoords();

}