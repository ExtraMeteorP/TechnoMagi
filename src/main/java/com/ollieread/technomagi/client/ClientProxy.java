package com.ollieread.technomagi.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.CommonProxy;

import cpw.mods.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy
{

    @Override
    public void init()
    {

    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }

    @Override
    public World getClientWorld()
    {
        return Minecraft.getMinecraft().theWorld;
    }

}
