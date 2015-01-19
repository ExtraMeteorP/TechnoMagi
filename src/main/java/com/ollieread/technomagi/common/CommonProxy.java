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
import com.ollieread.technomagi.client.gui.GuiFocuser;
import com.ollieread.technomagi.client.gui.GuiFurnace;
import com.ollieread.technomagi.client.gui.GuiNaniteReplicator;
import com.ollieread.technomagi.client.gui.GuiObservationChamber;
import com.ollieread.technomagi.client.gui.GuiPersonalInterface;
import com.ollieread.technomagi.client.gui.GuiSelf;
import com.ollieread.technomagi.client.gui.GuiSeparator;
import com.ollieread.technomagi.client.gui.GuiSpecialisation;
import com.ollieread.technomagi.client.gui.GuiStaff;
import com.ollieread.technomagi.client.gui.GuiTeleporter;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveAbilities;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveConstruct;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveCrafting;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveInfo;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveKnowledge;
import com.ollieread.technomagi.client.gui.archive.GuiArchiveMain;
import com.ollieread.technomagi.client.gui.builder.GuiBuilderContainer;
import com.ollieread.technomagi.event.handler.ChunkEventHandler;
import com.ollieread.technomagi.event.handler.EnndsEventHandler;
import com.ollieread.technomagi.event.handler.EntityEventHandler;
import com.ollieread.technomagi.event.handler.ItemEventHandler;
import com.ollieread.technomagi.event.handler.ResearchEventHandler;
import com.ollieread.technomagi.event.handler.TickEventHandler;
import com.ollieread.technomagi.inventory.ContainerAnalysis;
import com.ollieread.technomagi.inventory.ContainerArchive;
import com.ollieread.technomagi.inventory.ContainerConstruct;
import com.ollieread.technomagi.inventory.ContainerCrafting;
import com.ollieread.technomagi.inventory.ContainerFocuser;
import com.ollieread.technomagi.inventory.ContainerFurnace;
import com.ollieread.technomagi.inventory.ContainerNaniteReplicator;
import com.ollieread.technomagi.inventory.ContainerObservation;
import com.ollieread.technomagi.inventory.ContainerPersonalInterface;
import com.ollieread.technomagi.inventory.ContainerSeparator;
import com.ollieread.technomagi.inventory.ContainerStaff;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.tileentity.TileEntityConstruct;
import com.ollieread.technomagi.tileentity.TileEntityMachineAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityMachineAssembler;
import com.ollieread.technomagi.tileentity.TileEntityMachineFurnace;
import com.ollieread.technomagi.tileentity.TileEntityMachineInfuser;
import com.ollieread.technomagi.tileentity.TileEntityMachineObservation;
import com.ollieread.technomagi.tileentity.TileEntityMachineProcessor;
import com.ollieread.technomagi.tileentity.TileEntityMachineReplicator;
import com.ollieread.technomagi.tileentity.TileEntityMachineTeleporter;

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
    public static int GUI_SEPARATOR = 11;
    public static int GUI_FURNACE = 12;
    public static int GUI_FOCUSER = 13;
    public static int GUI_PERSONAL_INTERFACE = 14;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_REPLICATOR) {
            TileEntityMachineReplicator replicator = (TileEntityMachineReplicator) world.getTileEntity(x, y, z);

            if (replicator != null) {
                return new ContainerNaniteReplicator(player.inventory, replicator);
            }
        } else if (ID == GUI_ANALYSIS) {
            TileEntityMachineAnalysis analysis = (TileEntityMachineAnalysis) world.getTileEntity(x, y, z);

            if (analysis != null) {
                return new ContainerAnalysis(player.inventory, analysis);
            }
        } else if (ID == GUI_OBSERVATION) {
            TileEntityMachineObservation chamber = (TileEntityMachineObservation) world.getTileEntity(x, y, z);

            if (chamber != null) {
                return new ContainerObservation(player.inventory, chamber);
            }
        } else if (ID == GUI_ARCHIVE) {
            TileEntityArchive archive = (TileEntityArchive) world.getTileEntity(x, y, z);

            if (archive != null) {
                return new ContainerArchive(player.inventory, archive);
            }
        } else if (ID == GUI_CRAFTING) {
            TileEntityMachineAssembler crafter = (TileEntityMachineAssembler) world.getTileEntity(x, y, z);

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
        } else if (ID == GUI_SEPARATOR) {
            TileEntityMachineProcessor crafter = (TileEntityMachineProcessor) world.getTileEntity(x, y, z);

            if (crafter != null) {
                return new ContainerSeparator(player.inventory, crafter);
            }
        } else if (ID == GUI_FURNACE) {
            TileEntityMachineFurnace crafter = (TileEntityMachineFurnace) world.getTileEntity(x, y, z);

            if (crafter != null) {
                return new ContainerFurnace(player.inventory, crafter);
            }
        } else if (ID == GUI_FOCUSER) {
            TileEntityMachineInfuser focuser = (TileEntityMachineInfuser) world.getTileEntity(x, y, z);

            if (focuser != null) {
                return new ContainerFocuser(player.inventory, focuser);
            }
        } else if (ID == GUI_PERSONAL_INTERFACE) {
            return new ContainerPersonalInterface(player, player.getHeldItem());
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
            TileEntityMachineReplicator replicator = (TileEntityMachineReplicator) world.getTileEntity(x, y, z);

            if (replicator != null) {
                return new GuiNaniteReplicator(player.inventory, replicator);
            }
        } else if (ID == GUI_ANALYSIS) {
            TileEntityMachineAnalysis analysis = (TileEntityMachineAnalysis) world.getTileEntity(x, y, z);

            if (analysis != null) {
                return new GuiAnalysis(player.inventory, analysis);
            }
        } else if (ID == GUI_OBSERVATION) {
            TileEntityMachineObservation chamber = (TileEntityMachineObservation) world.getTileEntity(x, y, z);

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
            TileEntityMachineAssembler crafter = (TileEntityMachineAssembler) world.getTileEntity(x, y, z);

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
            TileEntityMachineTeleporter teleporter = (TileEntityMachineTeleporter) world.getTileEntity(x, y, z);

            if (teleporter != null) {
                return new GuiTeleporter(teleporter);
            }
        } else if (ID == GUI_SEPARATOR) {
            TileEntityMachineProcessor separator = (TileEntityMachineProcessor) world.getTileEntity(x, y, z);

            if (separator != null) {
                return new GuiSeparator(player.inventory, separator);
            }
        } else if (ID == GUI_FURNACE) {
            TileEntityMachineFurnace furnace = (TileEntityMachineFurnace) world.getTileEntity(x, y, z);

            if (furnace != null) {
                return new GuiFurnace(player.inventory, furnace);
            }
        } else if (ID == GUI_FOCUSER) {
            TileEntityMachineInfuser focuser = (TileEntityMachineInfuser) world.getTileEntity(x, y, z);

            if (focuser != null) {
                return new GuiFocuser(player.inventory, focuser);
            }
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

    public boolean isClient()
    {
        return false;
    }

    public boolean isServer()
    {
        return true;
    }

    public void init()
    {

    }

    public ModelBiped getArmorModel(int id)
    {
        return null;
    }

}
