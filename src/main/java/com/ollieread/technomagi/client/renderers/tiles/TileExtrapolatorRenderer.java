package com.ollieread.technomagi.client.renderers.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.models.ModelSpinningCube;
import com.ollieread.technomagi.common.block.extrapolator.tile.TileExtrapolator;
import com.ollieread.technomagi.util.ResourceHelper;

public class TileExtrapolatorRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation texture = ResourceHelper.texture("models/spinngcube.png");
    private ModelSpinningCube model;
    protected float shadowSize;

    public TileExtrapolatorRenderer()
    {
        this.shadowSize = 0.5F;
        this.model = new ModelSpinningCube(0.0F, true);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks)
    {
        if (tile instanceof TileExtrapolator) {
            TileExtrapolator extrapolator = (TileExtrapolator) tile;

            if (extrapolator.isActivated()) {
                float f2 = extrapolator.innerRotation + partialTicks;
                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + 0.5F, (float) y + 0.25F, (float) z + 0.5F);
                this.bindTexture(texture);
                float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
                f3 += f3 * f3;
                this.model.render(null, 0.0F, f2 * 3.0F, f3 * 0.2F, 0.0F, 0.0F, 0.0625F);
                GL11.glPopMatrix();
            }
        }
    }

}
