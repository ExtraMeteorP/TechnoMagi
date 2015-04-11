package com.ollieread.technomagi.common;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.client.gui.GuiWindowContainer;
import com.ollieread.technomagi.client.gui.GuiWindowScreen;
import com.ollieread.technomagi.client.gui.window.WindowKnowledge;
import com.ollieread.technomagi.client.gui.window.WindowSpecialisation;
import com.ollieread.technomagi.common.block.tile.ITileGui;
import com.ollieread.technomagi.common.event.handler.AbilityEvents;
import com.ollieread.technomagi.common.event.handler.ElectromagneticPocketEvents;
import com.ollieread.technomagi.common.event.handler.EntityEvents;
import com.ollieread.technomagi.common.event.handler.InitialisationEvents;
import com.ollieread.technomagi.common.event.handler.ResearchEvents;
import com.ollieread.technomagi.common.event.handler.ScanningEvents;
import com.ollieread.technomagi.compat.CompatCommonProxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{

    protected CompatCommonProxy compat;

    public static int GUI_TECHNOMAGE = 0;
    public static int GUI_TILE = 1;

    public CommonProxy()
    {
        compat = new CompatCommonProxy();
    }

    public void init()
    {
    }

    public void registerRenderers()
    {
        compat.registerEventHandlers();
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
        return true;
    }

    public boolean isClient()
    {
        return false;
    }

    public void registerEventHandlers()
    {
        EntityEvents entityHandler = new EntityEvents();
        ResearchEvents researchHandler = new ResearchEvents();
        AbilityEvents abilityHandler = new AbilityEvents();
        InitialisationEvents initialisationHandler = new InitialisationEvents();

        // Initialisation handler
        MinecraftForge.EVENT_BUS.register(initialisationHandler);
        FMLCommonHandler.instance().bus().register(initialisationHandler);

        // Basic entity/player handler
        MinecraftForge.EVENT_BUS.register(entityHandler);
        FMLCommonHandler.instance().bus().register(entityHandler);
        TechnomagiApi.EVENT_BUS.register(entityHandler);

        // Electromagnetic pocket handler
        TechnomagiApi.EVENT_BUS.register(new ElectromagneticPocketEvents());

        // Research handler
        MinecraftForge.EVENT_BUS.register(researchHandler);
        FMLCommonHandler.instance().bus().register(researchHandler);
        MinecraftForge.TERRAIN_GEN_BUS.register(researchHandler);
        TechnomagiApi.EVENT_BUS.register(researchHandler);

        // Scanning handler
        MinecraftForge.EVENT_BUS.register(new ScanningEvents());

        // Ability handler
        MinecraftForge.EVENT_BUS.register(abilityHandler);
        TechnomagiApi.EVENT_BUS.register(abilityHandler);

        compat.registerEventHandlers();
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_TILE) {
            ITileGui tile = (ITileGui) world.getTileEntity(x, y, z);

            if (tile != null) {
                return tile.getContainer(player);
            }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_TECHNOMAGE) {
            PlayerTechnomagi technomage = PlayerTechnomagi.get(player);

            if (technomage != null) {
                if (!technomage.hasSpecialised()) {
                    return new GuiWindowScreen(new WindowSpecialisation());
                } else {
                    return new GuiWindowScreen(new WindowKnowledge(technomage, null));
                }
            }
        } else if (ID == GUI_TILE) {
            ITileGui tile = (ITileGui) world.getTileEntity(x, y, z);

            if (tile != null) {
                return new GuiWindowContainer(tile.getWindow(player), tile.getContainer(player));
            }
        }

        return null;
    }

    private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

    public static void storeEntityData(String name, NBTTagCompound compound)
    {
        extendedEntityData.put(name, compound);
    }

    public static NBTTagCompound getEntityData(String name)
    {
        return extendedEntityData.remove(name);
    }

    public void updateOverlay()
    {
    }

    public void updateContent()
    {
    }

}
