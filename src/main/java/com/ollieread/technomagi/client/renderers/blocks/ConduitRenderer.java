package com.ollieread.technomagi.client.renderers.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.api.util.RenderHelper;
import com.ollieread.technomagi.common.block.conduit.BlockConduit;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ConduitRenderer implements ISimpleBlockRenderingHandler
{

    public static int id = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if (modelId == id) {
            double d = 0.3125D;
            double d2 = d + 0.375D;
            renderer.setRenderBounds(0D, d, d, 1D, d2, d2);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess blockaccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        BlockConduit conduit = (BlockConduit) block;
        boolean flag = false;

        double d = 0.3125D;
        double d2 = d + 0.375D;
        renderer.setRenderBounds(d, d, d, d2, d2, d2);
        renderer.renderStandardBlock(block, x, y, z);
        flag = true;

        boolean up = conduit.canConnectTo(renderer.blockAccess, x, y, z, ForgeDirection.UP);
        boolean down = conduit.canConnectTo(renderer.blockAccess, x, y, z, ForgeDirection.DOWN);
        boolean north = conduit.canConnectTo(renderer.blockAccess, x, y, z, ForgeDirection.NORTH);
        boolean south = conduit.canConnectTo(renderer.blockAccess, x, y, z, ForgeDirection.SOUTH);
        boolean east = conduit.canConnectTo(renderer.blockAccess, x, y, z, ForgeDirection.EAST);
        boolean west = conduit.canConnectTo(renderer.blockAccess, x, y, z, ForgeDirection.WEST);

        renderer.field_152631_f = true;

        if (up) {
            renderer.setRenderBounds(d, d, d, d2, 1D, d2);
            renderer.renderStandardBlock(block, x, y, z);
            flag = true;
        }

        if (down) {
            renderer.setRenderBounds(d, 0D, d, d2, d2, d2);
            renderer.renderStandardBlock(block, x, y, z);
            flag = true;
        }

        if (north) {
            renderer.setRenderBounds(d, d, 0D, d2, d2, d2);
            renderer.renderStandardBlock(block, x, y, z);
            flag = true;
        }

        if (south) {
            renderer.setRenderBounds(d, d, d, d2, d2, 1D);
            renderer.renderStandardBlock(block, x, y, z);
            flag = true;
        }

        if (east) {
            renderer.setRenderBounds(d, d, d, 1D, d2, d2);
            renderer.renderStandardBlock(block, x, y, z);
            flag = true;
        }

        if (west) {
            renderer.setRenderBounds(0D, d, d, d2, d2, d2);
            renderer.renderStandardBlock(block, x, y, z);
            flag = true;
        }

        renderer.field_152631_f = false;
        block.setBlockBoundsBasedOnState(renderer.blockAccess, x, y, z);
        return flag;
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
