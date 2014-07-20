package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.ollieread.technomagi.tileentity.TileEntityObservationChamber;

public class SlotObservation extends Slot
{

    protected boolean empty = false;
    protected TileEntityObservationChamber chamber;

    public SlotObservation(TileEntityObservationChamber analysis, int i, int x, int y)
    {
        super((IInventory) analysis, i, x, y);
        this.chamber = analysis;
    }

    public boolean canTakeStack(EntityPlayer player)
    {
        return !chamber.inProgress();
    }

}
