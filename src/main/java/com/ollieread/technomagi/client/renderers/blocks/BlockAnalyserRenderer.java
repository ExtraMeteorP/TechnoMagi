package com.ollieread.technomagi.client.renderers.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;

import com.ollieread.technomagi.util.RenderHelper;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockAnalyserRenderer implements ISimpleBlockRenderingHandler
{

    public static int id = RenderingRegistry.getNextAvailableRenderId();
    public static double s = 1D / 32;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if (modelId == id) {
            renderer.field_152631_f = true;

            //

            renderer.setRenderBounds(s * 9, 0, 0, 1D - (s * 9), s * 10, s * 10);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 9, 0, 1D - (s * 10), 1D - (s * 9), s * 10, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(0, 0, s * 9, s * 9, s * 10, 1D - (s * 9));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(1D - (s * 9), 0, s * 9, 1D, s * 10, 1D - (s * 9));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            //

            renderer.setRenderBounds(s * 9, 1D - (s * 10), 0, 1D - (s * 9), 1D, s * 10);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 9, 1D - (s * 10), 1D - (s * 10), 1D - (s * 9), 1D, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(0, 1D - (s * 10), s * 9, s * 9, 1D, 1D - (s * 9));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(1D - (s * 9), 1D - (s * 10), s * 9, 1D, 1D, 1D - (s * 9));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            //

            renderer.setRenderBounds(0, 0, 0, s * 9, 1D, s * 9);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(1D - (s * 9), 0, 0, 1D, 1D, s * 9);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(0, 0, 1D - (s * 9), s * 9, 1D, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(1D - (s * 9), 0, 1D - (s * 9), 1D, 1D, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            //

            renderer.setRenderBounds(s * 2, s * 10, s * 9, s * 3, 1D - (s * 10), 1D - (s * 9));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(1D - (s * 3), s * 10, s * 9, 1D - (s * 2), 1D - (s * 10), 1D - (s * 9));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 9, s * 10, s * 2, 1D - (s * 9), 1D - (s * 10), s * 3);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 9, s * 10, 1D - (s * 3), 1D - (s * 9), 1D - (s * 10), 1D - (s * 2));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 9, s * 2, s * 10, 1D - (s * 9), s * 3, 1D - (s * 10));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 9, 1D - (s * 3), s * 10, 1D - (s * 9), 1D - (s * 2), 1D - (s * 10));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.field_152631_f = false;
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        renderer.field_152631_f = true;
        int pass = MinecraftForgeClient.getRenderPass();

        //

        renderer.setRenderBounds(s * 9, 0, 0, 1D - (s * 9), s * 10, s * 10);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 9, 0, 1D - (s * 10), 1D - (s * 9), s * 10, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(0, 0, s * 9, s * 9, s * 10, 1D - (s * 9));
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(1D - (s * 9), 0, s * 9, 1D, s * 10, 1D - (s * 9));
        renderer.renderStandardBlock(block, x, y, z);

        //

        renderer.setRenderBounds(s * 9, 1D - (s * 10), 0, 1D - (s * 9), 1D, s * 10);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 9, 1D - (s * 10), 1D - (s * 10), 1D - (s * 9), 1D, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(0, 1D - (s * 10), s * 9, s * 9, 1D, 1D - (s * 9));
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(1D - (s * 9), 1D - (s * 10), s * 9, 1D, 1D, 1D - (s * 9));
        renderer.renderStandardBlock(block, x, y, z);

        //

        renderer.setRenderBounds(0, 0, 0, s * 9, 1D, s * 9);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(1D - (s * 9), 0, 0, 1D, 1D, s * 9);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(0, 0, 1D - (s * 9), s * 9, 1D, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(1D - (s * 9), 0, 1D - (s * 9), 1D, 1D, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        //

        renderer.setRenderBounds(s * 2, s * 10, s * 9, s * 3, 1D - (s * 10), 1D - (s * 9));
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(1D - (s * 3), s * 10, s * 9, 1D - (s * 2), 1D - (s * 10), 1D - (s * 9));
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 9, s * 10, s * 2, 1D - (s * 9), 1D - (s * 10), s * 3);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 9, s * 10, 1D - (s * 3), 1D - (s * 9), 1D - (s * 10), 1D - (s * 2));
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 9, s * 2, s * 10, 1D - (s * 9), s * 3, 1D - (s * 10));
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 9, 1D - (s * 3), s * 10, 1D - (s * 9), 1D - (s * 2), 1D - (s * 10));
        renderer.renderStandardBlock(block, x, y, z);

        renderer.field_152631_f = false;

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return id;
    }

}
