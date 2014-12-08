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
import com.ollieread.technomagi.client.renderer.item.RenderCraftingItem;
import com.ollieread.technomagi.client.renderer.item.RenderFocusChargerItem;
import com.ollieread.technomagi.client.renderer.item.RenderFocuserItem;
import com.ollieread.technomagi.client.renderer.item.RenderFurnaceItem;
import com.ollieread.technomagi.client.renderer.item.RenderReplicatorItem;
import com.ollieread.technomagi.client.renderer.item.RenderSeparatorItem;
import com.ollieread.technomagi.client.renderer.item.RenderStorageItem;
import com.ollieread.technomagi.client.renderer.item.RenderTankItem;
import com.ollieread.technomagi.client.renderer.item.RenderTechnomageStaff;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityAnalysisRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityArchiveRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityChamberRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityCraftingRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityFocusChargerRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityFocuserRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityFurnaceRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityReplicatorRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntitySeparatorRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityStorageRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityTankRenderer;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.KeyBindings;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.entity.robot.EntityRobotCow;
import com.ollieread.technomagi.entity.robot.EntityRobotCreeper;
import com.ollieread.technomagi.entity.robot.EntityRobotZombie;
import com.ollieread.technomagi.event.handler.KeyInputHandler;
import com.ollieread.technomagi.event.handler.MouseEventHandler;
import com.ollieread.technomagi.event.handler.RenderEventHandler;
import com.ollieread.technomagi.tileentity.TileEntityAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.tileentity.TileEntityCrafting;
import com.ollieread.technomagi.tileentity.TileEntityFocusCharger;
import com.ollieread.technomagi.tileentity.TileEntityFocuser;
import com.ollieread.technomagi.tileentity.TileEntityFurnace;
import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;
import com.ollieread.technomagi.tileentity.TileEntityObservationChamber;
import com.ollieread.technomagi.tileentity.TileEntitySeparator;
import com.ollieread.technomagi.tileentity.TileEntityStorage;
import com.ollieread.technomagi.tileentity.TileEntityTank;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{

    public static GuiTMOverlay overlay = new GuiTMOverlay(Minecraft.getMinecraft());

    public void registerEventHandlers()
    {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(new MouseEventHandler());
        FMLCommonHandler.instance().bus().register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(overlay);

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
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNaniteReplicator.class, new TileEntityReplicatorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityObservationChamber.class, new TileEntityChamberRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrafting.class, new TileEntityCraftingRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnalysis.class, new TileEntityAnalysisRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class, new TileEntityTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStorage.class, new TileEntityStorageRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySeparator.class, new TileEntitySeparatorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnace.class, new TileEntityFurnaceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFocuser.class, new TileEntityFocuserRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFocusCharger.class, new TileEntityFocusChargerRenderer());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockArchive), new RenderArchiveItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockNaniteReplicator), new RenderReplicatorItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockObservationChamber), new RenderChamberItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockCrafting), new RenderCraftingItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockAnalysis), new RenderAnalysisItem());
        MinecraftForgeClient.registerItemRenderer(Items.itemTechnomageStaff, new RenderTechnomageStaff());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockTank), new RenderTankItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockStorage), new RenderStorageItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockSeparator), new RenderSeparatorItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockFurnace), new RenderFurnaceItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockFocuser), new RenderFocuserItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockFocusCharger), new RenderFocusChargerItem());
    }

}
