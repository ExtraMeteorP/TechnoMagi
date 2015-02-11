package com.ollieread.technomagi.client.renderers.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.api.util.RenderHelper;
import com.ollieread.technomagi.common.block.tile.TileBlockScanner;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ScannerRenderer implements ISimpleBlockRenderingHandler
{

    public static int id = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if (modelId == id) {
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        double d = 0.25D;
        double d2 = d * 2;

        TileBlockScanner scanner = (TileBlockScanner) world.getTileEntity(x, y, z);

        if (scanner != null) {
            ForgeDirection direction = scanner.getDirection();
        }

        renderer.setRenderBounds(d, 0, d, 1 - d, d, 1 - d);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(d2, d, d2, 1 - d2, d2, 1 - d2);
        renderer.renderStandardBlock(block, x, y, z);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getRenderId()
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
