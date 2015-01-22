package com.ollieread.technomagi.common;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.client.gui.GuiPersonalInterface;
import com.ollieread.technomagi.client.gui.GuiSpecialisation;
import com.ollieread.technomagi.client.gui.GuiStaff;
import com.ollieread.technomagi.client.gui.abstracts.GuiBuilderContainer;
import com.ollieread.technomagi.client.gui.abstracts.GuiBuilderScreen;
import com.ollieread.technomagi.event.handler.ChunkEventHandler;
import com.ollieread.technomagi.event.handler.EnndsEventHandler;
import com.ollieread.technomagi.event.handler.EntityEventHandler;
import com.ollieread.technomagi.event.handler.ItemEventHandler;
import com.ollieread.technomagi.event.handler.ResearchEventHandler;
import com.ollieread.technomagi.event.handler.TickEventHandler;
import com.ollieread.technomagi.event.handler.WorldEventHandler;
import com.ollieread.technomagi.inventory.ContainerPersonalInterface;
import com.ollieread.technomagi.inventory.ContainerStaff;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CommonProxy implements IGuiHandler
{

    public static int GUI_STAFF = 1;
    public static int GUI_SPECIALISATION = 2;
    public static int GUI_PERSONAL_INTERFACE = 3;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_STAFF) {
            return new ContainerStaff(player, player.getHeldItem());
        } else if (ID == GUI_PERSONAL_INTERFACE) {
            return new ContainerPersonalInterface(player, player.getHeldItem());
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_STAFF) {
            return new GuiBuilderContainer(new ContainerStaff(player, player.getHeldItem()), new GuiStaff());
        } else if (ID == GUI_SPECIALISATION) {
            return new GuiBuilderScreen(new GuiSpecialisation());
        } else if (ID == GUI_PERSONAL_INTERFACE) {
            return new GuiBuilderContainer(new ContainerPersonalInterface(player, player.getHeldItem()), new GuiPersonalInterface());
        }

        return null;
    }

    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new ResearchEventHandler());
        MinecraftForge.EVENT_BUS.register(new EnndsEventHandler());
        MinecraftForge.EVENT_BUS.register(new ItemEventHandler());
        MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
        MinecraftForge.EVENT_BUS.register(new WorldEventHandler());

        FMLCommonHandler.instance().bus().register(new TickEventHandler());
        FMLCommonHandler.instance().bus().register(new EntityEventHandler());
        FMLCommonHandler.instance().bus().register(new ResearchEventHandler());
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

    public void init()
    {

    }

    public ModelBiped getArmorModel(int id)
    {
        return null;
    }

}
