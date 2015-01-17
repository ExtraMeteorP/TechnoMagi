package com.ollieread.technomagi.util;

import net.minecraft.entity.player.EntityPlayerMP;

import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageOpenTeleporter;
import com.ollieread.technomagi.network.message.MessageSetArchive;
import com.ollieread.technomagi.network.message.MessageSetBuilding;
import com.ollieread.technomagi.network.message.MessageSetCrafting;
import com.ollieread.technomagi.network.message.MessageSetProgress;
import com.ollieread.technomagi.network.message.MessageSetTeleporterMode;
import com.ollieread.technomagi.network.message.MessageSyncTileEntityTM;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.tileentity.TileEntityConstruct;
import com.ollieread.technomagi.tileentity.TileEntityMachineAssembler;
import com.ollieread.technomagi.tileentity.TileEntityMachineTeleporter;
import com.ollieread.technomagi.tileentity.abstracts.MachineResearch;
import com.ollieread.technomagi.tileentity.abstracts.Basic;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketHelper
{

    public static void setArchive(TileEntityArchive archive, int type, int subtype, int page)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageSetArchive(archive, type, subtype, page));
    }

    public static void setTeleporterMode(TileEntityMachineTeleporter machine, int mode)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageSetTeleporterMode(machine, mode));
    }

    public static void openTeleporter(int x, int y, int z, EntityPlayerMP player)
    {
        PacketHandler.INSTANCE.sendTo(new MessageOpenTeleporter(x, y, z), player);
    }

    public static void setBuilding(TileEntityConstruct machine, boolean building)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageSetBuilding(machine, building));
    }

    public static void setCrafting(TileEntityMachineAssembler machine, boolean progress)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageSetCrafting(machine, progress));
    }

    public static void setProgress(MachineResearch machine, boolean progress)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageSetProgress(machine, progress));
    }

    public static void syncTile(IMessage message)
    {
        PacketHandler.INSTANCE.sendToAll(message);
    }

    public static void syncTile(Basic tile)
    {
        syncTile(new MessageSyncTileEntityTM(tile));
    }

}
