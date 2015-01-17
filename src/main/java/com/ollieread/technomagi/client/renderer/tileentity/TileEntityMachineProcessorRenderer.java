package com.ollieread.technomagi.client.renderer.tileentity;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ollieread.technomagi.client.model.ModelMachineConstruct;
import com.ollieread.technomagi.client.model.ModelMachineProcessor;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityMachineProcessor;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class TileEntityMachineProcessorRenderer extends TileEntitySpecialRenderer
{

    private final ModelMachineConstruct construct;
    private final ModelMachineProcessor separator;
    private static Field rollField = ReflectionHelper.findField(EntityRenderer.class, "camRoll", "field_78495_O");
    private static Field prevRollField = ReflectionHelper.findField(EntityRenderer.class, "prevCamRoll", "field_78505_P");

    public TileEntityMachineProcessorRenderer()
    {
        construct = new ModelMachineConstruct();
        separator = new ModelMachineProcessor();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks)
    {
        TileEntityMachineProcessor machine = (TileEntityMachineProcessor) te;

        int side = machine.getFacing();
        Tessellator tessellator = Tessellator.instance;
        // This will make your block brightness dependent from surroundings
        // lighting.
        float f = Blocks.blockMachine.getLightValue(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
        int l = te.getWorldObj().getLightBrightnessForSkyBlocks(te.xCoord, te.yCoord, te.zCoord, 0);
        int l1 = l % 65536;
        int l2 = l / 65536;
        tessellator.setColorOpaque_F(f, f, f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) l1, (float) l2);

        ResourceLocation textureConstruct = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelConstruct.png"));
        ResourceLocation textureSeparator = (new ResourceLocation(Reference.MODID.toLowerCase(), "textures/blocks/modelSeparator.png"));

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        switch (side) {
            case 2:
                GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(270, 0.0F, 1.0F, 0.0F);
                break;
        }

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(textureConstruct);
        construct.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();

        GL11.glTranslatef(0.0F, (float) -1.5F, 0.0F);
        float f1 = (float) machine.field_145926_a + partialTicks;
        GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
        float f2;

        for (f2 = machine.field_145928_o - machine.field_145925_p; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
            ;
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        float f3 = machine.field_145925_p + f2 * partialTicks;

        ItemStack focus = machine.getStackInSlot(0);

        if (focus != null) {
            GL11.glPushMatrix();
            GL11.glScalef(1F, 1F, 1F);

            ItemStack stack2 = focus.copy();
            EntityItem entityItem = new EntityItem(machine.getWorldObj(), 0.0D, 0.0D, 0.0D, stack2);
            entityItem.getEntityItem().stackSize = 1;
            entityItem.hoverStart = 0.0F;
            entityItem.setLocationAndAngles(x, y, z, 0.0F, 0.0F);

            RenderManager.instance.renderEntityWithPosYaw(entityItem, 0D, 0.65D, 0D, 0.0F, 0.0F);

            GL11.glPopMatrix();

            f3 *= 45F;
        }

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

        Minecraft.getMinecraft().renderEngine.bindTexture(textureSeparator);

        separator.render((Entity) null, f1, f4, f5, f6, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }

}
