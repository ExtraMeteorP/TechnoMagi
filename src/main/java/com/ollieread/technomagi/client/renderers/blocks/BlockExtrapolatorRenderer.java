package com.ollieread.technomagi.client.renderers.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import com.ollieread.technomagi.common.block.extrapolator.BlockExtrapolator;
import com.ollieread.technomagi.util.RenderHelper;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockExtrapolatorRenderer implements ISimpleBlockRenderingHandler
{

    public static int id = RenderingRegistry.getNextAvailableRenderId();
    protected static double unit = 0.0625D;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if (modelId == id) {
            renderer.setRenderBounds(0, unit * 7, unit * 7, unit, 1D - (unit * 7), 1D - (unit * 7));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(unit * 7, 0, unit * 7, 1D - (unit * 7), unit, 1D - (unit * 7));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(unit * 7, unit * 7, 0, 1D - (unit * 7), 1D - (unit * 7), unit);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(unit * 15, unit * 7, unit * 7, unit * 16, 1D - (unit * 7), 1D - (unit * 7));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(unit * 7, unit * 15, unit * 7, 1D - (unit * 7), unit * 16, 1D - (unit * 7));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(unit * 7, unit * 7, unit * 15, 1D - (unit * 7), 1D - (unit * 7), unit * 16);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(unit, unit * 5, unit * 5, unit * 2, 1D - (unit * 5), 1D - (unit * 5));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(unit * 5, unit, unit * 5, 1D - (unit * 5), unit * 2, 1D - (unit * 5));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(unit * 5, unit * 5, unit, 1D - (unit * 5), 1D - (unit * 5), unit * 2);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(unit * 14, unit * 5, unit * 5, unit * 15, 1D - (unit * 5), 1D - (unit * 5));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(unit * 5, unit * 14, unit * 5, 1D - (unit * 5), unit * 15, 1D - (unit * 5));
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(unit * 5, unit * 5, unit * 14, 1D - (unit * 5), 1D - (unit * 5), unit * 15);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess blockaccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        BlockExtrapolator extrapolator = (BlockExtrapolator) block;

        renderer.setRenderBounds(unit, unit * 7, unit * 7, unit * 2, 1D - (unit * 7), 1D - (unit * 7));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(unit * 7, unit, unit * 7, 1D - (unit * 7), unit * 2, 1D - (unit * 7));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(unit * 7, unit * 7, unit, 1D - (unit * 7), 1D - (unit * 7), unit * 2);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(unit * 14, unit * 7, unit * 7, unit * 15, 1D - (unit * 7), 1D - (unit * 7));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(unit * 7, unit * 14, unit * 7, 1D - (unit * 7), unit * 15, 1D - (unit * 7));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(unit * 7, unit * 7, unit * 14, 1D - (unit * 7), 1D - (unit * 7), unit * 15);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(0, unit * 5, unit * 5, unit, 1D - (unit * 5), 1D - (unit * 5));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(unit * 5, 0, unit * 5, 1D - (unit * 5), unit, 1D - (unit * 5));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(unit * 5, unit * 5, 0, 1D - (unit * 5), 1D - (unit * 5), unit);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(unit * 15, unit * 5, unit * 5, unit * 16, 1D - (unit * 5), 1D - (unit * 5));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(unit * 5, unit * 15, unit * 5, 1D - (unit * 5), unit * 16, 1D - (unit * 5));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(unit * 5, unit * 5, unit * 15, 1D - (unit * 5), 1D - (unit * 5), unit * 16);
        renderer.renderStandardBlock(block, x, y, z);

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
