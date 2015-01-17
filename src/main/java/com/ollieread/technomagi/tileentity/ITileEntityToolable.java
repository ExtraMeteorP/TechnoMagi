package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * This should be implemented by blocks that wish to do something when right
 * clicked with a digital tool.
 * 
 * @author ollie
 * 
 */
public interface ITileEntityToolable
{

    /**
     * This is the function called when the block is right clicked.
     * 
     * If you don't like the name, consider the fact that I wanted to call it
     * 'onDigitallyTooled', and count yourself lucky.
     * 
     * @param player
     * @param tool
     * @return
     */
    public boolean onTooled(EntityPlayer player, World world, int x, int y, int z, ItemStack tool);

}
