package com.ollieread.technomagi.api.ability;

import net.minecraft.nbt.NBTTagCompound;

/**
 * This interface is to be used by ability data classes. An example for this
 * would be data that needs to be persisted while casting. The NBT is for
 * syncing mostly.
 * 
 * The ability data is user specific and can optionally be updated every tick,
 * so that effects can expire.
 * 
 * @author ollieread
 *
 */
public interface IAbilityData
{

    /**
     * Load the data from the NBTTagCompound, used for syncing.
     * 
     * @param compound
     */
    public void loadNBTData(NBTTagCompound compound);

    /**
     * Save the data to the NBTTagCompound, used for syncing.
     * 
     * @param compound
     */
    public void saveNBTData(NBTTagCompound compound);

    /**
     * Whether or not this data requires an update each tick.
     * 
     * @return
     */
    public boolean requiresUpdate();

    /**
     * Perform the tick update.
     */
    public void update();

}
