package com.ollieread.technomagi.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelMachineChamber;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityMachineObservation;

public class TileEntityMachineObservationRenderer extends TileEntitySpecialRenderer
{

    private final ModelMachineChamber model;

    public TileEntityMachineObservationRenderer()
    {
        model = new ModelMachineChamber();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks)
    {
        TileEntityMachineObservation chamber = (TileEntityMachineObservation) te;
        int side = chamber.getBlockMetadata();
        Tessellator tessellator = Tessellator.instance;

        EntityLiving entity = chamber.getEntityLiving();

        // This will make your block brightness dependent from surroundings
        // lighting.
        float f = Blocks.blockObservationChamber.getLightValue(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
        int l = te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        switch (side) {
            case 2:
                GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
        }

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        ResourceLocation textures = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelChamber.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (chamber.hasEntity()) {
            model.renderDoor((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

            entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);

            GL11.glPushMatrix();
            GL11.glScalef(-0.75F, 0.75F, 0.75F);
            GL11.glRotatef(180F, 0.0F, 0.0F, -1.0F);
            GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
            double yo = -1.0D;

            if (entity instanceof EntityEnderman) {
                yo = -1.5D;
            }

            float f1 = (float) chamber.field_145926_a + partialTicks;
            GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
            float f2;

            for (f2 = chamber.field_145928_o - chamber.field_145925_p; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
                ;
            }

            while (f2 < -(float) Math.PI) {
                f2 += ((float) Math.PI * 2F);
            }

            float f4 = chamber.field_145931_j + (chamber.field_145933_i - chamber.field_145931_j) * partialTicks + 0.25F;
            float f5 = chamber.field_145931_j + (chamber.field_145933_i - chamber.field_145931_j) * partialTicks + 0.75F;
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

            float f6 = chamber.field_145927_n + (chamber.field_145930_m - chamber.field_145927_n) * partialTicks;
            GL11.glEnable(GL11.GL_CULL_FACE);

            RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, yo, -0.1D, 0.0F, 0F);
            GL11.glPopMatrix();
        }

        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

}
