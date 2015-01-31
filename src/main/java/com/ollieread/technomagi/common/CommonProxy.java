package com.ollieread.technomagi.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy
{

    public void init()
    {

    }

    public EntityPlayer getClientPlayer()
    {
        return null;
    }

    public World getClientWorld()
    {
        return null;
    }

    public boolean isServer()
    {
        return FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER);
    }

    public boolean isClient()
    {
        return FMLCommonHandler.instance().getEffectiveSide().equals(Side.CLIENT);
    }

}
