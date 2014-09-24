package com.ollieread.technomagi.common;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.client.gui.GuiAnalysis;
import com.ollieread.technomagi.client.gui.GuiArchive;
import com.ollieread.technomagi.client.gui.GuiConstruct;
import com.ollieread.technomagi.client.gui.GuiCrafting;
import com.ollieread.technomagi.client.gui.GuiNaniteReplicator;
import com.ollieread.technomagi.client.gui.GuiObservationChamber;
import com.ollieread.technomagi.client.gui.GuiSelf;
import com.ollieread.technomagi.client.gui.GuiSpecialisation;
import com.ollieread.technomagi.client.gui.GuiStaff;
import com.ollieread.technomagi.client.gui.GuiTeleporter;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveAbilities;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveConstruct;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveCrafting;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveInfo;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveKnowledge;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveMain;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveReactive;
import com.ollieread.technomagi.event.handler.EnndsEventHandler;
import com.ollieread.technomagi.event.handler.ItemEventHandler;
import com.ollieread.technomagi.event.handler.PlayerEventHandler;
import com.ollieread.technomagi.event.handler.TickEventHandler;
import com.ollieread.technomagi.inventory.ContainerAnalysis;
import com.ollieread.technomagi.inventory.ContainerArchive;
import com.ollieread.technomagi.inventory.ContainerConstruct;
import com.ollieread.technomagi.inventory.ContainerCrafting;
import com.ollieread.technomagi.inventory.ContainerNaniteReplicator;
import com.ollieread.technomagi.inventory.ContainerObservation;
import com.ollieread.technomagi.inventory.ContainerStaff;
import com.ollieread.technomagi.tileentity.TileEntityAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.tileentity.TileEntityConstruct;
import com.ollieread.technomagi.tileentity.TileEntityCrafting;
import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;
import com.ollieread.technomagi.tileentity.TileEntityObservationChamber;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CommonProxy implements IGuiHandler
{

    public static int GUI_TECHNOMAGI = 1;
    public static int GUI_SPECIALISATION = 2;
    public static int GUI_REPLICATOR = 3;
    public static int GUI_ANALYSIS = 4;
    public static int GUI_OBSERVATION = 5;
    public static int GUI_ARCHIVE = 6;
    public static int GUI_CRAFTING = 7;
    public static int GUI_STAFF = 8;
    public static int GUI_CONSTRUCT = 9;
    public static int GUI_TELEPORTER = 10;

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
        } else if (ID == GUI_OBSERVATION) {
            TileEntityObservationChamber chamber = (TileEntityObservationChamber) world.getTileEntity(x, y, z);

            if (chamber != null) {
                return new ContainerObservation(player.inventory, chamber);
            }
        } else if (ID == GUI_ARCHIVE) {
            TileEntityArchive archive = (TileEntityArchive) world.getTileEntity(x, y, z);

            if (archive != null) {
                return new ContainerArchive(player.inventory, archive);
            }
        } else if (ID == GUI_CRAFTING) {
            TileEntityCrafting crafter = (TileEntityCrafting) world.getTileEntity(x, y, z);

            if (crafter != null) {
                return new ContainerCrafting(player.inventory, crafter);
            }
        } else if (ID == GUI_STAFF) {
            return new ContainerStaff(player, player.getHeldItem());
        } else if (ID == GUI_CONSTRUCT) {
            TileEntityConstruct construct = (TileEntityConstruct) world.getTileEntity(x, y, z);

            if (construct != null) {
                return new ContainerConstruct(player.inventory, construct);
            }
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_TECHNOMAGI) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            if (!charon.canSpecialise()) {
                return new GuiSelf();
            }
        } else if (ID == GUI_SPECIALISATION) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            if (charon.canSpecialise()) {
                return new GuiSpecialisation();
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
        } else if (ID == GUI_OBSERVATION) {
            TileEntityObservationChamber chamber = (TileEntityObservationChamber) world.getTileEntity(x, y, z);

            if (chamber != null) {
                return new GuiObservationChamber(player.inventory, chamber);
            }
        } else if (ID == GUI_ARCHIVE) {
            TileEntityArchive archive = (TileEntityArchive) world.getTileEntity(x, y, z);

            if (archive != null) {
                int type = archive.getType();
                GuiArchive gui = null;

                switch (type) {
                    case 1:
                        gui = new GuiArchiveInfo(player, archive);
                        break;
                    case 2:
                        gui = new GuiArchiveKnowledge(player, archive);
                        break;
                    case 3:
                        gui = new GuiArchiveAbilities(player, archive);
                        break;
                    case 4:
                        gui = new GuiArchiveCrafting(player, archive);
                        break;
                    case 5:
                        gui = new GuiArchiveConstruct(player, archive);
                        break;
                    case 6:
                        gui = new GuiArchiveReactive(player, archive);
                        break;
                    default:
                        gui = new GuiArchiveMain(player, archive);
                }

                if (gui != null) {
                    gui.setType(type);
                    gui.setSubType(archive.getSubType());
                    gui.setPage(archive.getPage());

                    return gui;
                }
            }
        } else if (ID == GUI_CRAFTING) {
            TileEntityCrafting crafter = (TileEntityCrafting) world.getTileEntity(x, y, z);

            if (crafter != null) {
                return new GuiCrafting(player.inventory, crafter);
            }
        } else if (ID == GUI_STAFF) {
            return new GuiStaff(player, player.getHeldItem());
        } else if (ID == GUI_CONSTRUCT) {
            TileEntityConstruct construct = (TileEntityConstruct) world.getTileEntity(x, y, z);

            if (construct != null) {
                return new GuiConstruct(player.inventory, construct);
            }
        } else if (ID == GUI_TELEPORTER) {
            TileEntityTeleporter teleporter = (TileEntityTeleporter) world.getTileEntity(x, y, z);

            if (teleporter != null) {
                return new GuiTeleporter(teleporter);
            }
        }

        return null;
    }

    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(playerEventHandler);
        MinecraftForge.EVENT_BUS.register(new EnndsEventHandler());
        MinecraftForge.EVENT_BUS.register(new ItemEventHandler());

        FMLCommonHandler.instance().bus().register(new TickEventHandler());
        FMLCommonHandler.instance().bus().register(playerEventHandler);
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
