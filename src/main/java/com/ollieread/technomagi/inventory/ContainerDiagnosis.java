package com.ollieread.technomagi.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerDiagnosis extends Container
{

    private EntityPlayer player;

    public ContainerDiagnosis(EntityPlayer player)
    {
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
}
