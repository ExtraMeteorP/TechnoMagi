package com.ollieread.technomagi.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelPrismaticPillar;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityPrismaticPillar;

public class TileEntityPrismaticPillarRenderer extends TileEntitySpecialRenderer
{

    private final ModelPrismaticPillar pillar;

    public TileEntityPrismaticPillarRenderer()
    {
        pillar = new ModelPrismaticPillar();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks)
    {
        TileEntityPrismaticPillar machine = (TileEntityPrismaticPillar) te;

        Tessellator tessellator = Tessellator.instance;
        // This will make your block brightness dependent from surroundings
        // lighting.
        float f = Blocks.blockBattery.getLightValue(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
        int l = te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);

        ResourceLocation texturePillar = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelPrismaticPillar.png"));
        ResourceLocation textureBeam = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/prismaticPillarBeam.png");
        float f1 = (float) machine.field_145926_a + partialTicks;

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(texturePillar);

        pillar.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();

        GL11.glPushMatrix();

        GL11.glTranslatef(0.0F, (float) -0.6F, 0.0F);
        GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
        float f2;

        for (f2 = machine.field_145928_o - machine.field_145925_p; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
            ;
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        float f3 = machine.field_145925_p + f2 * partialTicks;
        GL11.glRotatef(-f3 * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);

        float f4 = machine.field_145931_j + (machine.field_145933_i - machine.field_145931_j) * partialTicks + 0.25F;
        float f5 = machine.field_145931_j + (machine.field_145933_i - machine.field_145931_j) * partialTicks + 0.75F;
        f4 = (f4 - (float) MathHelper.truncateDoubleToInt((double) f4)) * 1.6F - 0.3F;
        f5 = (f5 - (float) MathHelper.truncateDoubleToInt((double) f5)) * 1.6F - 0.3F;

        if (f4 < 0.0F) {
            f4 = 0.0F;
        }

        if (f5 < 0.0F) {
            f5 = 0.0F;
        }

        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        if (f5 > 1.0F) {
            f5 = 1.0F;
        }

        float f6 = machine.field_145927_n + (machine.field_145930_m - machine.field_145927_n) * partialTicks;
        GL11.glEnable(GL11.GL_CULL_FACE);

        pillar.renderPrism((Entity) null, f1, f4, f5, f6, 0.0F, 0.0625F);

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
