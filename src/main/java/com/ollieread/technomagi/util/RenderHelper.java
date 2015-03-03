package com.ollieread.technomagi.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

public class RenderHelper
{

    public static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, int meta)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(0, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(1, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(2, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(3, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(4, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(5, meta)));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public static void renderIcon(IIcon icon, double pos)
    {
        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.addVertexWithUV(0.0D, 16.0D, pos, icon.getMinU(), icon.getMaxV());
        Tessellator.instance.addVertexWithUV(16.0D, 16.0D, pos, icon.getMaxU(), icon.getMaxV());
        Tessellator.instance.addVertexWithUV(16.0D, 0.0D, pos, icon.getMaxU(), icon.getMinV());
        Tessellator.instance.addVertexWithUV(0.0D, 0.0D, pos, icon.getMinU(), icon.getMinV());
        Tessellator.instance.draw();
    }

    public static IIcon getIcon(IIcon icon)
    {
        if (icon != null) {
            return icon;
        }

        return ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
    }

}
