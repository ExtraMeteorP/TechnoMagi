package com.ollieread.technomagi.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.client.event.handler.MouseEvents;
import com.ollieread.technomagi.client.gui.GuiBuilder;
import com.ollieread.technomagi.client.gui.GuiTechnomagi;
import com.ollieread.technomagi.client.renderers.blocks.BlockConduitRenderer;
import com.ollieread.technomagi.client.renderers.blocks.BlockFauxPocketRenderer;
import com.ollieread.technomagi.client.renderers.blocks.BlockHardlightPaneRenderer;
import com.ollieread.technomagi.client.renderers.blocks.BlockScannerRenderer;
import com.ollieread.technomagi.client.renderers.blocks.BlockTankRenderer;
import com.ollieread.technomagi.client.renderers.items.ItemBatteryRenderer;
import com.ollieread.technomagi.client.renderers.items.ItemFluidCapsuleRenderer;
import com.ollieread.technomagi.client.renderers.tiles.TileBatteryRenderer;
import com.ollieread.technomagi.client.renderers.tiles.TileFauxPocketRenderer;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.block.energy.tile.TileBattery;
import com.ollieread.technomagi.common.block.machine.tile.TileFauxPocket;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Items;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

    @Override
    public void init()
    {
        super.init();
    }

    @Override
    public void registerEventHandlers()
    {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(new GuiTechnomagi());
        MinecraftForge.EVENT_BUS.register(new MouseEvents());
    }

    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerBlockHandler(BlockConduitRenderer.id, new BlockConduitRenderer());
        RenderingRegistry.registerBlockHandler(BlockScannerRenderer.id, new BlockScannerRenderer());
        RenderingRegistry.registerBlockHandler(BlockFauxPocketRenderer.id, new BlockFauxPocketRenderer());
        RenderingRegistry.registerBlockHandler(BlockHardlightPaneRenderer.id, new BlockHardlightPaneRenderer());
        RenderingRegistry.registerBlockHandler(BlockTankRenderer.id, new BlockTankRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileFauxPocket.class, new TileFauxPocketRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileBattery.class, new TileBatteryRenderer());

        MinecraftForgeClient.registerItemRenderer(Items.fluidCapsule, new ItemFluidCapsuleRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Blocks.battery), new ItemBatteryRenderer());

    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return FMLClientHandler.instance().getClientPlayerEntity();
    }

    @Override
    public World getClientWorld()
    {
        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public boolean isServer()
    {
        return false;
    }

    @Override
    public boolean isClient()
    {
        return true;
    }

    @Override
    public void updateOverlay()
    {
        if (GuiTechnomagi.overlay != null) {
            GuiTechnomagi.overlay.updateContent();
        }
    }

    @Override
    public void updateContent()
    {
        if (GuiBuilder.instance.currentWindow != null) {
            GuiBuilder.instance.currentWindow.updateContent();
        }
    }

}
