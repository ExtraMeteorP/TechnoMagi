package com.ollieread.technomagi.client.renderers.tiles;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.common.block.research.tile.TileAnalyser;

public class TileAnalyserRenderer extends TileEntitySpecialRenderer
{

    public TileAnalyserRenderer()
    {
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks)
    {
        if (tile instanceof TileAnalyser) {
            TileAnalyser analyser = (TileAnalyser) tile;
            ItemStack focus = analyser.getStackInSlot(0);

            if (focus != null) {
                ItemStack stack = focus.copy();
                stack.stackSize = 1;
                EntityItem item = new EntityItem(analyser.getWorldObj(), 0.0D, 0.0D, 0.0D, stack);
                item.hoverStart = 0.0F;
                item.setLocationAndAngles(x, y, z, 0.0F, 0.0F);

                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.38F, (float) z + 0.5F);

                RenderManager.instance.renderEntityWithPosYaw(item, 0D, 0.0D, 0D, 0.0F, 0.0F);

                GL11.glPopMatrix();
            }
        }
    }
}
