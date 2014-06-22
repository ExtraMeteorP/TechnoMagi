package com.ollieread.technomagi.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public abstract class ExtendedProperties implements IExtendedEntityProperties
{

    protected EntityPlayer player;

    public ExtendedProperties(EntityPlayer player)
    {
        this.player = player;
    }

    @Override
    public void init(Entity entity, World world)
    {
    }

}
