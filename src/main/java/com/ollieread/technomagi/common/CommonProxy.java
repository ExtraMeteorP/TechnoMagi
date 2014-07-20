package com.ollieread.technomagi.common;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.ennds.EnndsRegistry;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.client.gui.GuiAnalysis;
import com.ollieread.technomagi.client.gui.GuiNaniteReplicator;
import com.ollieread.technomagi.client.gui.GuiSelf;
import com.ollieread.technomagi.client.gui.GuiSpecialisation;
import com.ollieread.technomagi.event.handler.PlayerEventHandler;
import com.ollieread.technomagi.event.handler.TMEventHandler;
import com.ollieread.technomagi.event.handler.TickEventHandler;
import com.ollieread.technomagi.inventory.ContainerAnalysis;
import com.ollieread.technomagi.inventory.ContainerNaniteReplicator;
import com.ollieread.technomagi.tileentity.TileEntityAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{

    public static int GUI_TECHNOMAGI = 1;
    public static int GUI_SPECIALISATION = 2;
    public static int GUI_REPLICATOR = 3;
    public static int GUI_ANALYSIS = 4;

    public static PlayerEventHandler playerEventHandler = new PlayerEventHandler();

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_REPLICATOR) {
            TileEntityNaniteReplicator replicator = (TileEntityNaniteReplicator) world.getTileEntity(x, y, z);

            if (replicator != null) {
                return new ContainerNaniteReplicator(player.inventory, replicator);
            }
        } else if (ID == GUI_ANALYSIS) {
            TileEntityAnalysis analysis = (TileEntityAnalysis) world.getTileEntity(x, y, z);

            if (analysis != null) {
                return new ContainerAnalysis(player.inventory, analysis);
            }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_TECHNOMAGI) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            if (charon.canSpecialise()) {
                return new GuiSpecialisation();
            } else {
                return new GuiSelf();
            }
        } else if (ID == GUI_REPLICATOR) {
            TileEntityNaniteReplicator replicator = (TileEntityNaniteReplicator) world.getTileEntity(x, y, z);

            if (replicator != null) {
                return new GuiNaniteReplicator(player.inventory, replicator);
            }
        } else if (ID == GUI_ANALYSIS) {
            TileEntityAnalysis analysis = (TileEntityAnalysis) world.getTileEntity(x, y, z);

            if (analysis != null) {
                return new GuiAnalysis(player.inventory, analysis);
            }
        }

        return null;
    }

    public void registerEventHandlers()
    {
        EnndsRegistry.registerEvent("specialisation");
        EnndsRegistry.registerEvent("researchProgress");
        EnndsRegistry.registerEvent("inFire");
        EnndsRegistry.registerEvent("playerTick");
        EnndsRegistry.registerEvent("fall");
        EnndsRegistry.registerEvent("onFire");
        EnndsRegistry.registerEvent("inLava");
        EnndsRegistry.registerEvent("inWall");
        EnndsRegistry.registerEvent("starve");
        EnndsRegistry.registerEvent("cactus");
        EnndsRegistry.registerEvent("void");
        EnndsRegistry.registerEvent("magic");
        EnndsRegistry.registerEvent("wither");
        EnndsRegistry.registerEvent("anvil");
        EnndsRegistry.registerEvent("fallingBlock");
        EnndsRegistry.registerEvent("toNether");
        EnndsRegistry.registerEvent("toEnd");
        EnndsRegistry.registerEvent("toOverworld");

        MinecraftForge.EVENT_BUS.register(playerEventHandler);
        MinecraftForge.EVENT_BUS.register(new TMEventHandler());

        FMLCommonHandler.instance().bus().register(new TickEventHandler());
    }

    public EntityPlayer getClientPlayer()
    {
        return null;
    }

    public void init()
    {

    }

    public ModelBiped getArmorModel(int id)
    {
        return null;
    }

}
