package com.ollieread.technomagi.client;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.client.gui.GuiTMOverlay;
import com.ollieread.technomagi.client.model.ModelRobotCow;
import com.ollieread.technomagi.client.model.ModelRobotCreeper;
import com.ollieread.technomagi.client.model.ModelRobotZombie;
import com.ollieread.technomagi.client.renderer.entity.RenderRobotCow;
import com.ollieread.technomagi.client.renderer.entity.RenderRobotCreeper;
import com.ollieread.technomagi.client.renderer.entity.RenderRobotZombie;
import com.ollieread.technomagi.client.renderer.item.RenderAnalysisItem;
import com.ollieread.technomagi.client.renderer.item.RenderArchiveItem;
import com.ollieread.technomagi.client.renderer.item.RenderChamberItem;
import com.ollieread.technomagi.client.renderer.item.RenderConstructItem;
import com.ollieread.technomagi.client.renderer.item.RenderCraftingItem;
import com.ollieread.technomagi.client.renderer.item.RenderReplicatorItem;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityAnalysisRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityArchiveRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityCraftingRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityNaniteReplicatorRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityObservationChamberRenderer;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.KeyBindings;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.entity.robot.EntityRobotCow;
import com.ollieread.technomagi.entity.robot.EntityRobotCreeper;
import com.ollieread.technomagi.entity.robot.EntityRobotZombie;
import com.ollieread.technomagi.event.handler.KeyInputHandler;
import com.ollieread.technomagi.event.handler.MouseEventHandler;
import com.ollieread.technomagi.event.handler.RenderEventHandler;
import com.ollieread.technomagi.tileentity.TileEntityAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.tileentity.TileEntityCrafting;
import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;
import com.ollieread.technomagi.tileentity.TileEntityObservationChamber;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{

    public void registerEventHandlers()
    {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(new MouseEventHandler());
        FMLCommonHandler.instance().bus().register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(new GuiTMOverlay(Minecraft.getMinecraft()));

        KeyBindings.init();

        try {
            Field f = Render.class.getDeclaredField("renderManager");
            f.setAccessible(true);

            MinecraftForge.EVENT_BUS.register(new RenderEventHandler(f));
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public EntityPlayer getClientPlayer()
    {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }

    public void init()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityRobotCow.class, new RenderRobotCow(new ModelRobotCow(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityRobotCreeper.class, new RenderRobotCreeper(new ModelRobotCreeper(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityRobotZombie.class, new RenderRobotZombie(new ModelRobotZombie(), 0.5F));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityArchive.class, new TileEntityArchiveRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNaniteReplicator.class, new TileEntityNaniteReplicatorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityObservationChamber.class, new TileEntityObservationChamberRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrafting.class, new TileEntityCraftingRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnalysis.class, new TileEntityAnalysisRenderer());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockConstruct), new RenderConstructItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockArchive), new RenderArchiveItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockNaniteReplicator), new RenderReplicatorItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockObservationChamber), new RenderChamberItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockCrafting), new RenderCraftingItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockAnalysis), new RenderAnalysisItem());
    }

}
