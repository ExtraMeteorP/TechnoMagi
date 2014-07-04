package com.ollieread.technomagi.common;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.api.TMRegistry;
import com.ollieread.technomagi.client.gui.GuiNaniteReplicator;
import com.ollieread.technomagi.client.gui.GuiSelf;
import com.ollieread.technomagi.client.gui.GuiSpecialisation;
import com.ollieread.technomagi.event.handler.PlayerEventHandler;
import com.ollieread.technomagi.event.handler.TMEventHandler;
import com.ollieread.technomagi.event.handler.TickEventHandler;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.inventory.ContainerNaniteReplicator;
import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{

    public static int GUI_TECHNOMAGI = 1;
    public static int GUI_SPECIALISATION = 2;
    public static int GUI_REPLICATOR = 3;

    public static PlayerEventHandler playerEventHandler = new PlayerEventHandler();

    private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();

    public static void storeEntityData(String name, NBTTagCompound compound)
    {
        extendedEntityData.put(name, compound);
    }

    public static NBTTagCompound getEntityData(String name)
    {
        return extendedEntityData.remove(name);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == GUI_REPLICATOR) {
            TileEntityNaniteReplicator replicator = (TileEntityNaniteReplicator) world.getTileEntity(x, y, z);

            if (replicator != null) {
                return new ContainerNaniteReplicator(player.inventory, replicator);
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
        }

        return null;
    }

    public void registerEventHandlers()
    {
        TMRegistry.registerEvent("specialisation");
        TMRegistry.registerEvent("researchProgress");
        TMRegistry.registerEvent("inFire");
        TMRegistry.registerEvent("playerTick");
        TMRegistry.registerEvent("fall");
        TMRegistry.registerEvent("onFire");
        TMRegistry.registerEvent("inLava");
        TMRegistry.registerEvent("inWall");
        TMRegistry.registerEvent("starve");
        TMRegistry.registerEvent("cactus");
        TMRegistry.registerEvent("void");
        TMRegistry.registerEvent("magic");
        TMRegistry.registerEvent("wither");
        TMRegistry.registerEvent("anvil");
        TMRegistry.registerEvent("fallingBlock");
        TMRegistry.registerEvent("toNether");
        TMRegistry.registerEvent("toEnd");
        TMRegistry.registerEvent("toOverworld");

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
