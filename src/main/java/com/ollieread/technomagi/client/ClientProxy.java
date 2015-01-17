package com.ollieread.technomagi.client;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.client.gui.GuiTMOverlay;
import com.ollieread.technomagi.client.model.ModelRobotCow;
import com.ollieread.technomagi.client.model.ModelRobotCreeper;
import com.ollieread.technomagi.client.model.ModelRobotZombie;
import com.ollieread.technomagi.client.renderer.block.BlockDisguiseRenderer;
import com.ollieread.technomagi.client.renderer.block.BlockSmartmetalRenderer;
import com.ollieread.technomagi.client.renderer.entity.RenderRobotCow;
import com.ollieread.technomagi.client.renderer.entity.RenderRobotCreeper;
import com.ollieread.technomagi.client.renderer.entity.RenderRobotZombie;
import com.ollieread.technomagi.client.renderer.item.RenderBatteryItem;
import com.ollieread.technomagi.client.renderer.item.RenderChamberItem;
import com.ollieread.technomagi.client.renderer.item.RenderFocusChargerItem;
import com.ollieread.technomagi.client.renderer.item.RenderGeneratorItem;
import com.ollieread.technomagi.client.renderer.item.RenderMachineItem;
import com.ollieread.technomagi.client.renderer.item.RenderPrismaticPillarItem;
import com.ollieread.technomagi.client.renderer.item.RenderStorageItem;
import com.ollieread.technomagi.client.renderer.item.RenderTankItem;
import com.ollieread.technomagi.client.renderer.item.RenderTechnomageStaff;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityAnalysisRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityArchiveRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityBatteryRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityFocusChargerRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityFurnaceRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityGeneratorSolarRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityMachineAssemblerRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityMachineInfuserRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityMachineObservationRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityMachineProcessorRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityMachineReplicatorRenderer;
import com.ollieread.technomagi.client.renderer.tileentity.TileEntityPrismaticPillarRenderer;
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
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.tileentity.TileEntityBattery;
import com.ollieread.technomagi.tileentity.TileEntityGeneratorSolar;
import com.ollieread.technomagi.tileentity.TileEntityMachineAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityMachineAssembler;
import com.ollieread.technomagi.tileentity.TileEntityMachineFurnace;
import com.ollieread.technomagi.tileentity.TileEntityMachineInfuser;
import com.ollieread.technomagi.tileentity.TileEntityMachineInfuserCharger;
import com.ollieread.technomagi.tileentity.TileEntityMachineObservation;
import com.ollieread.technomagi.tileentity.TileEntityMachineProcessor;
import com.ollieread.technomagi.tileentity.TileEntityMachineReplicator;
import com.ollieread.technomagi.tileentity.TileEntityPrismaticPillar;
import com.ollieread.technomagi.tileentity.TileEntityStorageFluid;
import com.ollieread.technomagi.tileentity.TileEntityStorageItems;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{

    public static GuiTMOverlay overlay = new GuiTMOverlay(Minecraft.getMinecraft());

    public static int renderPass = 0;

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

    public World getClientWorld()
    {
        return Minecraft.getMinecraft().theWorld;
    }

    public boolean isClient()
    {
        return true;
    }

    public boolean isServer()
    {
        return false;
    }

    public void init()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityRobotCow.class, new RenderRobotCow(new ModelRobotCow(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityRobotCreeper.class, new RenderRobotCreeper(new ModelRobotCreeper(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityRobotZombie.class, new RenderRobotZombie(new ModelRobotZombie(), 0.5F));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityArchive.class, new TileEntityArchiveRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineReplicator.class, new TileEntityMachineReplicatorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineObservation.class, new TileEntityMachineObservationRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAssembler.class, new TileEntityMachineAssemblerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAnalysis.class, new TileEntityAnalysisRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStorageFluid.class, new TileEntityTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStorageItems.class, new TileEntityStorageRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineProcessor.class, new TileEntityMachineProcessorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFurnace.class, new TileEntityFurnaceRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineInfuser.class, new TileEntityMachineInfuserRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineInfuserCharger.class, new TileEntityFocusChargerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBattery.class, new TileEntityBatteryRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGeneratorSolar.class, new TileEntityGeneratorSolarRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPrismaticPillar.class, new TileEntityPrismaticPillarRenderer());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockMachine), new RenderMachineItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockObservationChamber), new RenderChamberItem());
        MinecraftForgeClient.registerItemRenderer(Items.itemTechnomageStaff, new RenderTechnomageStaff());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockTank), new RenderTankItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockStorage), new RenderStorageItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockFocusCharger), new RenderFocusChargerItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockBattery), new RenderBatteryItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockGenerator), new RenderGeneratorItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.blockPrismaticPillar), new RenderPrismaticPillarItem());

        RenderingRegistry.registerBlockHandler(new BlockSmartmetalRenderer());
        RenderingRegistry.registerBlockHandler(new BlockDisguiseRenderer());
    }

}
