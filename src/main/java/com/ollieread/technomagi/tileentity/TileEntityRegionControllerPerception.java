package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.abstracts.TileEntityRegionController;
import com.ollieread.technomagi.tileentity.component.ComponentRegionPerception;
import com.ollieread.technomagi.world.region.RegionPayload;

public class TileEntityRegionControllerPerception extends TileEntityRegionController implements ITileEntityRegionController
{

    public TileEntityRegionControllerPerception()
    {
        super(0, 0, 0);

        setController(new ComponentRegionPerception(this));
    }

    @Override
    public void perform(RegionPayload payload)
    {

    }

    @Override
    public Container getContainer(InventoryPlayer playerInventory, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getGui(World world, int x, int y, int z, EntityPlayer player)
    {
        return 0;
    }

    @Override
    public boolean canProcess()
    {
        return false;
    }

    @Override
    public boolean isProcessing()
    {
        return false;
    }

    @Override
    public void process()
    {

    }

}
