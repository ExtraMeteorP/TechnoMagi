package com.ollieread.technomagi.common.block.machine.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import cofh.api.energy.EnergyStorage;

import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.common.component.Inventory;

public class TileResourceProcessorNanite extends TileResourceProcessorElectric
{

    public TileResourceProcessorNanite()
    {
        super();

        this.modifier = 0.5F;
        this.baseConsume = 3;
        this.inventory = new Inventory("inventory.technomagi.processor", 4);
    }

    @Override
    protected void createEnergyStorage()
    {
        this.energy = new EnergyStorage(6400, 25, 0);
    }

    @Override
    public Container getContainer(EntityPlayer player)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Window getWindow(EntityPlayer player)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
