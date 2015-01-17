package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.ollieread.technomagi.tileentity.TileEntityMachineObservation;

public class SlotObservation extends Slot
{

    protected boolean empty = false;
    protected TileEntityMachineObservation chamber;

    public SlotObservation(TileEntityMachineObservation analysis, int i, int x, int y)
    {
        super((IInventory) analysis, i, x, y);
        this.chamber = analysis;
    }

    public boolean canTakeStack(EntityPlayer player)
    {
        return !chamber.inProgress();
    }

}
