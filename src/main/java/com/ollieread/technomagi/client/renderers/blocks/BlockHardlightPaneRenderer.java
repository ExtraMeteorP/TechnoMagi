package com.ollieread.technomagi.client.renderers.blocks;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.ollieread.technomagi.util.RenderHelper;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockHardlightPaneRenderer implements ISimpleBlockRenderingHandler
{

    public static int id = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        if (modelId == id) {
            renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            RenderHelper.renderStandardInvBlock(renderer, block, metadata);
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess blockaccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        int l = blockaccess.getHeight();
        Tessellator tessellator = Tessellator.instance;
        BlockPane pane = (BlockPane) block;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockaccess, x, y, z));
        int i1 = block.colorMultiplier(blockaccess, x, y, z);
        float f = (i1 >> 16 & 255) / 255.0F;
        float f1 = (i1 >> 8 & 255) / 255.0F;
        float f2 = (i1 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }

        tessellator.setColorOpaque_F(f, f1, f2);
        IIcon iicon;

        if (renderer.hasOverrideBlockTexture()) {
            iicon = renderer.overrideBlockTexture;
        } else {
            int j1 = blockaccess.getBlockMetadata(x, y, z);
            iicon = renderer.getBlockIconFromSideAndMetadata(block, 0, j1);
        }

        double d21 = iicon.getMinU();
        double d0 = iicon.getInterpolatedU(8.0D);
        double d1 = iicon.getMaxU();
        double d2 = iicon.getMinV();
        double d3 = iicon.getMaxV();
        double d4 = iicon.getInterpolatedU(7.0D);
        double d5 = iicon.getInterpolatedU(9.0D);
        double d6 = iicon.getMinV();
        double d7 = iicon.getInterpolatedV(8.0D);
        double d8 = iicon.getMaxV();
        double d9 = x;
        double d10 = x + 0.5D;
        double d11 = x + 1;
        double d12 = z;
        double d13 = z + 0.5D;
        double d14 = z + 1;
        double d15 = x + 0.5D - 0.0625D;
        double d16 = x + 0.5D + 0.0625D;
        double d17 = z + 0.5D - 0.0625D;
        double d18 = z + 0.5D + 0.0625D;
        boolean flag = pane.canPaneConnectTo(blockaccess, x, y, z - 1, NORTH);
        boolean flag1 = pane.canPaneConnectTo(blockaccess, x, y, z + 1, SOUTH);
        boolean flag2 = pane.canPaneConnectTo(blockaccess, x - 1, y, z, WEST);
        boolean flag3 = pane.canPaneConnectTo(blockaccess, x + 1, y, z, EAST);
        boolean flag4 = pane.shouldSideBeRendered(blockaccess, x, y + 1, z, 1);
        boolean flag5 = pane.shouldSideBeRendered(blockaccess, x, y - 1, z, 0);
        double d19 = 0.01D;
        double d20 = 0.005D;

        if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
            if (flag2 && !flag3) {
                tessellator.addVertexWithUV(d9, y + 1, d13, d21, d2);
                tessellator.addVertexWithUV(d9, y + 0, d13, d21, d3);
                tessellator.addVertexWithUV(d10, y + 0, d13, d0, d3);
                tessellator.addVertexWithUV(d10, y + 1, d13, d0, d2);
                tessellator.addVertexWithUV(d10, y + 1, d13, d21, d2);
                tessellator.addVertexWithUV(d10, y + 0, d13, d21, d3);
                tessellator.addVertexWithUV(d9, y + 0, d13, d0, d3);
                tessellator.addVertexWithUV(d9, y + 1, d13, d0, d2);

                if (!flag1 && !flag) {
                    tessellator.addVertexWithUV(d10, y + 1, d18, d4, d6);
                    tessellator.addVertexWithUV(d10, y + 0, d18, d4, d8);
                    tessellator.addVertexWithUV(d10, y + 0, d17, d5, d8);
                    tessellator.addVertexWithUV(d10, y + 1, d17, d5, d6);
                    tessellator.addVertexWithUV(d10, y + 1, d17, d4, d6);
                    tessellator.addVertexWithUV(d10, y + 0, d17, d4, d8);
                    tessellator.addVertexWithUV(d10, y + 0, d18, d5, d8);
                    tessellator.addVertexWithUV(d10, y + 1, d18, d5, d6);
                }

                if (flag4 || y < l - 1 && blockaccess.isAirBlock(x - 1, y + 1, z)) {
                    tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d17, d4, d8);
                    tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d17, d4, d8);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d17, d4, d7);
                }

                if (flag5 || y > 1 && blockaccess.isAirBlock(x - 1, y - 1, z)) {
                    tessellator.addVertexWithUV(d9, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d17, d4, d8);
                    tessellator.addVertexWithUV(d9, y - 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d9, y - 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d9, y - 0.01D, d17, d4, d8);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d17, d4, d7);
                }
            } else if (!flag2 && flag3) {
                tessellator.addVertexWithUV(d10, y + 1, d13, d0, d2);
                tessellator.addVertexWithUV(d10, y + 0, d13, d0, d3);
                tessellator.addVertexWithUV(d11, y + 0, d13, d1, d3);
                tessellator.addVertexWithUV(d11, y + 1, d13, d1, d2);
                tessellator.addVertexWithUV(d11, y + 1, d13, d0, d2);
                tessellator.addVertexWithUV(d11, y + 0, d13, d0, d3);
                tessellator.addVertexWithUV(d10, y + 0, d13, d1, d3);
                tessellator.addVertexWithUV(d10, y + 1, d13, d1, d2);

                if (!flag1 && !flag) {
                    tessellator.addVertexWithUV(d10, y + 1, d17, d4, d6);
                    tessellator.addVertexWithUV(d10, y + 0, d17, d4, d8);
                    tessellator.addVertexWithUV(d10, y + 0, d18, d5, d8);
                    tessellator.addVertexWithUV(d10, y + 1, d18, d5, d6);
                    tessellator.addVertexWithUV(d10, y + 1, d18, d4, d6);
                    tessellator.addVertexWithUV(d10, y + 0, d18, d4, d8);
                    tessellator.addVertexWithUV(d10, y + 0, d17, d5, d8);
                    tessellator.addVertexWithUV(d10, y + 1, d17, d5, d6);
                }

                if (flag4 || y < l - 1 && blockaccess.isAirBlock(x + 1, y + 1, z)) {
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d6);
                    tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d17, d4, d6);
                    tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d6);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d17, d4, d6);
                }

                if (flag5 || y > 1 && blockaccess.isAirBlock(x + 1, y - 1, z)) {
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d6);
                    tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d11, y - 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d17, d4, d6);
                    tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d6);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d11, y - 0.01D, d17, d4, d6);
                }
            }
        } else {
            tessellator.addVertexWithUV(d9, y + 1, d13, d21, d2);
            tessellator.addVertexWithUV(d9, y + 0, d13, d21, d3);
            tessellator.addVertexWithUV(d11, y + 0, d13, d1, d3);
            tessellator.addVertexWithUV(d11, y + 1, d13, d1, d2);
            tessellator.addVertexWithUV(d11, y + 1, d13, d21, d2);
            tessellator.addVertexWithUV(d11, y + 0, d13, d21, d3);
            tessellator.addVertexWithUV(d9, y + 0, d13, d1, d3);
            tessellator.addVertexWithUV(d9, y + 1, d13, d1, d2);

            if (flag4) {
                tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d6);
                tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d17, d4, d6);
                tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d17, d4, d8);
                tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d18, d5, d6);
                tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d17, d4, d6);
                tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d17, d4, d8);
            } else {
                if (y < l - 1 && blockaccess.isAirBlock(x - 1, y + 1, z)) {
                    tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d17, d4, d8);
                    tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d9, y + 1 + 0.01D, d17, d4, d8);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d17, d4, d7);
                }

                if (y < l - 1 && blockaccess.isAirBlock(x + 1, y + 1, z)) {
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d6);
                    tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d17, d4, d6);
                    tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d18, d5, d6);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y + 1 + 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d11, y + 1 + 0.01D, d17, d4, d6);
                }
            }

            if (flag5) {
                tessellator.addVertexWithUV(d9, y - 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d6);
                tessellator.addVertexWithUV(d11, y - 0.01D, d17, d4, d6);
                tessellator.addVertexWithUV(d9, y - 0.01D, d17, d4, d8);
                tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d8);
                tessellator.addVertexWithUV(d9, y - 0.01D, d18, d5, d6);
                tessellator.addVertexWithUV(d9, y - 0.01D, d17, d4, d6);
                tessellator.addVertexWithUV(d11, y - 0.01D, d17, d4, d8);
            } else {
                if (y > 1 && blockaccess.isAirBlock(x - 1, y - 1, z)) {
                    tessellator.addVertexWithUV(d9, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d17, d4, d8);
                    tessellator.addVertexWithUV(d9, y - 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d9, y - 0.01D, d18, d5, d8);
                    tessellator.addVertexWithUV(d9, y - 0.01D, d17, d4, d8);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d17, d4, d7);
                }

                if (y > 1 && blockaccess.isAirBlock(x + 1, y - 1, z)) {
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d6);
                    tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d11, y - 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d17, d4, d6);
                    tessellator.addVertexWithUV(d11, y - 0.01D, d18, d5, d6);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d18, d5, d7);
                    tessellator.addVertexWithUV(d10, y - 0.01D, d17, d4, d7);
                    tessellator.addVertexWithUV(d11, y - 0.01D, d17, d4, d6);
                }
            }
        }

        if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
            if (flag && !flag1) {
                tessellator.addVertexWithUV(d10, y + 1, d12, d21, d2);
                tessellator.addVertexWithUV(d10, y + 0, d12, d21, d3);
                tessellator.addVertexWithUV(d10, y + 0, d13, d0, d3);
                tessellator.addVertexWithUV(d10, y + 1, d13, d0, d2);
                tessellator.addVertexWithUV(d10, y + 1, d13, d21, d2);
                tessellator.addVertexWithUV(d10, y + 0, d13, d21, d3);
                tessellator.addVertexWithUV(d10, y + 0, d12, d0, d3);
                tessellator.addVertexWithUV(d10, y + 1, d12, d0, d2);

                if (!flag3 && !flag2) {
                    tessellator.addVertexWithUV(d15, y + 1, d13, d4, d6);
                    tessellator.addVertexWithUV(d15, y + 0, d13, d4, d8);
                    tessellator.addVertexWithUV(d16, y + 0, d13, d5, d8);
                    tessellator.addVertexWithUV(d16, y + 1, d13, d5, d6);
                    tessellator.addVertexWithUV(d16, y + 1, d13, d4, d6);
                    tessellator.addVertexWithUV(d16, y + 0, d13, d4, d8);
                    tessellator.addVertexWithUV(d15, y + 0, d13, d5, d8);
                    tessellator.addVertexWithUV(d15, y + 1, d13, d5, d6);
                }

                if (flag4 || y < l - 1 && blockaccess.isAirBlock(x, y + 1, z - 1)) {
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d12, d5, d6);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d4, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d12, d4, d6);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d13, d5, d6);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d12, d5, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d12, d4, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d4, d6);
                }

                if (flag5 || y > 1 && blockaccess.isAirBlock(x, y - 1, z - 1)) {
                    tessellator.addVertexWithUV(d15, y - 0.005D, d12, d5, d6);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d4, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d12, d4, d6);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d13, d5, d6);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d12, d5, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d12, d4, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d4, d6);
                }
            } else if (!flag && flag1) {
                tessellator.addVertexWithUV(d10, y + 1, d13, d0, d2);
                tessellator.addVertexWithUV(d10, y + 0, d13, d0, d3);
                tessellator.addVertexWithUV(d10, y + 0, d14, d1, d3);
                tessellator.addVertexWithUV(d10, y + 1, d14, d1, d2);
                tessellator.addVertexWithUV(d10, y + 1, d14, d0, d2);
                tessellator.addVertexWithUV(d10, y + 0, d14, d0, d3);
                tessellator.addVertexWithUV(d10, y + 0, d13, d1, d3);
                tessellator.addVertexWithUV(d10, y + 1, d13, d1, d2);

                if (!flag3 && !flag2) {
                    tessellator.addVertexWithUV(d16, y + 1, d13, d4, d6);
                    tessellator.addVertexWithUV(d16, y + 0, d13, d4, d8);
                    tessellator.addVertexWithUV(d15, y + 0, d13, d5, d8);
                    tessellator.addVertexWithUV(d15, y + 1, d13, d5, d6);
                    tessellator.addVertexWithUV(d15, y + 1, d13, d4, d6);
                    tessellator.addVertexWithUV(d15, y + 0, d13, d4, d8);
                    tessellator.addVertexWithUV(d16, y + 0, d13, d5, d8);
                    tessellator.addVertexWithUV(d16, y + 1, d13, d5, d6);
                }

                if (flag4 || y < l - 1 && blockaccess.isAirBlock(x, y + 1, z + 1)) {
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d13, d4, d7);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d14, d4, d8);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d8);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d14, d4, d7);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d13, d4, d8);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d5, d8);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d7);
                }

                if (flag5 || y > 1 && blockaccess.isAirBlock(x, y - 1, z + 1)) {
                    tessellator.addVertexWithUV(d15, y - 0.005D, d13, d4, d7);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d14, d4, d8);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d8);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d14, d4, d7);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d13, d4, d8);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d5, d8);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d7);
                }
            }
        } else {
            tessellator.addVertexWithUV(d10, y + 1, d14, d21, d2);
            tessellator.addVertexWithUV(d10, y + 0, d14, d21, d3);
            tessellator.addVertexWithUV(d10, y + 0, d12, d1, d3);
            tessellator.addVertexWithUV(d10, y + 1, d12, d1, d2);
            tessellator.addVertexWithUV(d10, y + 1, d12, d21, d2);
            tessellator.addVertexWithUV(d10, y + 0, d12, d21, d3);
            tessellator.addVertexWithUV(d10, y + 0, d14, d1, d3);
            tessellator.addVertexWithUV(d10, y + 1, d14, d1, d2);

            if (flag4) {
                tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d8);
                tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d12, d5, d6);
                tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d12, d4, d6);
                tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d14, d4, d8);
                tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d12, d5, d8);
                tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d6);
                tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d14, d4, d6);
                tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d12, d4, d8);
            } else {
                if (y < l - 1 && blockaccess.isAirBlock(x, y + 1, z - 1)) {
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d12, d5, d6);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d4, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d12, d4, d6);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d13, d5, d6);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d12, d5, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d12, d4, d7);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d4, d6);
                }

                if (y < l - 1 && blockaccess.isAirBlock(x, y + 1, z + 1)) {
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d13, d4, d7);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d14, d4, d8);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d8);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d14, d4, d7);
                    tessellator.addVertexWithUV(d15, y + 1 + 0.005D, d13, d4, d8);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d13, d5, d8);
                    tessellator.addVertexWithUV(d16, y + 1 + 0.005D, d14, d5, d7);
                }
            }

            if (flag5) {
                tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d8);
                tessellator.addVertexWithUV(d16, y - 0.005D, d12, d5, d6);
                tessellator.addVertexWithUV(d15, y - 0.005D, d12, d4, d6);
                tessellator.addVertexWithUV(d15, y - 0.005D, d14, d4, d8);
                tessellator.addVertexWithUV(d16, y - 0.005D, d12, d5, d8);
                tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d6);
                tessellator.addVertexWithUV(d15, y - 0.005D, d14, d4, d6);
                tessellator.addVertexWithUV(d15, y - 0.005D, d12, d4, d8);
            } else {
                if (y > 1 && blockaccess.isAirBlock(x, y - 1, z - 1)) {
                    tessellator.addVertexWithUV(d15, y - 0.005D, d12, d5, d6);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d4, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d12, d4, d6);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d13, d5, d6);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d12, d5, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d12, d4, d7);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d4, d6);
                }

                if (y > 1 && blockaccess.isAirBlock(x, y - 1, z + 1)) {
                    tessellator.addVertexWithUV(d15, y - 0.005D, d13, d4, d7);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d14, d4, d8);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d8);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d5, d7);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d14, d4, d7);
                    tessellator.addVertexWithUV(d15, y - 0.005D, d13, d4, d8);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d13, d5, d8);
                    tessellator.addVertexWithUV(d16, y - 0.005D, d14, d5, d7);
                }
            }
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return false;
    }

    @Override
    public int getRenderId()
    {
        return id;
    }

}
