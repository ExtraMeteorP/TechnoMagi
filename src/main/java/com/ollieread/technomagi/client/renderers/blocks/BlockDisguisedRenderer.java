package com.ollieread.technomagi.client.renderers.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import com.ollieread.technomagi.api.tile.ITileDisguisable;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockDisguisedRenderer implements ISimpleBlockRenderingHandler
{

    public static int id = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess blockaccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        ITileDisguisable tile = (ITileDisguisable) blockaccess.getTileEntity(x, y, z);

        if (tile.isDisguised()) {
            ItemStack stack = tile.getDisguise();
            renderer.renderBlockAllFaces(Block.getBlockFromItem(stack.getItem()), x, y, z);
        }

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