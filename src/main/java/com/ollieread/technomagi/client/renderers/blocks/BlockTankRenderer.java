package com.ollieread.technomagi.client.renderers.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import com.ollieread.technomagi.common.block.fluid.tile.TileTank;
import com.ollieread.technomagi.util.RenderHelper;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockTankRenderer implements ISimpleBlockRenderingHandler
{

    public static int id = RenderingRegistry.getNextAvailableRenderId();
    public static double s = 1D / 32;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if (modelId == id) {
            renderer.field_152631_f = true;

            // Sides

            renderer.setRenderBounds(0, 0, 0, s * 2, 1D, s * 2);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 30, 0, 0, 1D, 1D, s * 2);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(0, 0, s * 30, s * 2, 1D, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 30, 0, s * 30, 1D, 1D, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            // Bottom

            renderer.setRenderBounds(0, 0, 0, 1D, s * 1, s * 1);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(0, 0, 0, s * 1, s * 1, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 31, 0, 0, 1D, s * 1, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(0, 0, s * 31, 1D, s * 1, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            // Top

            renderer.setRenderBounds(0, s * 31, 0, 1D, 1D, s * 1);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(0, s * 31, 0, s * 1, 1D, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 31, s * 31, 0, 1D, 1D, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(0, s * 31, s * 31, 1D, 1D, 1D);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            // Glass

            renderer.setRenderBounds(s * 1, s * 1, s * 1, s * 31, s * 2, s * 31);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 1, s * 30, s * 1, s * 31, s * 31, s * 31);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 1, s * 1, s * 1, s * 2, s * 31, s * 31);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 30, s * 1, s * 1, s * 31, s * 31, s * 31);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.setRenderBounds(s * 1, s * 1, s * 1, s * 31, s * 31, s * 2);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
            renderer.setRenderBounds(s * 1, s * 1, s * 30, s * 31, s * 31, s * 31);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);

            renderer.field_152631_f = false;
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        renderer.field_152631_f = true;
        int pass = MinecraftForgeClient.getRenderPass();

        // Sides

        renderer.setRenderBounds(0, 0, 0, s * 2, 1D, s * 2);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 30, 0, 0, 1D, 1D, s * 2);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(0, 0, s * 30, s * 2, 1D, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 30, 0, s * 30, 1D, 1D, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        // Bottom

        renderer.setRenderBounds(0, 0, 0, 1D, s * 1, s * 1);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(0, 0, 0, s * 1, s * 1, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 31, 0, 0, 1D, s * 1, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(0, 0, s * 31, 1D, s * 1, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        // Top

        renderer.setRenderBounds(0, s * 31, 0, 1D, 1D, s * 1);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(0, s * 31, 0, s * 1, 1D, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 31, s * 31, 0, 1D, 1D, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(0, s * 31, s * 31, 1D, 1D, 1D);
        renderer.renderStandardBlock(block, x, y, z);

        // Glass

        renderer.setRenderBounds(s * 1, s * 1, s * 1, s * 31, s * 2, s * 31);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 1, s * 30, s * 1, s * 31, s * 31, s * 31);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 1, s * 1, s * 1, s * 2, s * 31, s * 31);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 30, s * 1, s * 1, s * 31, s * 31, s * 31);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 1, s * 1, s * 1, s * 31, s * 31, s * 2);
        renderer.renderStandardBlock(block, x, y, z);

        renderer.setRenderBounds(s * 1, s * 1, s * 30, s * 31, s * 31, s * 31);
        renderer.renderStandardBlock(block, x, y, z);

        TileTank tank = (TileTank) world.getTileEntity(x, y, z);

        if (tank != null && tank.getFluid() != null && tank.getFluidAmount() > 0) {
            FluidStack fluidStack = tank.getFluid();
            int c = tank.getCapacity();
            int a = tank.getFluidAmount();

            double height = ((s * 28) / 100) * a / (c / 100);
            final Fluid fluid = fluidStack.getFluid();
            IIcon icon = fluid.getStillIcon();

            renderer.setOverrideBlockTexture(icon);
            if (!fluid.isGaseous()) {
                renderer.setRenderBounds(s * 2, s * 2, s * 2, s * 30, (s * 2) + height, s * 30);
            } else {
                renderer.setRenderBounds(s * 2, (s * 28) - height, s * 2, s * 30, s * 30, s * 30);
            }
            renderer.renderStandardBlock(block, x, y, z);
            renderer.clearOverrideBlockTexture();
        }

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
