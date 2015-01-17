package com.ollieread.technomagi.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.client.ClientProxy;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.ITileEntityDisguisable;
import com.ollieread.technomagi.tileentity.ITileEntityHasOwner;
import com.ollieread.technomagi.util.RenderHelper;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockSmartmetalRenderer implements ISimpleBlockRenderingHandler
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
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (ClientProxy.renderPass == 1) {
            if (((ITileEntityHasOwner) tileEntity).isOwner(TechnoMagi.proxy.getClientPlayer().getCommandSenderName())) {
                if (((ITileEntityDisguisable) tileEntity).isDisguised() && world.getBlockMetadata(x, y, z) != 3) {
                    renderer.setOverrideBlockTexture(Blocks.blockSmartmetal.overlayIcon);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        renderer.renderStandardBlock(block, x, y, z);
        renderer.clearOverrideBlockTexture();

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
